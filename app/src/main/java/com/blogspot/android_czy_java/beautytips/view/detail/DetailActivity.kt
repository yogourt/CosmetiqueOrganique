package com.blogspot.android_czy_java.beautytips.view.detail

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.TextView
import androidx.lifecycle.Observer
import com.blogspot.android_czy_java.beautytips.R
import com.blogspot.android_czy_java.beautytips.appUtils.categories.labels.CategoryLabel
import com.blogspot.android_czy_java.beautytips.appUtils.categories.labels.SubcategoryLabel
import com.blogspot.android_czy_java.beautytips.appUtils.orders.Order
import com.blogspot.android_czy_java.beautytips.usecase.recipe.RecipeRequest
import com.blogspot.android_czy_java.beautytips.view.IntentDataKeys
import com.blogspot.android_czy_java.beautytips.view.recipe.OneListFragment
import com.blogspot.android_czy_java.beautytips.viewmodel.GenericUiModel
import com.blogspot.android_czy_java.beautytips.viewmodel.detail.HeaderData
import com.blogspot.android_czy_java.beautytips.viewmodel.detail.HeaderViewModel
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import dagger.android.AndroidInjection
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import kotlinx.android.synthetic.main.motion_layout_activity_detail_1.*
import javax.inject.Inject

class DetailActivity : AppCompatActivity() {

    @Inject
    lateinit var headerViewModel: HeaderViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.motion_layout_activity_detail_1)

        AndroidInjection.inject(this)

        setStatusBarTransparent()

        headerViewModel.headerFragmentLiveData.observe(this, Observer { render(it) })

        getRecipeId()?.let { headerViewModel.init(it) }
    }

    private fun getRecipeId() = this.intent?.getLongExtra(IntentDataKeys.KEY_RECIPE_ID, 0)

    private fun render(uiModel: GenericUiModel<HeaderData>) {

        when (uiModel) {
            is GenericUiModel.LoadingSuccess -> prepareContent(uiModel.data)

            is GenericUiModel.LoadingError -> {
                showInfoAboutError(uiModel.message)
            }
        }
    }

    private fun prepareContent(data: HeaderData) {

        loadImage(data)
        findViewById<TextView>(R.id.title).text = data.title
        prepareButtons(data)
    }

    private fun prepareButtons(data: HeaderData) {
        category.apply {
            text = data.category
            setOnClickListener {
                val category = CategoryLabel.get(data.category)
                category?.let {
                    startOneRecipeListFragment(data.category, SubcategoryLabel.SUBCATEGORY_ALL.label)
                }
            }
        }
        subcategory.apply {
            text = data.subcategory
            setOnClickListener {
                startOneRecipeListFragment(data.category, data.subcategory)
            }

        }
    }

    private fun showInfoAboutError(error: String) {
        Snackbar.make(scrollable_content, error, Snackbar.LENGTH_LONG).show()
    }

    private fun loadImage(data: HeaderData) {

        Glide.with(this).load(data.imageUrl).into(image)
        image.contentDescription = resources.getString(R.string.description_tip_image, data.title)
    }

    private fun startOneRecipeListFragment(category: String, subcategory: String) {
        val categoryLabel = CategoryLabel.get(category) ?: return
        val request = RecipeRequest(
                CategoryLabel.get(categoryLabel, subcategory),
                Order.NEW)
        supportFragmentManager
                .beginTransaction().replace(
                        R.id.detail_for_list_container,
                        OneListFragment.OneRecipeListFragment.getInstance(request),
                        OneListFragment.TAG_ONE_LIST_FRAGMENT)
                .addToBackStack(null)
                .commit()
    }

    private fun setStatusBarTransparent() {
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }

    public override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase))
    }
}
