package com.blogspot.android_czy_java.beautytips.listView.view


import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

import com.blogspot.android_czy_java.beautytips.R
import com.blogspot.android_czy_java.beautytips.listView.utils.recyclerViewUtils.RecyclerViewHelper
import com.blogspot.android_czy_java.beautytips.listView.utils.recyclerViewUtils.SpacesItemDecoration
import com.blogspot.android_czy_java.beautytips.listView.view.dialogs.DeleteTipDialog
import com.blogspot.android_czy_java.beautytips.viewmodel.recipe.RecipeUiModel
import com.blogspot.android_czy_java.beautytips.viewmodel.recipe.RecipeViewModel
import com.blogspot.android_czy_java.beautytips.listView.viewmodel.TabletDetailViewModel

import butterknife.BindView
import butterknife.ButterKnife
import com.blogspot.android_czy_java.beautytips.database.ItemModelInterface
import com.blogspot.android_czy_java.beautytips.database.recipe.RecipeModel

import com.blogspot.android_czy_java.beautytips.listView.view.RecipeListAdapter.KEY_ITEM
import com.blogspot.android_czy_java.beautytips.listView.view.dialogs.DeleteTipDialog.TAG_DELETE_TIP_DIALOG
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 */
class MainActivityFragment : Fragment(), BaseListViewAdapter.PositionListener {


    @BindView(R.id.recycler_view)
    lateinit var mRecyclerView: RecyclerView

    @BindView(R.id.loading_indicator)
    lateinit var loadingIndicator: ProgressBar

    lateinit var mLayoutManager: StaggeredGridLayoutManager

    lateinit var mAdapter: BaseListViewAdapter<out ItemModelInterface>


    lateinit var viewModel: TabletDetailViewModel

    @Inject
    lateinit var recipeViewModel: RecipeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!).get(TabletDetailViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_main_activity, container, false)

        ButterKnife.bind(this, view)

        return view

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        /*TODO: delete
         this is done to pass changed fav num from detail fragment so it's updated in tip list
        viewModel.tipChangeIndicator.observe(this, Observer {
            if (activity != null) {
                Timber.d("activity not null")
                if (activity!!.intent != null) {
                    val bundle = activity!!.intent.extras
                    val id = bundle!!.getString(KEY_ID)
                    val favNum = bundle.getLong(KEY_FAV_NUM, 0)

                    Timber.d("favNum: $favNum")

                    if (!TextUtils.isEmpty(id)) {
                        mAdapter?.setFavNum(id, favNum)
                    }
                }
            }
        })
        */


        recipeViewModel.recipeLiveData.observe(this, Observer { this.render(it) })
        recipeViewModel.init()

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onResume() {
        super.onResume()
        //open tip from notification
        if (activity != null && activity!!.intent != null) {
            val tipId = activity!!.intent.getStringExtra(KEY_ITEM)
            if (!TextUtils.isEmpty(tipId)) {
                mAdapter!!.openTipWithId(tipId)
                activity!!.intent = null
            }
        }
    }

    private fun prepareRecyclerView(recyclerViewList: List<RecipeModel>) {

        if (loadingIndicator.visibility == View.VISIBLE) {
            loadingIndicator.visibility = View.INVISIBLE
        }

        var itemDivider: Float

        val isTablet = resources.getBoolean(R.bool.is_tablet)
        val orientation = resources.configuration.orientation

        if (!isTablet) {
            itemDivider = 1f
        } else {
            val isSmallTablet = resources.getBoolean(R.bool.is_small_tablet)
            //on tablet min720dp landscape
            if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                itemDivider = 2f
            } else
                itemDivider = 1.5f//on tablet min720dp portrait

            if (isSmallTablet) itemDivider *= 0.8f
        }


        mAdapter = RecipeListAdapter(context, recyclerViewList, this,
                viewModel, itemDivider)


        mRecyclerView.adapter = mAdapter

        val columnNum: Int

        if (!isTablet && orientation == Configuration.ORIENTATION_LANDSCAPE) {
            columnNum = 2
        } else
            columnNum = 1

        //add layout manager
        mLayoutManager = RecyclerViewHelper.createLayoutManager(columnNum)
        mRecyclerView.layoutManager = mLayoutManager

        //item decoration is added to make spaces between items in recycler view
        if (mRecyclerView.itemDecorationCount == 0)
            mRecyclerView.addItemDecoration(SpacesItemDecoration(
                    resources.getDimension(R.dimen.list_padding).toInt()))


    }

    private fun render(model: RecipeUiModel) {
        when (model) {
            is RecipeUiModel.RecipeSuccess -> {
                prepareRecyclerView(model.recipes)
            }
            is RecipeUiModel.RecipeLoading -> {

            }
            is RecipeUiModel.RecipeLoadingError -> {

            }
        }

    }


    /*
        Interface methods
     */

    override fun onClickDeleteTip(tipId: Long) {
        val mDialogFragment = DeleteTipDialog()
        mDialogFragment.setTipId(tipId)
        mDialogFragment.setViewModel(viewModel)
        mDialogFragment.show(activity!!.fragmentManager, TAG_DELETE_TIP_DIALOG)

    }
}// Required empty public constructor
