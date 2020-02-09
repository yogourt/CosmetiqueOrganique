package com.blogspot.android_czy_java.beautytips.view.recipe

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blogspot.android_czy_java.beautytips.usecase.common.OneListRequest
import com.blogspot.android_czy_java.beautytips.view.IntentDataKeys
import com.blogspot.android_czy_java.beautytips.view.common.AppFragment
import com.blogspot.android_czy_java.beautytips.view.detail.DetailActivity
import com.blogspot.android_czy_java.beautytips.view.recipe.callback.ListCallback
import com.blogspot.android_czy_java.beautytips.viewmodel.GenericUiModel
import com.blogspot.android_czy_java.beautytips.viewmodel.detail.DetailActivityViewModel
import com.blogspot.android_czy_java.beautytips.viewmodel.recipe.OneListData
import com.blogspot.android_czy_java.beautytips.viewmodel.recipe.OneListViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject
import com.adroitandroid.chipcloud.ChipCloud
import com.adroitandroid.chipcloud.FlowLayout
import com.blogspot.android_czy_java.beautytips.R
import com.blogspot.android_czy_java.beautytips.appUtils.categories.CategoryAll
import com.blogspot.android_czy_java.beautytips.appUtils.categories.CategoryInterface
import com.blogspot.android_czy_java.beautytips.appUtils.categories.labels.CategoryLabel
import com.blogspot.android_czy_java.beautytips.appUtils.orders.Order
import com.blogspot.android_czy_java.beautytips.usecase.common.RecipeRequest
import com.blogspot.android_czy_java.beautytips.usecase.common.SearchResultRequest
import com.blogspot.android_czy_java.beautytips.usecase.common.UserListRecipeRequest
import com.blogspot.android_czy_java.beautytips.view.recipe.callback.SubcategoryListener
import kotlinx.android.synthetic.main.motion_layout_fragment_one_list.*
import kotlinx.android.synthetic.main.motion_layout_fragment_one_list.view.*


