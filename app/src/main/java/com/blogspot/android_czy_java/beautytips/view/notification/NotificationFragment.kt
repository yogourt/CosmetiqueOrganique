package com.blogspot.android_czy_java.beautytips.view.notification

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blogspot.android_czy_java.beautytips.R
import com.blogspot.android_czy_java.beautytips.database.notification.NotificationModel
import com.blogspot.android_czy_java.beautytips.database.user.UserModel
import com.blogspot.android_czy_java.beautytips.view.IntentDataKeys
import com.blogspot.android_czy_java.beautytips.view.common.AppBottomSheetDialogFragment
import com.blogspot.android_czy_java.beautytips.view.detail.DetailActivity
import com.blogspot.android_czy_java.beautytips.view.notification.callback.NotificationListCallback
import com.blogspot.android_czy_java.beautytips.viewmodel.GenericUiModel
import com.blogspot.android_czy_java.beautytips.viewmodel.account.AccountViewModel
import com.blogspot.android_czy_java.beautytips.viewmodel.detail.DetailActivityViewModel
import com.blogspot.android_czy_java.beautytips.viewmodel.notification.NotificationViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_notification.view.*
import kotlinx.android.synthetic.main.layout_info_for_anonymous.info_for_anonymous
import javax.inject.Inject

class NotificationFragment : AppBottomSheetDialogFragment(), NotificationListCallback {

    @Inject
    lateinit var viewModel: NotificationViewModel

    @Inject
    lateinit var accountViewModel: AccountViewModel

    @Inject
    lateinit var activityViewModel: DetailActivityViewModel

    private lateinit var notifications: RecyclerView
    private lateinit var loadingIndicator: ProgressBar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_notification, container, false)

        expand()
        prepareLayout(view)

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)

        accountViewModel.userLiveData.observe(this, Observer { handleUserState(it) })
        accountViewModel.init()
    }

    private fun prepareLayout(view: View) {
        notifications = view.notifications
        loadingIndicator = view.loading_indicator

        view.login_button.setOnClickListener { handleLoginClick(view.info_for_anonymous) }
    }

    private fun handleUserState(uiModel: GenericUiModel<UserModel>?) {
        if (uiModel == null) {
            info_for_anonymous?.apply { visibility = View.VISIBLE }

        } else {
            info_for_anonymous?.apply { visibility = View.INVISIBLE }

            when (uiModel) {
                is GenericUiModel.LoadingSuccess -> {
                    viewModel.notificationLiveData.observe(this, Observer { render(it) })
                    viewModel.init()
                }
            }
        }
    }

    private fun render(uiModel: GenericUiModel<List<NotificationModel>>?) {

        when (uiModel) {
            is GenericUiModel.LoadingSuccess -> {
                loadingIndicator.visibility = View.GONE
                prepareNotificationList(uiModel.data)
            }

            is GenericUiModel.StatusLoading -> {
                loadingIndicator.visibility = View.VISIBLE
            }
            is GenericUiModel.LoadingError -> {
                loadingIndicator.visibility = View.GONE
                showInfoAboutError(notifications, uiModel.message)
            }
        }
    }

    private fun prepareNotificationList(data: List<NotificationModel>) {
        view?.notifications?.apply {
            adapter = NotificationAdapter(data, this@NotificationFragment)
            layoutManager = LinearLayoutManager(context,
                    RecyclerView.VERTICAL, true)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == accountViewModel.requestCode) {
            if (resultCode == Activity.RESULT_OK) {
                accountViewModel.loginUser()
            } else {
                showInfoAboutError(notifications, getString(R.string.error_msg_common))
            }
        } else super.onActivityResult(requestCode, resultCode, data)
    }


    override fun onRecipeClick(recipeId: Long) {
        if (isTablet) {
            activityViewModel.chosenItemId = recipeId
        } else {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(IntentDataKeys.KEY_RECIPE_ID, recipeId)
            startActivity(intent)
        }
        dismiss()
    }

    override fun makeNotificationSeen(notificationId: Int) {
        viewModel.makeNotificationSeen(notificationId)
    }


}