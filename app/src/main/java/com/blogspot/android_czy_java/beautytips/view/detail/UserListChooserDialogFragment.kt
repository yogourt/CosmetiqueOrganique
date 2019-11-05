package com.blogspot.android_czy_java.beautytips.view.detail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import com.blogspot.android_czy_java.beautytips.R
import com.blogspot.android_czy_java.beautytips.view.common.AppBottomSheetDialogFragment
import com.blogspot.android_czy_java.beautytips.view.detail.callback.UserListChooserCallback
import com.blogspot.android_czy_java.beautytips.viewmodel.GenericUiModel
import com.blogspot.android_czy_java.beautytips.viewmodel.account.UserListViewModel
import com.blogspot.android_czy_java.beautytips.viewmodel.detail.HeaderViewModel
import com.blogspot.android_czy_java.beautytips.viewmodel.recipe.MainListData
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_user_list_chooser.view.*
import kotlinx.android.synthetic.main.layout_user_info.view.loading_indicator
import javax.inject.Inject

class UserListChooserDialogFragment : AppBottomSheetDialogFragment(), UserListChooserCallback {

    @Inject
    lateinit var viewModel: UserListViewModel

    @Inject
    lateinit var headerViewModel: HeaderViewModel

    lateinit var loadingIndicator: ProgressBar
    lateinit var userLists: GridView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_user_list_chooser, container, false)

        loadingIndicator = view.loading_indicator
        userLists = view.user_lists

        expand()
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
        prepareViewModel()
    }


    private fun prepareViewModel() {
        viewModel.recipeListLiveData.observe(this, Observer { render(it) })
        viewModel.init()
    }

    private fun render(uiModel: GenericUiModel<MainListData>) {

        when (uiModel) {
            is GenericUiModel.LoadingSuccess -> {
                loadingIndicator.visibility = View.GONE
                prepareContent(uiModel.data)
            }

            is GenericUiModel.StatusLoading -> {
                loadingIndicator.visibility = View.VISIBLE
            }
            is GenericUiModel.LoadingError -> {
                loadingIndicator.visibility = View.GONE
                showInfoAboutError(uiModel.message)
            }
        }
    }

    private fun prepareContent(data: MainListData) {
        activity?.let {
            userLists.adapter = UserListAdapter(data, this)
        }
    }

    private fun showInfoAboutError(error: String) {
        Snackbar.make(userLists, error, Snackbar.LENGTH_LONG).show()
    }

    override fun onClick(listName: String) {
        headerViewModel.saveToList(listName)
        dismiss()
    }

    companion object {
        const val TAG = "UserListChooserDialogFragment"
    }

}