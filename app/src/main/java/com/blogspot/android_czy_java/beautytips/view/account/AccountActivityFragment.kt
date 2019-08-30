package com.blogspot.android_czy_java.beautytips.view.account

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.blogspot.android_czy_java.beautytips.R
import com.blogspot.android_czy_java.beautytips.view.common.AppFragment
import com.blogspot.android_czy_java.beautytips.viewmodel.account.AccountViewModel
import com.firebase.ui.auth.AuthUI
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.layout_info_for_anonymous.view.*
import javax.inject.Inject

class AccountActivityFragment: AppFragment() {

    @Inject
    lateinit var viewModel: AccountViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_activity_account,
                container, false)

        prepareInfoForAnonymous(view)

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    private fun prepareInfoForAnonymous(view: View) {
        view.login_button.setOnClickListener {
            openLoginActivity()
        }
    }

    private fun openLoginActivity() {
        if(activity != null) {
            activity!!.startActivityForResult(AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setIsSmartLockEnabled(false)
                    .setAvailableProviders(viewModel.login_providers)
                    .setTheme(R.style.LoginStyle)
                    .setLogo(R.drawable.withoutback)
                    .build(), viewModel.request_code_login)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == viewModel.request_code_login)

        else super.onActivityResult(requestCode, resultCode, data)
    }

}