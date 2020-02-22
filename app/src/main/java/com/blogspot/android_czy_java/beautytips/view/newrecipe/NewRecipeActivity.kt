package com.blogspot.android_czy_java.beautytips.view.newrecipe

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.blogspot.android_czy_java.beautytips.R
import io.github.inflationx.viewpump.ViewPumpContextWrapper

class NewRecipeActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_recipe)
    }

    public override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase))
    }
}