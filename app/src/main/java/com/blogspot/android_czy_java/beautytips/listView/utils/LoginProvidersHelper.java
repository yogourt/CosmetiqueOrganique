package com.blogspot.android_czy_java.beautytips.view.listView.utils;

import android.net.Uri;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import static com.blogspot.android_czy_java.beautytips.view.listView.firebase.FirebaseLoginHelper.getUserId;

public class LoginProvidersHelper {

    public static String saveProvidedPhoto(Uri photoUrl) {
        List<? extends UserInfo> providerData =  FirebaseAuth.getInstance().
                getCurrentUser().getProviderData();

        for (UserInfo user:providerData) {

            //enlarge and return facebook provided photo
            if (user.getProviderId().equals("facebook.com")) {
                Uri newPhotoUrl = photoUrl.buildUpon().
                        appendQueryParameter("height", "200").build();
                FirebaseDatabase.getInstance().getReference("userPhotos")
                        .child(getUserId()).setValue(newPhotoUrl.toString());
                return newPhotoUrl.toString();

            }
            //enlarge and return google provided photo
            else if(user.getProviderId().equals("google.com")) {
                String newPhotoUrl = photoUrl.toString();
                newPhotoUrl = newPhotoUrl.
                        replace("s96-c", "s200-c");
                FirebaseDatabase.getInstance().getReference("userPhotos")
                        .child(getUserId()).setValue(newPhotoUrl);
                return newPhotoUrl;
            }
        }
        FirebaseDatabase.getInstance().getReference("userPhotos")
                .child(getUserId()).setValue(photoUrl.toString());
        return photoUrl.toString();
    }
}
