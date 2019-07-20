package com.blogspot.android_czy_java.beautytips.view.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.blogspot.android_czy_java.beautytips.R

class DetailOverviewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_overview)

        setStatusBarTransparent()
    }

    private fun setStatusBarTransparent() {
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }
}
