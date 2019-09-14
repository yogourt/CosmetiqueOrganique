package com.blogspot.android_czy_java.beautytips.view.account

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.Observer
import com.blogspot.android_czy_java.beautytips.R
import com.blogspot.android_czy_java.beautytips.database.user.UserModel
import com.blogspot.android_czy_java.beautytips.view.common.AppFragment
import com.blogspot.android_czy_java.beautytips.viewmodel.GenericUiModel
import com.blogspot.android_czy_java.beautytips.viewmodel.account.AccountViewModel
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

    private lateinit var infoForAnonymous: View

    private lateinit var loginButton: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_activity_account,
                container, false)


        infoForAnonymous = view.fragment_info_for_anonymous
        loginButton = view.login_button

        prepareInfoForAnonymous()

        viewModel.userLiveData.observe(this, Observer { render(it) })
        viewModel.init()

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }


    private fun prepareInfoForAnonymous() {
        loginButton.setOnClickListener {
            if (viewModel.isNetworkConnection()) {
                openLoginActivity()
            } else {
                showInfoAboutError(getString(R.string.no_internet))
            }
        }
    }

    private fun render(uiModel: GenericUiModel<UserModel>?) {
        if (uiModel != null) {
            infoForAnonymous.visibility = View.INVISIBLE
        } else {
            infoForAnonymous.visibility = View.VISIBLE
        }
        when (uiModel) {
            is GenericUiModel.LoadingSuccess -> {
                loading_indicator.visibility = View.INVISIBLE
            }

            is GenericUiModel.StatusLoading -> {
                loading_indicator.visibility = View.VISIBLE
            }
            is GenericUiModel.LoadingError -> {
                loading_indicator.visibility = View.INVISIBLE
                showInfoAboutError(uiModel.message)
                if (viewModel.isNetworkConnection()) {
                    viewModel.logout()
                }
            }
        }
    }

    private fun openLoginActivity() {
        startActivityForResult(
                viewModel.buildIntentForLoginActivity(),
                viewModel.requestCode
        )

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == viewModel.requestCode) {
            if (resultCode == Activity.RESULT_OK) {
                viewModel.saveFirebaseUserToDatabase()
            } else {
                showInfoAboutError(getString(R.string.error_msg_common))
            }
        } else super.onActivityResult(requestCode, resultCode, data)
    }

    private fun showInfoAboutError(message: String) {
        Snackbar.make(
                fragment_account,
                message,
                Snackbar.LENGTH_LONG
        ).show()
    }

}