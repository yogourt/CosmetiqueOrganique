package com.blogspot.android_czy_java.beautytips.appUtils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;

import static com.blogspot.android_czy_java.beautytips.listView.view.MainActivity.RC_PHOTO_PICKER;

public class ExternalStoragePermissionHelper {

    public static final int RC_PERMISSION_EXT_STORAGE = 140;

    public static boolean isPermissionGranted(Context context) {
        int result = ContextCompat.checkSelfPermission(context,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        return (result == PackageManager.PERMISSION_GRANTED);
    }

    public static void askForPermission(Activity activity) {
        ActivityCompat.requestPermissions(activity,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                RC_PERMISSION_EXT_STORAGE);
    }

    public static void showPhotoPicker(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        activity.startActivityForResult(intent, RC_PHOTO_PICKER);
    }

    public static void answerForPermissionResult(Activity activity, int[] grantResults,
                                                 View snackbarView) {
            if(grantResults.length > 0) {
                //permission for external storage granted
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showPhotoPicker(activity);
                }
                //permission for external storage denied
                else {
                    SnackbarHelper.showExtStoragePermissionDenied(snackbarView);
                }
            }

    }

}
