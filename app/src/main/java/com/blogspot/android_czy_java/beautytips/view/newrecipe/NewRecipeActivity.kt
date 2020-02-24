package com.blogspot.android_czy_java.beautytips.view.newrecipe

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.blogspot.android_czy_java.beautytips.R
import com.blogspot.android_czy_java.beautytips.appUtils.ExternalStoragePermissionHelper
import com.bumptech.glide.Glide
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import kotlinx.android.synthetic.main.activity_new_recipe.*

class NewRecipeActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_recipe)
    }



    public override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase))
    }
}