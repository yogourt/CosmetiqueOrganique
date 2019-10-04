package com.blogspot.android_czy_java.beautytips.view.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.blogspot.android_czy_java.beautytips.R
import com.blogspot.android_czy_java.beautytips.appUtils.categories.CategoryAll
import com.blogspot.android_czy_java.beautytips.appUtils.categories.CategoryInterface
import com.blogspot.android_czy_java.beautytips.appUtils.categories.labels.CategoryLabel
import com.blogspot.android_czy_java.beautytips.appUtils.orders.Order
import com.blogspot.android_czy_java.beautytips.usecase.search.SearchResultRequest
import com.blogspot.android_czy_java.beautytips.view.common.AppBottomSheetDialogFragment
import com.blogspot.android_czy_java.beautytips.view.recipe.OneListFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_search.view.*
import javax.inject.Inject

class SearchFragment : AppBottomSheetDialogFragment() {

    private var category: CategoryInterface = CategoryAll.SUBCATEGORY_ALL
    private var order = Order.NEW

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        prepareLayout(view)

        expand()
        return view;
    }

    private fun prepareLayout(view: View) {
        prepareSpinners(view)
        view.button_search.setOnClickListener {
            fragmentManager
                    ?.beginTransaction()?.replace(
                            R.id.main_container,
                            OneListFragment.OneSearchRecipeListFragment.getInstance(prepareRequest(view)),
                            OneListFragment.TAG_ONE_LIST_FRAGMENT)
                    ?.addToBackStack(null)
                    ?.commit()
            this.dismiss()
        }
    }

    private fun prepareSpinners(view: View) {
        view.category_spinner.apply {
            setItemsArray(CategoryLabel.values().map { it.label })
            onItemChosenListener = CategorySpinnerListener(view)
        }
        view.subcategory_spinner.onItemChosenListener = SubcategorySpinnerListener()

        view.order_spinner.apply {
            setItemsArray(Order.values().map { it.label })
            onItemChosenListener = OrderSpinnerListener()
        }
    }

    private fun prepareRequest(view: View): SearchResultRequest {
        return SearchResultRequest(
                category,
                order,
                view.title_et.text.toString(),
                "",
                view.keywords_et.text.toString()
        )
    }

    inner class CategorySpinnerListener(private val view: View) : SpinnerListener() {
        override fun onItemChosen(adapterView: AdapterView<*>?,
                                  id: Long) {
            view.subcategory_spinner.apply {
                if (id != 0L) {
                    category = CategoryLabel.values()[id.toInt()].getCategory()
                    setItemsArray(category.subcategories())
                    visibility = View.VISIBLE
                } else {
                    visibility = View.GONE
                    category = CategoryAll.SUBCATEGORY_ALL
                }
            }
        }
    }

    inner class SubcategorySpinnerListener : SpinnerListener() {
        override fun onItemChosen(adapterView: AdapterView<*>?,
                                  id: Long) {
            category = category.newSubcategory(id.toInt())
        }
    }

    inner class OrderSpinnerListener : SpinnerListener() {
        override fun onItemChosen(adapterView: AdapterView<*>?,
                                  id: Long) {
            order = Order.values()[id.toInt()]
        }
    }


    companion object {
        const val TAG_SEARCH_FRAGMENT = "search fragment"
    }
}