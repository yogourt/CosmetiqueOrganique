package com.blogspot.android_czy_java.beautytips.listView.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blogspot.android_czy_java.beautytips.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class OpeningFragment extends Fragment {


    public OpeningFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_opening, container, false);
    }

}
