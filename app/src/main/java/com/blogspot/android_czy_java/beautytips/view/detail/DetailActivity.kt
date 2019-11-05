package com.blogspot.android_czy_java.beautytips.view.detail

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.lifecycle.Observer
import com.blogspot.android_czy_java.beautytips.R
import com.blogspot.android_czy_java.beautytips.appUtils.categories.labels.CategoryLabel
import com.blogspot.android_czy_java.beautytips.appUtils.categories.labels.SubcategoryLabel
import com.blogspot.android_czy_java.beautytips.appUtils.orders.Order
import com.blogspot.android_czy_java.beautytips.usecase.recipe.RecipeRequest
import com.blogspot.android_czy_java.beautytips.view.IntentDataKeys
import com.blogspot.android_czy_java.beautytips.view.detail.callback.UserListChooserCallback
import com.blogspot.android_czy_java.beautytips.view.recipe.OneListFragment
import com.blogspot.android_czy_java.beautytips.viewmodel.GenericUiModel
import com.blogspot.android_czy_java.beautytips.viewmodel.account.AccountViewModel
import com.blogspot.android_czy_java.beautytips.viewmodel.detail.HeaderData
import com.blogspot.android_czy_java.beautytips.viewmodel.detail.HeaderHeartData
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

    @Inject
    lateinit var accountViewModel: AccountViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.motion_layout_activity_detail_1)

        AndroidInjection.inject(this)

        setStatusBarTransparent()

        headerViewModel.headerFragmentLiveData.observe(this, Observer { render(it) })
        headerViewModel.headerHeartLiveData.observe(this, Observer { renderHeart(it) })

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

    private fun renderHeart(uiModel: GenericUiModel<HeaderHeartData>) {

        when (uiModel) {
            is GenericUiModel.LoadingSuccess -> {
                prepareHeartAndFavNum(uiModel.data)
            }

            is GenericUiModel.StatusLoading -> {
                heart.setOnClickListener { }
            }

            is GenericUiModel.LoadingError -> {
                showInfoAboutError(uiModel.message)
                heart.setOnClickListener {
                    handleHeartClick()
                }
            }
        }

    }

    private fun prepareContent(data: HeaderData) {

        loadImage(data)
        findViewById<TextView>(R.id.title).text = data.title
        prepareButtons(data)
    }

    private fun prepareHeartAndFavNum(data: HeaderHeartData) {
        fav_num.text = data.favNum.toString()
        setHeartIcon(data.inFav)

        heart.setOnClickListener {
            handleHeartClick()
        }

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

        bookmark.setOnClickListener {
            handleSaveToListClick()
        }
        save_to.setOnClickListener {
            handleSaveToListClick()
        }

    }

    private fun handleHeartClick() {
        if (accountViewModel.isUserAnonymous()) {
            showLoginSnackbar()
        } else {
            headerViewModel.handleHeartClick()
        }
    }

    private fun handleSaveToListClick() {
        if (accountViewModel.isUserAnonymous()) {
            showLoginSnackbar()
        } else {
            showUserListChooser()
        }
    }

    private fun setHeartIcon(inFav: Boolean) {
        if (inFav) {
            heart.setImageDrawable(getDrawable(R.drawable.ic_heart))
        } else {
            heart.setImageDrawable(getDrawable(R.drawable.ic_heart_stroked))
        }
    }

    private fun showUserListChooser() {
        UserListChooserDialogFragment().show(supportFragmentManager, UserListChooserDialogFragment.TAG)
    }

    private fun showInfoAboutError(error: String) {
        Snackbar.make(scrollable_content, error, Snackbar.LENGTH_LONG).show()
    }

    private fun showLoginSnackbar() {

        Snackbar.make(layout_detail, R.string.login_prompt, Snackbar.LENGTH_LONG)
                .setAction(R.string.login) {
                    handleLoginClick()
                }
                .show()
    }

    private fun handleLoginClick() {
        if (accountViewModel.isNetworkConnection()) {
            openLoginActivity()
        } else {
            showInfoAboutError(getString(R.string.no_internet))
        }
    }

    private fun openLoginActivity() {
        startActivityForResult(
                accountViewModel.buildIntentForLoginActivity(),
                accountViewModel.requestCode
        )

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == accountViewModel.requestCode) {
            if (resultCode == Activity.RESULT_OK) {
                accountViewModel.loginUser()
            } else {
                showInfoAboutError(getString(R.string.error_msg_common))
            }
        } else super.onActivityResult(requestCode, resultCode, data)
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
