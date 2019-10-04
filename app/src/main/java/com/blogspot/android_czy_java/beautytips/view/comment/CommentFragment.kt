package com.blogspot.android_czy_java.beautytips.view.comment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blogspot.android_czy_java.beautytips.R
import com.blogspot.android_czy_java.beautytips.database.comment.CommentModel
import com.blogspot.android_czy_java.beautytips.view.common.AppBottomSheetDialogFragment
import com.blogspot.android_czy_java.beautytips.viewmodel.GenericUiModel
import com.blogspot.android_czy_java.beautytips.viewmodel.comments.CommentsViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.fragment_comments.view.*


class CommentFragment : AppBottomSheetDialogFragment() {

    @Inject
    lateinit var viewModel: CommentsViewModel

    private var recipeId: Long = 1

    private lateinit var loadingIndicator: ProgressBar
    private lateinit var commentList: RecyclerView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_comments, container, false)

        loadingIndicator = view.loading_indicator
        commentList = view.comments_list

        expand()

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

    private fun render(uiModel: GenericUiModel<List<CommentModel>>?) {
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

    private fun prepareCommentList(comments: List<CommentModel>) {
        commentList.apply {
            adapter = CommentListAdapter(comments)
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, true)
        }
    }

    private fun hideLoadingIndicator() {
        loadingIndicator.visibility = View.INVISIBLE
    }

    private fun showLoadingIndicator() {
        loadingIndicator.visibility = View.VISIBLE
    }

    companion object {
        const val KEY_RECIPE_ID = "recipe id"
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