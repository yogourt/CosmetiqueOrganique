package com.blogspot.android_czy_java.beautytips.view.listView.view


import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


import com.blogspot.android_czy_java.beautytips.R
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView


import butterknife.BindView
import butterknife.ButterKnife
import com.blogspot.android_czy_java.beautytips.view.common.AppFragment

class AdBannerFragment : AppFragment() {

    @BindView(R.id.adView)
    lateinit var mAdBanner: AdView

    private var adHandler = Handler()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.layout_ad_banner, container, false)

        ButterKnife.bind(this, view)

        return view
    }

    override fun onStart() {
        super.onStart()
        prepareAdView()
    }

    private fun prepareAdView() {

            val adRequest = AdRequest.Builder().build()
            mAdBanner.loadAd(adRequest)
    }

}
