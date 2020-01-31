package com.blogspot.android_czy_java.beautytips.view.recipe

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blogspot.android_czy_java.beautytips.usecase.account.userlist.UserListRecipeRequest
import com.blogspot.android_czy_java.beautytips.usecase.common.OneListRequest
import com.blogspot.android_czy_java.beautytips.usecase.recipe.RecipeRequest
import com.blogspot.android_czy_java.beautytips.usecase.search.SearchResultRequest
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
import com.blogspot.android_czy_java.beautytips.view.recipe.callback.SubcategoryListener
import kotlinx.android.synthetic.main.fragment_one_list.recipe_list
import kotlinx.android.synthetic.main.fragment_one_list.title
import kotlinx.android.synthetic.main.fragment_one_list.view.*
import kotlinx.android.synthetic.main.motion_layout_fragment_one_list.*


sealed class OneListFragment<RECIPE_REQUEST : OneListRequest, VIEW_MODEL : OneListViewModel<RECIPE_REQUEST>> :
        AppFragment(), ListCallback.InnerListCallback {

    @Inject
    lateinit var activityViewModel: DetailActivityViewModel

    @Inject
    lateinit var viewModel: VIEW_MODEL


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.motion_layout_fragment_one_list, container, false)
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
    }

    private fun prepareViewModel() {
        viewModel.recipeListLiveData.observe(this, Observer { render(it) })
        arguments?.getSerializable(KEY_REQUEST)?.let {
            viewModel.getList(it as RECIPE_REQUEST)
        }
    }

    private fun render(uiModel: GenericUiModel<OneListData>?) {

        when (uiModel) {
            is GenericUiModel.LoadingSuccess -> {
                recipe_list.apply {
                    adapter = RecipeListAdapter(this@OneListFragment,
                            uiModel.data.data, false)
                    layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                }
                title.text = uiModel.data.listTitle

                uiModel.data.category?.let { configureChipCloud(it) }
            }

            is GenericUiModel.StatusLoading -> {
            }
            is GenericUiModel.LoadingError -> {

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

    private fun startOneRecipeListFragment(category: String, subcategory: String) {
        val categoryLabel = CategoryLabel.get(category) ?: return
        val request = RecipeRequest(
                CategoryLabel.get(categoryLabel, subcategory),
                Order.NEW)
        activity?.supportFragmentManager
                ?.beginTransaction()?.replace(
                        R.id.main_container,
                        OneRecipeListFragment.getInstance(request),
                        TAG_ONE_LIST_FRAGMENT)
                ?.addToBackStack(null)
                ?.commit()
    }


    companion object {
        const val KEY_REQUEST = "recipe request"
        const val TAG_ONE_LIST_FRAGMENT = "one list fragment"
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