package com.ig.igtradinggame.ui;

import android.support.v4.app.Fragment;

import butterknife.Unbinder;


public abstract class BaseFragment extends Fragment {
    protected Unbinder unbinder;

    public BaseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onDestroyView() {
        if (unbinder != null) {
            unbinder.unbind();
        }
        super.onDestroyView();
    }
}
