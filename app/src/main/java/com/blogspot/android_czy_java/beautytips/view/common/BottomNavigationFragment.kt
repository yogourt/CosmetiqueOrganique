package com.blogspot.android_czy_java.beautytips.view.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.blogspot.android_czy_java.beautytips.R
import com.blogspot.android_czy_java.beautytips.view.account.AccountActivityFragment
import com.blogspot.android_czy_java.beautytips.view.notification.NotificationFragment
import com.blogspot.android_czy_java.beautytips.view.recipe.MainActivityFragment
import com.blogspot.android_czy_java.beautytips.view.search.SearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_main_bottom_navigation.view.bottom_navigation

class BottomNavigationFragment : AppFragment() {

    private lateinit var navigation: BottomNavigationView

    private var searchFragment = SearchFragment()

    private val indexToIdMap = mapOf(
            Pair(R.id.menu_home, 0),
            Pair(R.id.menu_account, 1),
            Pair(R.id.menu_notifications, 2),
            Pair(R.id.menu_search, 3)
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
            selectItem()
        }
    }

    private fun selectItem() {
        if (searchFragment.isVisible) {
            selectItem(R.id.menu_search)
        } else {
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
            is NotificationFragment -> selectItem(R.id.menu_notifications)
            is SearchFragment -> selectItem(R.id.menu_search)
        }
    }

    private fun addOnClickListener() {
        navigation.setOnNavigationItemSelectedListener { item ->

            val fragment = getCurrentFragment(item)
            if (fragment != null) {
                when (fragment) {
                    is AppBottomSheetDialogFragment -> showBottomSheetDialogFragment(fragment)
                    else -> replaceFragment(fragment)
                }
            }
            true
        }
    }

    private fun showBottomSheetDialogFragment(fragment: BottomSheetDialogFragment) {
        fragmentManager?.let {
            fragment.apply {
                show(it, SearchFragment.TAG_SEARCH_FRAGMENT)
                it.executePendingTransactions()
                dialog?.setOnCancelListener {
                    selectItem()
                }
            }
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

    private fun getCurrentFragment(item: MenuItem): Fragment? {
        return when (item.itemId) {
            R.id.menu_home -> MainActivityFragment()
            R.id.menu_account -> AccountActivityFragment()
            R.id.menu_notifications -> NotificationFragment()
            R.id.menu_search -> searchFragment
            else -> null
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        fragmentManager?.beginTransaction()?.replace(
                R.id.main_container,
                fragment)?.addToBackStack(null)?.commit()
    }

}
