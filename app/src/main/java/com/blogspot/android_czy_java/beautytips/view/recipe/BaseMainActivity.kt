package com.blogspot.android_czy_java.beautytips.view.listView.view

import android.os.Bundle
import android.widget.FrameLayout
import android.widget.SearchView
import androidx.fragment.app.FragmentActivity

import com.blogspot.android_czy_java.beautytips.R
import com.blogspot.android_czy_java.beautytips.appUtils.ExternalStoragePermissionHelper
import com.blogspot.android_czy_java.beautytips.viewmodel.recipe.MainActivityViewModel
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds

import butterknife.BindView
import butterknife.ButterKnife

import com.blogspot.android_czy_java.beautytips.appUtils.ExternalStoragePermissionHelper.RC_PERMISSION_EXT_STORAGE

abstract class BaseMainActivity : FragmentActivity() {

    @BindView(R.id.main_layout)
    lateinit var layout: FrameLayout

    lateinit var mSearchView: SearchView

    lateinit var viewModel: MainActivityViewModel

    lateinit var interstitialAd: InterstitialAd


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        ButterKnife.bind(this)


        if (savedInstanceState == null) {
            MobileAds.initialize(this, resources.getString(R.string.add_mob_app_id))
        }

        prepareInterstitialAd()

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {

        if (requestCode == RC_PERMISSION_EXT_STORAGE) {
            ExternalStoragePermissionHelper.answerForPermissionResult(this, grantResults,
                    layout)
        }
    }

    private fun prepareInterstitialAd() {
        interstitialAd = InterstitialAd(this)
        interstitialAd.adUnitId = getString(R.string.support_unit_ad_id)
        interstitialAd.loadAd(AdRequest.Builder().build())

        interstitialAd.adListener = object : AdListener() {
            override fun onAdClosed() {
                interstitialAd.loadAd(AdRequest.Builder().build())
            }

        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount <= 1) finish()
        super.onBackPressed()
    }

    companion object {
        const val KEY_QUERY = "query"
    }
}
