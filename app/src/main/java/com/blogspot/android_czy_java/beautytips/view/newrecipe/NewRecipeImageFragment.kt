package com.blogspot.android_czy_java.beautytips.view.newrecipe

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.blogspot.android_czy_java.beautytips.R
import com.blogspot.android_czy_java.beautytips.view.common.*
import com.blogspot.android_czy_java.beautytips.view.extensions.PERMISSIONS_REQUEST_CODE
import com.blogspot.android_czy_java.beautytips.view.extensions.arePermissionsGranted
import com.blogspot.android_czy_java.beautytips.view.extensions.isAnyPermissionGranted
import com.blogspot.android_czy_java.beautytips.view.extensions.requestPermissionsCompat
import com.blogspot.android_czy_java.beautytips.viewmodel.newrecipe.NewRecipeActivityViewModel
import com.bumptech.glide.Glide
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_new_recipe_image.view.*
import pl.aprilapps.easyphotopicker.DefaultCallback
import pl.aprilapps.easyphotopicker.EasyImage
import pl.aprilapps.easyphotopicker.MediaFile
import pl.aprilapps.easyphotopicker.MediaSource
import javax.inject.Inject


class NewRecipeImageFragment : AppFragment() {

    private lateinit var image: ImageView
    private lateinit var appIcon: ImageView


    private lateinit var easyImage: EasyImage

    @Inject
    lateinit var viewModel: NewRecipeActivityViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_new_recipe_image, container, false)

        image = view.image
        appIcon = view.app_icon

        easyImage = EasyImage.Builder(view.context)
                .allowMultiple(false)
                .build()

        if (viewModel.imagePath.isNotEmpty()) {
            loadImageAndHideAppIcon()
        }
        prepareButtons(view)

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    private fun prepareButtons(view: View) {
        view.button_add_photo.setOnClickListener {
            if (arePermissionsGranted()) {
                easyImage.openChooser(this)
            } else {
                requestPermissionsCompat();
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        activity?.let {
            easyImage.handleActivityResult(requestCode, resultCode, data, it, PhotoPickerCallback())
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSIONS_REQUEST_CODE && isAnyPermissionGranted()) {
            easyImage.openChooser(this)
        }
    }

    private fun loadImageAndHideAppIcon() {
        Glide.with(image).load(viewModel.imagePath).into(image)
        hideAppIcon()
    }

    private fun hideAppIcon() {
        if (appIcon.visibility == View.VISIBLE) {
            appIcon.visibility = View.GONE
        }
    }

    inner class PhotoPickerCallback : DefaultCallback() {
        override fun onMediaFilesPicked(imageFiles: Array<MediaFile>, source: MediaSource) {
            viewModel.imagePath = imageFiles[0].file.absolutePath
            loadImageAndHideAppIcon()
        }
    }

}