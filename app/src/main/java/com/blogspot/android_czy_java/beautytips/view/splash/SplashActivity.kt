package com.blogspot.android_czy_java.beautytips.view.splash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.blogspot.android_czy_java.beautytips.R
import com.blogspot.android_czy_java.beautytips.view.recipe.MainActivity
import com.blogspot.android_czy_java.beautytips.viewmodel.GenericUiModel
import com.blogspot.android_czy_java.beautytips.viewmodel.splash.SplashViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.android.AndroidInjection
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import kotlinx.android.synthetic.main.activity_splash.*
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {

    companion object {
        const val KEY_FIRST_FETCH_COMPLETED = "first fetch completed"
    }

    @Inject
    lateinit var viewModel: SplashViewModel

    private lateinit var networkNeededSnackbar: Snackbar
    private lateinit var splashInfo: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        AndroidInjection.inject(this)

        networkNeededSnackbar = Snackbar.make(
                splash_layout,
                getString(R.string.no_internet_data_loading),
                Snackbar.LENGTH_INDEFINITE
        )


        setStatusBarTransparent()

        startMainActivityIfFirstFetchNotNeeded()

        splashInfo = splash_info

        viewModel.networkLiveData.observe(this, Observer { handleNetworkChange(it) })
        viewModel.fetchSuccessLiveData.observe(this, Observer { handleFetchResult(it) })
        viewModel.init()
    }

    private fun startMainActivityIfFirstFetchNotNeeded() {
        if (getPreferences(MODE_PRIVATE).contains(KEY_FIRST_FETCH_COMPLETED)) {
            startMainActivity()
        }
    }

    private fun handleFetchResult(uiModel: GenericUiModel<Boolean>) {
        when (uiModel) {
            is GenericUiModel.LoadingSuccess -> {
                getPreferences(MODE_PRIVATE).edit().putBoolean(KEY_FIRST_FETCH_COMPLETED, true).apply()
                startMainActivity()
            }
            is GenericUiModel.StatusLoading -> {
                splashInfo.visibility = View.VISIBLE

            }
            is GenericUiModel.LoadingError -> {
                splashInfo.visibility = View.INVISIBLE
                viewModel.retry()
            }
        }
    }

    private fun handleNetworkChange(isConnected: Boolean) {
        if (isConnected) {
            onNetworkAccessed()
        } else {
            networkNeededSnackbar.show()
        }
    }

    private fun startMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun onNetworkAccessed() {
        networkNeededSnackbar.dismiss()
        viewModel.onNetworkAccessed()
    }

    private fun setStatusBarTransparent() {
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }

    public override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase))
    }
}