package com.blogspot.android_czy_java.beautytips.view.account

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import com.blogspot.android_czy_java.beautytips.R
import com.blogspot.android_czy_java.beautytips.database.user.UserModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.layout_profile_details_edit_popup.view.*
import kotlinx.android.synthetic.main.layout_profile_details_edit_popup.view.nickname
import kotlinx.android.synthetic.main.layout_profile_details_edit_popup.view.photo
import pl.aprilapps.easyphotopicker.DefaultCallback
import pl.aprilapps.easyphotopicker.EasyImage
import pl.aprilapps.easyphotopicker.MediaFile
import pl.aprilapps.easyphotopicker.MediaSource

class ProfileDetailsEditDialogFragment : DialogFragment() {

    private lateinit var user: UserModel

    private lateinit var image: ImageView
    private lateinit var nickname: EditText
    private lateinit var about: EditText

    private lateinit var easyImage: EasyImage
    var imagePath = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.layout_profile_details_edit_popup, container, false)

        user = arguments?.getSerializable(KEY_USER) as UserModel
        if (user == null) dismiss()

        imagePath = user.photo

        prepareUserInfo(view)
        prepareButtons(view)

        easyImage = EasyImage.Builder(view.context)
                .allowMultiple(false)
                .build()

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.fullscreenDialogStyle)
    }

    private fun prepareUserInfo(view: View) {
        view.let {
            image = it.photo
            Glide.with(this).load(user.photo).into(image)
            image.setOnClickListener {
                easyImage.openChooser(this)
            }

            nickname = it.nickname
            about = it.about_me_et

            nickname.text = Editable.Factory.getInstance().newEditable(user.nickname)
            about.text = Editable.Factory.getInstance().newEditable(user.about)
        }
    }

    private fun prepareButtons(view: View) {
        view.apply {
            discard_button.setOnClickListener { dismiss() }
            save_button.setOnClickListener {
                fragmentManager?.findFragmentById(R.id.fragment_profile_details)?.let {
                    (it as ProfileDetailsFragment).saveNewUserInfo(getNewUserInfo())
                }
                dismiss()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        activity?.let {
            easyImage.handleActivityResult(requestCode, resultCode, data, it, PhotoPickerCallback())
        }
    }

    private fun getNewUserInfo() =
            UserModel(user.id, nickname.text.toString(), imagePath, about.text.toString())

    companion object {
        const val FRAGMENT_TAG = "Profile Details Edit Dialog Fragment"
        private const val KEY_USER = "user"

        fun getInstance(user: UserModel): ProfileDetailsEditDialogFragment {
            val fragment = ProfileDetailsEditDialogFragment()
            val bundle = Bundle()
            bundle.putSerializable(KEY_USER, user)
            fragment.arguments = bundle
            return fragment
        }
    }

    inner class PhotoPickerCallback : DefaultCallback() {
        override fun onMediaFilesPicked(imageFiles: Array<MediaFile>, source: MediaSource) {
            imagePath = imageFiles[0].file.absolutePath
        }
    }

}