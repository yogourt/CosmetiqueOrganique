package com.blogspot.android_czy_java.beautytips.notifications;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import static com.blogspot.android_czy_java.beautytips.listView.firebase.FirebaseLoginHelper.getUserId;

public class NotificationTokenHelper {

    public static void saveUserNotificationToken() {

        //pass user notification token to the server
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(
                new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        String token = task.getResult().getToken();
                        FirebaseDatabase.getInstance().getReference("userTokens").
                                child(getUserId()).setValue(token);
                    }
                });
    }
}
