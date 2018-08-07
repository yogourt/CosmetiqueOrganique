package com.blogspot.android_czy_java.beautytips.listView.view;

import android.app.DialogFragment;
import android.arch.lifecycle.Observer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.blogspot.android_czy_java.beautytips.R;
import com.blogspot.android_czy_java.beautytips.listView.model.ListItem;
import com.blogspot.android_czy_java.beautytips.listView.utils.recyclerViewUtils.RecyclerViewHelper;
import com.blogspot.android_czy_java.beautytips.listView.utils.recyclerViewUtils.SpacesItemDecoration;
import com.blogspot.android_czy_java.beautytips.listView.view.dialogs.DeleteTipDialog;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;

import java.util.List;

import butterknife.BindView;
import timber.log.Timber;

public class MainActivity extends BaseMainActivity implements ListViewAdapter.PositionListener {


    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private StaggeredGridLayoutManager mLayoutManager;
    private ListViewAdapter mAdapter;

    private DialogFragment mDialogFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        viewModel.getRecyclerViewLiveData().observe(this, new Observer<List<ListItem>>() {
            @Override
            public void onChanged(@Nullable List<ListItem> list) {
                prepareRecyclerView(list);
            }
        });


    }

    private void prepareRecyclerView(List<ListItem> recyclerViewList) {

        //add adapter
        mAdapter = new ListViewAdapter(this, recyclerViewList, this,
                viewModel);

        mRecyclerView.setAdapter(mAdapter);

        //add layout manager
        int columnNum = getResources().getInteger(R.integer.column_num);
        mLayoutManager = RecyclerViewHelper.createLayoutManager(columnNum);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //item decoration is added to make spaces between items in recycler view
        if (mRecyclerView.getItemDecorationCount() == 0)
            mRecyclerView.addItemDecoration(new SpacesItemDecoration(
                    (int) getResources().getDimension(R.dimen.list_padding)));
    }


   /*
       Here is the beginning of interfaces methods
     */


    /*
         Implementation of ListViewAdapter.PositionListener
     */
    @Override
    public void onClickDeleteTip(String tipId) {
        mDialogFragment = new DeleteTipDialog();
        ((DeleteTipDialog) mDialogFragment).setTipId(tipId);
        ((DeleteTipDialog) mDialogFragment).setViewModel(viewModel);
        mDialogFragment.show(getFragmentManager(), TAG_DELETE_TIP_DIALOG);
    }


    /*
       Implementation of NicknamePickerDialog.NicknamePickerDialogListener
     */
    @Override
    public void onDialogSaveButtonClick(String nickname) {
        super.onDialogSaveButtonClick(nickname);
        handleDynamicLink();
    }


    @Override
    public void handleDynamicLink() {
        if (getIntent() == null) return;

        FirebaseDynamicLinks.getInstance().getDynamicLink(getIntent())
                .addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
                    @Override
                    public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                        Uri deepLink = null;
                        if (pendingDynamicLinkData != null) {
                            deepLink = pendingDynamicLinkData.getLink();
                            Timber.d("deep link: " + deepLink);
                            String link = deepLink.getQueryParameter("link");

                            //this is done because sometimes deepLink contains all dynamic
                            // link and sometimes not
                            Uri linkUrl;
                            if (link != null) linkUrl = Uri.parse(link);
                            else linkUrl = deepLink;

                            final String tipId = linkUrl.getLastPathSegment();

                            setIntent(null);

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if (mAdapter != null) mAdapter.openTipWithId(tipId);
                                    Timber.d("open tip after delay");
                                }
                            }, 180);

                        }
                    }
                }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
    }
}
