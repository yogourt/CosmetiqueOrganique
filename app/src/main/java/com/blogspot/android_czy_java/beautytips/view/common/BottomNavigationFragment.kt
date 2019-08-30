package com.blogspot.android_czy_java.beautytips.view.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.blogspot.android_czy_java.beautytips.R
import com.blogspot.android_czy_java.beautytips.view.account.AccountActivityFragment
import com.blogspot.android_czy_java.beautytips.view.listView.view.MainActivityFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fragment_main_bottom_navigation.view.bottom_navigation

class BottomNavigationFragment : AppFragment() {

    private var selectedItemId = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_main_bottom_navigation,
                container, false)

        if (fragmentManager?.findFragmentById(R.id.main_container) == null) {
            addMainFragment()
        }

        prepareBottomNavigation(view.bottom_navigation)

        return view
    }

    private fun prepareBottomNavigation(navigation: BottomNavigationView) {
        selectItem(navigation)
        addOnClickListener(navigation)
    }

    private fun selectItem(navigation: BottomNavigationView) {
        navigation.selectedItemId = selectedItemId
    }

    private fun addOnClickListener(navigation: BottomNavigationView) {
        navigation.setOnNavigationItemSelectedListener { item ->

            val fragment = getCurrentFragment(item)
            if (fragment != null) {
                replaceFragment(fragment)
            }
            true
        }
    }

    private fun addMainFragment() {
        fragmentManager?.beginTransaction()?.add(
                R.id.main_container,
                MainActivityFragment())?.commit()
    }

    private fun getCurrentFragment(item: MenuItem): Fragment? {
        return when (item.itemId) {
            R.id.menu_home -> MainActivityFragment()
            R.id.menu_account -> AccountActivityFragment()
            else -> null
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        fragmentManager?.beginTransaction()?.replace(
                R.id.main_container,
                fragment)?.commit()
    }

}
