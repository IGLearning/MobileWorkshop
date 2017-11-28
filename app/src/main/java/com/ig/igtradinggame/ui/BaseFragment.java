package com.ig.igtradinggame.ui;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.View;

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

    protected void showMessage(String message, boolean isError) {
        View view = getView();
        if (view != null) {
            Snackbar snackbar = Snackbar.make(getView(), message, Snackbar.LENGTH_SHORT);

            if (isError) {
                View snackView = snackbar.getView();
                snackView.setBackgroundColor(Color.RED);
            }

            snackbar.show();
        }
    }
}
