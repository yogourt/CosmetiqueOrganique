package com.blogspot.android_czy_java.beautytips.view.common

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.blogspot.android_czy_java.beautytips.R
import com.blogspot.android_czy_java.beautytips.view.account.AccountActivity
import com.blogspot.android_czy_java.beautytips.view.listView.view.MainActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fragment_main_bottom_navigation.view.*

class BottomNavigationFragment : AppFragment() {

    private var selectedItemId = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_main_bottom_navigation,
                container, false)

        prepareBottomNavigation(view.bottom_navigation)

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        getItemToSelect(context)
    }

    private fun prepareBottomNavigation(navigation: BottomNavigationView) {
        selectItem(navigation)
        addOnClickListener(navigation)
    }


    private fun getItemToSelect(context: Context) {
        when (context) {
            is MainActivity -> selectedItemId = R.id.menu_home
            is AccountActivity -> selectedItemId = R.id.menu_account
        }
    }

    private fun selectItem(navigation: BottomNavigationView) {
        navigation.selectedItemId = selectedItemId
    }

    private fun addOnClickListener(navigation: BottomNavigationView) {
        navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_account -> {
                    startActivity(Intent(this.context, AccountActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }
}
