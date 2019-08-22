package com.blogspot.android_czy_java.beautytips.view.detail

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import butterknife.BindView
import butterknife.ButterKnife
import com.blogspot.android_czy_java.beautytips.R
import com.blogspot.android_czy_java.beautytips.view.IntentDataKeys
import com.blogspot.android_czy_java.beautytips.viewmodel.GenericUiModel
import com.blogspot.android_czy_java.beautytips.viewmodel.detail.DetailActivityViewModel
import com.blogspot.android_czy_java.beautytips.viewmodel.detail.HeaderData
import com.blogspot.android_czy_java.beautytips.viewmodel.detail.HeaderViewModel
import com.bumptech.glide.Glide
import dagger.android.AndroidInjection
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import javax.inject.Inject

class DetailActivity : AppCompatActivity() {

    @BindView(R.id.image)
    lateinit var image: ImageView

    @BindView(R.id.title)
    lateinit var title: TextView

    @BindView(R.id.category)
    lateinit var category: TextView

    @BindView(R.id.subcategory)
    lateinit var subcategory: TextView

    @Inject
    lateinit var headerViewModel: HeaderViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.motion_layout_activity_detail_1)

        ButterKnife.bind(this)
        AndroidInjection.inject(this)

        setStatusBarTransparent()

        headerViewModel.headerFragmentLiveData.observe(this, Observer { render(it) })

        getRecipeId()?.let { headerViewModel.init(it) }
    }

    private fun getRecipeId() = this.intent?.getLongExtra(IntentDataKeys.KEY_RECIPE_ID, 0)

    private fun render(uiModel: GenericUiModel<HeaderData>) {

        when (uiModel) {
            is GenericUiModel.LoadingSuccess -> prepareContent(uiModel.data)

            is GenericUiModel.LoadingError -> return //TODO: implement
        }
    }

    private fun prepareContent(data: HeaderData) {

        loadImage(data)
        title.text = data.title
        category.text = data.category
        subcategory.text = data.subcategory

    }

    private fun loadImage(data: HeaderData) {

        Glide.with(this).load(data.imageUrl).into(image)
        image.contentDescription = resources.getString(R.string.description_tip_image, data.title)
    }

    private fun setStatusBarTransparent() {
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }

    public override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase))
    }
}
