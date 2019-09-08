package com.blogspot.android_czy_java.beautytips.view.account

import android.content.Context
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
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_profile_details.view.*
import kotlinx.android.synthetic.main.layout_user_info.*
import javax.inject.Inject

class ProfileDetailsFragment : AppFragment() {

    @Inject
    lateinit var accountViewModel: AccountViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_profile_details,
                container, false)

        prepareButtons(view)

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
        accountViewModel.userLiveData.observe(this, Observer { render(it) })
    }

    private fun render(uiModel: GenericUiModel<UserModel>?) {

        if (uiModel == null) {
            prepareLayoutForAnonymous()
        }
        when (uiModel) {
            is GenericUiModel.LoadingSuccess -> {
                prepareLayoutForUser(uiModel.data)
            }

            is GenericUiModel.StatusLoading -> {

            }
            is GenericUiModel.LoadingError -> {
                prepareLayoutForAnonymous()
            }
        }
    }

    private fun prepareButtons(view: View) {
        view.apply {
            logout_button.setOnClickListener { accountViewModel.logout() }
        }
    }

    private fun prepareLayoutForUser(user: UserModel) {
        view?.apply {
            Glide.with(this).load(user.photo).into(photo)
            nickname.text = user.nickname
            logout_button.visibility = View.VISIBLE
        }
    }

    private fun prepareLayoutForAnonymous() {
        view?.apply {
            Glide.with(this).load(R.color.bluegrey700).into(photo)
            nickname.text = getString(R.string.label_anonymous)
            logout_button.visibility = View.INVISIBLE
        }
    }

}