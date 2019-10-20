package com.blogspot.android_czy_java.beautytips.view.comment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blogspot.android_czy_java.beautytips.R
import com.blogspot.android_czy_java.beautytips.database.comment.CommentModel
import com.blogspot.android_czy_java.beautytips.view.comment.callback.CommentListCallback
import com.blogspot.android_czy_java.beautytips.view.common.AppBottomSheetDialogFragment
import com.blogspot.android_czy_java.beautytips.viewmodel.GenericUiModel
import com.blogspot.android_czy_java.beautytips.viewmodel.account.AccountViewModel
import com.blogspot.android_czy_java.beautytips.viewmodel.comment.CommentsViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.layout_new_comment.view.*
import android.app.Activity
import com.blogspot.android_czy_java.beautytips.viewmodel.comment.CommentWithAuthorModel
import kotlinx.android.synthetic.main.fragment_comments.view.*


class CommentFragment : AppBottomSheetDialogFragment(), CommentListCallback {

    @Inject
    lateinit var viewModel: CommentsViewModel

    @Inject
    lateinit var accountViewModel: AccountViewModel

    private var recipeId: Long = 1

    private lateinit var loadingIndicator: ProgressBar
    private lateinit var commentList: RecyclerView
    private lateinit var commentsLayout: CoordinatorLayout


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_comments, container, false)

        loadingIndicator = view.loading_indicator
        commentList = view.comments_list
        commentsLayout = view.comments_layout

        expand()
        prepareNewCommentLayout(view)

        return view
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)

        recipeId = arguments?.getLong(KEY_RECIPE_ID, 1) ?: 1

        prepareViewModel()
    }

    private fun prepareViewModel() {
        viewModel.commentListLiveData.observe(this, Observer { render(it) })
        if (recipeId != 1L) {
            viewModel.init(recipeId)

        }

    }

    private fun prepareNewCommentLayout(view: View) {
        view.button_add_new.setOnClickListener {
            addComment(
                    view.new_comment.text.toString(),
                    null
            )
        }
    }

    private fun render(uiModel: GenericUiModel<List<CommentWithAuthorModel>>?) {
        when (uiModel) {
            is GenericUiModel.LoadingSuccess -> {
                hideLoadingIndicator()
                prepareCommentList(uiModel.data)
            }

            is GenericUiModel.StatusLoading -> {
                showLoadingIndicator()
            }
            is GenericUiModel.LoadingError -> {
                hideLoadingIndicator()
            }
        }
    }

    private fun prepareCommentList(comments: List<CommentWithAuthorModel>) {

        if (comments.isEmpty()) {
            commentList.visibility = View.GONE
        } else {
            commentList.visibility = View.VISIBLE
            commentList.apply {
                adapter = CommentListAdapter(this@CommentFragment, comments)
                layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, true)
            }
        }
    }

    override fun addComment(newComment: String, responseTo: String?) {
        if (accountViewModel.isUserAnonymous()) {
            showLoginSnackbar(commentList,
                    responseTo != null)
        } else {
            viewModel.addComment(newComment, responseTo)
            collapse()
        }
    }

    private fun hideLoadingIndicator() {
        loadingIndicator.visibility = View.INVISIBLE
    }

    private fun showLoadingIndicator() {
        loadingIndicator.visibility = View.VISIBLE
    }

    private fun showLoginSnackbar(view: View, closeKeyboard: Boolean = false) {

        if (closeKeyboard) closeKeyboard()

        Snackbar.make(view, R.string.login_prompt, Snackbar.LENGTH_LONG)
                .setAction(R.string.login) {
                    handleLoginClick(view)
                }
                .show()
    }

    private fun closeKeyboard() {
        val imm = context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager
        view?.let {
            imm?.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }

    private fun handleLoginClick(view: View) {
        if (accountViewModel.isNetworkConnection()) {
            openLoginActivity()
        } else {
            showInfoAboutError(view, getString(R.string.no_internet))
        }
    }

    private fun showInfoAboutError(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
    }

    private fun openLoginActivity() {
        startActivityForResult(
                accountViewModel.buildIntentForLoginActivity(),
                accountViewModel.requestCode
        )

    }

    companion object {
        const val KEY_RECIPE_ID = "recipe firebaseId"
        const val TAG_COMMENTS_FRAGMENT = "comments fragment"

        fun getInstance(recipeId: Long): CommentFragment {
            val fragment = CommentFragment()
            val bundle = Bundle()
            bundle.putLong(KEY_RECIPE_ID, recipeId)
            fragment.arguments = bundle
            return fragment
        }
    }

}