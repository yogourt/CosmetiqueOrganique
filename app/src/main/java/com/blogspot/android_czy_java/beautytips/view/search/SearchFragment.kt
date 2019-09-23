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
import com.blogspot.android_czy_java.beautytips.usecase.search.SearchResultInnerRequest
import com.blogspot.android_czy_java.beautytips.view.common.AppFragment
import com.blogspot.android_czy_java.beautytips.viewmodel.search.SearchActivityViewModel
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_search.view.*
import javax.inject.Inject

class SearchFragment : AppFragment() {

    @Inject
    lateinit var viewModel: SearchActivityViewModel

    private var category: CategoryInterface = CategoryAll.SUBCATEGORY_ALL
    private var order = Order.NEW

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        prepareLayout(view)

        return view;
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    private fun prepareLayout(view: View) {
        prepareSpinners(view)
        view.button_search.setOnClickListener { viewModel.search(prepareRequest(view)) }
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

    private fun prepareRequest(view: View): SearchResultInnerRequest {
        return SearchResultInnerRequest(
                category,
                order,
                view.title_et.text.toString(),
                view.author_et.text.toString(),
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
}