package com.blogspot.android_czy_java.beautytips.detail.firebase;

import android.support.annotation.NonNull;

import com.blogspot.android_czy_java.beautytips.detail.model.Comment;
import com.blogspot.android_czy_java.beautytips.listView.firebase.FirebaseLoginHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

import timber.log.Timber;

public class

CommentsFirebaseHelper {

    public static void saveComment(final String recipeId, final String commentDesc) {

        final String authorId = FirebaseLoginHelper.getUserId();

        FirebaseDatabase.getInstance().
                getReference("userNicknames/" + authorId).addListenerForSingleValueEvent
                (new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String authorNickname = String.valueOf(dataSnapshot.getValue());

                String timestamp = String.valueOf(Calendar.getInstance().getTimeInMillis());

                Timber.d("tipList/" + recipeId + "/comments/" +  timestamp);
                Comment comment = new Comment(authorNickname, authorId, commentDesc, false);

                FirebaseDatabase.getInstance().getReference(
                        "tips/" + recipeId + "/comments/" +  timestamp).setValue(comment);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


    }
}
