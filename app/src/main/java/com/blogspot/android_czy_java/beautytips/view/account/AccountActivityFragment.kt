package com.blogspot.android_czy_java.beautytips.view.account

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.blogspot.android_czy_java.beautytips.R
import com.blogspot.android_czy_java.beautytips.database.user.UserModel
import com.blogspot.android_czy_java.beautytips.view.common.AppFragment
import com.blogspot.android_czy_java.beautytips.viewmodel.GenericUiModel
import com.blogspot.android_czy_java.beautytips.viewmodel.account.AccountViewModel
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_activity_account.*
import kotlinx.android.synthetic.main.fragment_activity_account.view.*
import kotlinx.android.synthetic.main.layout_info_for_anonymous.view.*
import kotlinx.android.synthetic.main.layout_user_info.*
import javax.inject.Inject

class AccountActivityFragment : AppFragment() {

    @Inject
    lateinit var viewModel: AccountViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_activity_account,
                container, false)


        if (!viewModel.isUserAnonymous()) {
            makeUserListInvisible(view)
            prepareInfoForAnonymous(view)
        }

        viewModel.userLiveData.observe(this, Observer { render(it) })
        viewModel.init()

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == viewModel.requestCode && resultCode == Activity.RESULT_OK) {
            viewModel.reloadUser()
        } else super.onActivityResult(requestCode, resultCode, data)
    }

    private fun makeUserListInvisible(view: View) {
        view.fragment_user_lists.visibility = View.INVISIBLE
    }

    private fun prepareInfoForAnonymous(view: View) {
        view.login_button.setOnClickListener {
            openLoginActivity()
        }
    }

    private fun render(uiModel: GenericUiModel<UserModel>) {
        when (uiModel) {
            is GenericUiModel.LoadingSuccess -> {
                prepareUserInfo(uiModel.data)
            }
            is GenericUiModel.StatusLoading -> {
                loading_indicator.visibility = View.VISIBLE
            }
            is GenericUiModel.LoadingError -> {
                showInfoAboutError(uiModel.message)
            }
        }
    }

    private fun openLoginActivity() {
        if (activity != null) {
            activity!!.startActivityForResult(
                    viewModel.buildIntentForLoginActivity(),
                    viewModel.requestCode
            )
        }
    }

    private fun prepareUserInfo(user: UserModel) {
        Glide.with(this).load(user.photo).into(photo)
        nickname.text = user.nickname
    }

    private fun showInfoAboutError(message: String) {
        Snackbar.make(
                fragment_account,
                message,
                Snackbar.LENGTH_LONG
        ).setAction(
                R.string.retry
        ) { retryDataLoading() }
                .show()
    }

    private fun retryDataLoading() = viewModel.reloadUser()

}