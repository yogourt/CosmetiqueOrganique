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

    private lateinit var navigation: BottomNavigationView

    private val indexToIdMap = mapOf(
            Pair(R.id.menu_home, 0),
            Pair(R.id.menu_account, 1),
            Pair(R.id.menu_search, 2)
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_main_bottom_navigation,
                container, false)

        if (fragmentManager?.findFragmentById(R.id.main_container) == null) {
            addMainFragment()
        }

        navigation = view.bottom_navigation
        prepareBottomNavigation()
        addBackStackChangeListener()

        return view
    }

    private fun prepareBottomNavigation() {
        navigation.selectedItemId = R.id.menu_home
        addOnClickListener()
    }

    private fun addBackStackChangeListener() {
        fragmentManager?.addOnBackStackChangedListener {
            fragmentManager?.findFragmentById(R.id.main_container)?.let { selectItem(it) }
        }
    }

    private fun selectItem(id: Int) {
        indexToIdMap[id]?.let { navigation.menu.getItem(it).isChecked = true; }
    }

    private fun selectItem(fragment: Fragment) {
        when (fragment) {
            is MainActivityFragment -> selectItem(R.id.menu_home)
            is AccountActivityFragment -> selectItem(R.id.menu_account)
        }
    }

    private fun addOnClickListener() {
        navigation.setOnNavigationItemSelectedListener { item ->

            val fragment = getCurrentFragment(item)
            if (fragment != null) {
                replaceFragment(fragment)
            }
            true
        }
    }

    private fun addMainFragment() {
        val fragment = MainActivityFragment()
        fragmentManager?.beginTransaction()?.add(
                R.id.main_container,
                fragment)
                ?.addToBackStack(null)
                ?.commit()
    }

    private fun getCurrentFragment(item: MenuItem): AppFragment? {
        return when (item.itemId) {
            R.id.menu_home -> MainActivityFragment()
            R.id.menu_account -> AccountActivityFragment()
            else -> null
        }
    }

    private fun replaceFragment(fragment: AppFragment) {
        fragmentManager?.beginTransaction()?.replace(
                R.id.main_container,
                fragment)?.addToBackStack(null)?.commit()
    }

}
