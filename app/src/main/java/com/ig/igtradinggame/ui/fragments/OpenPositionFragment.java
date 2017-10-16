package com.ig.igtradinggame.ui.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ig.igtradinggame.R;

import butterknife.ButterKnife;

public class OpenPositionFragment extends BaseFragment {
    public OpenPositionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_open_position, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

}