sealed class OneListFragment<RECIPE_REQUEST : OneListRequest, VIEW_MODEL : OneListViewModel<RECIPE_REQUEST>> :
        AppFragment(), ListCallback.InnerListCallback {

    @Inject
    lateinit var activityViewModel: DetailActivityViewModel

    @Inject
    lateinit var viewModel: VIEW_MODEL

    private lateinit var request: RECIPE_REQUEST

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.motion_layout_fragment_one_list, container, false)

        //TODO: error prone
        request = arguments?.getSerializable(KEY_REQUEST) as RECIPE_REQUEST

        prepareRecipeList(view)
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        prepareViewModel()
    }

    private fun prepareRecipeList(view: View) {
        if (context is MainActivity) {
            view.recipe_list.setPadding(0, 0, 0,
                    resources.getDimensionPixelSize(R.dimen.main_list_bottom_padding)
            )
        } else if (context is DetailActivity) {
            view.status_bar.visibility = View.VISIBLE
        }

        prepareListTitle(view)
    }

    private fun prepareViewModel() {
        viewModel.recipeListLiveData.observe(this, Observer { render(it) })
        viewModel.getList(request)
    }

    private fun render(uiModel: GenericUiModel<OneListData>?) {

        when (uiModel) {
            is GenericUiModel.LoadingSuccess -> {
                recipe_list.apply {
                    adapter = RecipeListAdapter(this@OneListFragment,
                            uiModel.data.data, false)
                    layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                }

                if (shouldGetTitleFromUiModel()) {
                    title.text = uiModel.data.listTitle
                }

                uiModel.data.category?.let { configureChipCloud(it) }
            }

            is GenericUiModel.StatusLoading -> {
            }
            is GenericUiModel.LoadingError -> {

            }
        }
    }

    private fun shouldGetTitleFromUiModel(): Boolean {
        return request is SearchResultRequest && titleOrKeywordsNotEmpty(request as SearchResultRequest)
    }

    private fun titleOrKeywordsNotEmpty(request: SearchResultRequest) =
            request.title.isNotEmpty() || request.keywords.isNotEmpty()

    private fun prepareListTitle(view: View) {

        view.apply {
            if (request is UserListRecipeRequest) {
                this.title?.text = (request as UserListRecipeRequest).userListTitle
            } else if (request.category !is CategoryAll) {
                this.title?.text = request.category.getListTitle()
            }
            preparePopularNewSwitch(this)
        }
    }

    private fun preparePopularNewSwitch(view: View) {
        val pink = resources.getColor(R.color.colorAccent)
        view.apply {
            if (request.order == Order.NEW) {
                this.switch_new?.setTextColor(pink)
                this.switch_popular?.setOnClickListener { startOneListFragment(request.newOrder(Order.POPULARITY)) }
            } else {
                this.switch_popular?.setTextColor(pink)
                this.switch_new?.setOnClickListener { startOneListFragment(request.newOrder(Order.NEW)) }
            }
        }
    }

    private fun configureChipCloud(category: CategoryInterface) {

        if (this is OneRecipeListFragment) {

            if (category is CategoryAll) return

            ChipCloud.Configure()
                    .chipCloud(subcategories)
                    .labels(category.subcategories().toTypedArray())
                    .mode(ChipCloud.Mode.REQUIRED)
                    .gravity(FlowLayout.Gravity.CENTER)
                    .build()

            subcategories.setSelectedChip(category.indexOfSubcategory())
            subcategories.setChipListener(SubcategoryListener(category, ::startOneRecipeListFragment))
        }
    }

    override fun onRecipeClick(recipeId: Long) {
        if (isTablet) {
            activityViewModel.chosenItemId = recipeId
        } else {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(IntentDataKeys.KEY_RECIPE_ID, recipeId)
            startActivity(intent)
        }
    }

    private fun startOneRecipeListFragment(category: CategoryInterface) {
        val request = RecipeRequest(category, request.order)
        startOneListFragment(request)
    }

    private fun startOneListFragment(request: OneListRequest) {
        val container = if (activity is MainActivity) R.id.main_container else R.id.detail_for_list_container
        activity?.supportFragmentManager
                ?.beginTransaction()?.replace(
                        container,
                        getInstance(request),
                        TAG_ONE_LIST_FRAGMENT)
                ?.addToBackStack(null)
                ?.commit()
    }


    companion object {
        const val KEY_REQUEST = "recipe request"
        const val TAG_ONE_LIST_FRAGMENT = "one list fragment"

        private fun getInstance(request: OneListRequest): Fragment {
            return when (request) {
                is RecipeRequest -> OneRecipeListFragment.getInstance(request)
                is UserListRecipeRequest -> OneUserRecipeListFragment.getInstance(request)
                is SearchResultRequest -> OneSearchRecipeListFragment.getInstance(request)
            }
        }
    }


    class OneRecipeListFragment : OneListFragment<RecipeRequest,
            OneListViewModel.OneRecipeListViewModel>() {
        companion object {
            fun getInstance(request: RecipeRequest): OneRecipeListFragment {
                val fragment = OneRecipeListFragment()
                val bundle = Bundle()
                bundle.putSerializable(KEY_REQUEST, request)
                fragment.arguments = bundle
                return fragment
            }
        }
    }

    class OneUserRecipeListFragment : OneListFragment<UserListRecipeRequest,
            OneListViewModel.OneUserRecipeListViewModel>() {
        companion object {
            fun getInstance(request: UserListRecipeRequest): OneUserRecipeListFragment {
                val fragment = OneUserRecipeListFragment()
                val bundle = Bundle()
                bundle.putSerializable(KEY_REQUEST, request)
                fragment.arguments = bundle
                return fragment
            }
        }
    }

    class OneSearchRecipeListFragment : OneListFragment<SearchResultRequest,
            OneListViewModel.SearchResultViewModel>() {
        companion object {
            fun getInstance(request: SearchResultRequest): OneSearchRecipeListFragment {
                val fragment = OneSearchRecipeListFragment()
                val bundle = Bundle()
                bundle.putSerializable(KEY_REQUEST, request)
                fragment.arguments = bundle
                return fragment
            }
        }
    }
}