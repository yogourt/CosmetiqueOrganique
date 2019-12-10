package com.blogspot.android_czy_java.beautytips.usecase.account

import android.net.Uri
import com.blogspot.android_czy_java.beautytips.database.error.ErrorModel
import com.blogspot.android_czy_java.beautytips.database.user.UserModel
import com.blogspot.android_czy_java.beautytips.repository.forViewModels.error.ErrorRepository
import com.blogspot.android_czy_java.beautytips.repository.forViewModels.user.UserRepository
import com.blogspot.android_czy_java.beautytips.usecase.common.UseCaseInterface
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import io.reactivex.Single
import io.reactivex.SingleEmitter
import org.json.JSONObject

class UpdateUserDataInFirebaseUseCase(private val errorRepository: ErrorRepository,
                                      private val userRepository: UserRepository) : UseCaseInterface<String, Boolean> {

    private lateinit var user: UserModel
    private lateinit var error: ErrorModel

    override fun execute(request: String): Single<Boolean> {

        error = ErrorModel("Users", request)
        /*
        this is indicator that this method is running, if it completes, the error will be deleted
        this error meaning: data in local db is fresher than firebase data
         */
        insertError()

        return Single.create { emitter ->
            handleUserPresenceInLocalDb(request, emitter)

            if (photoIsUploaded()) {
                pushUserDataToFirebase(emitter)
            } else {
                uploadPhotoAndPushUserDataToFirebase(emitter)
            }


        }
    }


    private fun handleUserPresenceInLocalDb(request: String, emitter: SingleEmitter<Boolean>) {
        userRepository.getUserByFirebaseId(request).apply {
            if (this == null)
                onError(emitter)
            else {
                user = this
            }
        }
    }

    private fun photoIsUploaded() = user.photo.startsWith("https://")

    private fun pushUserDataToFirebase(emitter: SingleEmitter<Boolean>) {
        FirebaseDatabase.getInstance()
                .getReference("users/${user.id}")
                .setValue(createJson())
                .addOnSuccessListener {
                    onSuccess(emitter)
                }.addOnFailureListener {
                    onError(emitter)
                }
    }

    private fun uploadPhotoAndPushUserDataToFirebase(emitter: SingleEmitter<Boolean>) {
        val imageRef = getImageRef()
        val task = imageRef.putFile(Uri.parse(user.photo))
        task.addOnSuccessListener {
            imageRef.downloadUrl.addOnSuccessListener {
                updateLocalUser(it)
                pushUserDataToFirebase(emitter)
            }

        }
        task.addOnFailureListener {
            onError(emitter)
        }
    }

    private fun getImageRef() =
            FirebaseStorage.getInstance()
                    .getReference("userPhotos/${user.id}")


    private fun updateLocalUser(it: Uri) {
        userRepository.updateUser(UserModel(user.id, user.nickname, it.toString(), user.about))
    }

    private fun createJson(): JSONObject {
        val json = JSONObject()
        json.put("photo", user.photo)
        json.put("nickname", user.nickname)
        json.put("about", user.about)
        return json
    }

    private fun onError(emitter: SingleEmitter<Boolean>) {
        insertError()
        emitter.onSuccess(false)
    }

    private fun onSuccess(emitter: SingleEmitter<Boolean>) {
        deleteError()
        emitter.onSuccess(true)
    }

    private fun insertError() = errorRepository.saveError(error)
    private fun deleteError() = errorRepository.deleteError(error)
}