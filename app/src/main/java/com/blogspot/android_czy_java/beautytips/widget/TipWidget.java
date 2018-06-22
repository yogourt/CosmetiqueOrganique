package com.blogspot.android_czy_java.beautytips.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.RemoteViews;

import com.blogspot.android_czy_java.beautytips.R;
import com.blogspot.android_czy_java.beautytips.detail.view.DetailActivity;
import com.blogspot.android_czy_java.beautytips.listView.view.ListViewAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.Executor;

import timber.log.Timber;

import static com.blogspot.android_czy_java.beautytips.listView.view.ListViewAdapter.KEY_AUTHOR;
import static com.blogspot.android_czy_java.beautytips.listView.view.ListViewAdapter.KEY_ID;

/**
 * Implementation of App Widget functionality.
 */
public class TipWidget extends AppWidgetProvider {

    static void updateAppWidget(final Context context, final AppWidgetManager appWidgetManager,
                                final int appWidgetId) {

        // Construct the RemoteViews object
        final RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.tip_widget);

        final Intent tipIntent = new Intent(context, DetailActivity.class);

        //create bundle for intent
        final Bundle bundle = new Bundle();

        //first we get number of tips
        FirebaseDatabase.getInstance().getReference("tipNumber").addListenerForSingleValueEvent(
                new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long tipNumber = (long)dataSnapshot.getValue();
                Timber.d(String.valueOf(tipNumber));
                int newTip = (int)(Math.random()*tipNumber) + 1;
                Timber.d("new tip: " + String.valueOf(newTip));

                //then we get info about selected tip
                FirebaseDatabase.getInstance().getReference("tipList/-" + String.valueOf(newTip))
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Timber.d("creating bundle");
                                String title = (String)dataSnapshot.child("title").getValue();
                                views.setTextViewText(R.id.widget_title, title);
                                bundle.putString(ListViewAdapter.KEY_TITLE,
                                        title);
                                bundle.putString(ListViewAdapter.KEY_IMAGE,
                                        (String)dataSnapshot.child("image").getValue());
                                bundle.putString(ListViewAdapter.KEY_ID, dataSnapshot.getKey());

                                String author = String.valueOf(dataSnapshot.child("authorId").getValue());
                                if(!author.equals("null"))
                                    bundle.putString(ListViewAdapter.KEY_AUTHOR, author);

                                tipIntent.putExtras(bundle);
                                PendingIntent pendingIntent = PendingIntent.getActivity(context,
                                        0, tipIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                                views.setOnClickPendingIntent(R.id.tip_widget_layout, pendingIntent);

                                //update widget
                                appWidgetManager.updateAppWidget(appWidgetId, views);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Timber.d(databaseError.getMessage());
                            }
                        });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Timber.d(databaseError.getMessage());
            }
        });

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

