package com.ig.igtradinggame.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.ig.igtradinggame.R;
import com.ig.igtradinggame.network.APIService;
import com.ig.igtradinggame.network.APIServiceInterface;
import com.ig.igtradinggame.network.BuildConfig;
import com.ig.igtradinggame.network.market.Market;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SampleSlide extends Fragment {
    private static final String TAG = "AppIntroFragment1";
    private static final String ARG_LAYOUT_RES_ID = "layoutResId";

    @BindView(R.id.editText_baseApiAddress)
    EditText baseApiAddress;

    @BindView(R.id.textView_connectionSuccessful)
    TextView connectionSuccessfulText;

    private Unbinder unbinder;
    private int layoutResId;

    public static SampleSlide newInstance(int layoutResId) {
        SampleSlide sampleSlide = new SampleSlide();

        Bundle args = new Bundle();
        args.putInt(ARG_LAYOUT_RES_ID, layoutResId);
        sampleSlide.setArguments(args);
        return sampleSlide;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate: ");
        if (getArguments() != null && getArguments().containsKey(ARG_LAYOUT_RES_ID)) {
            layoutResId = getArguments().getInt(ARG_LAYOUT_RES_ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(layoutResId, container, false);
        unbinder = ButterKnife.bind(this, view);


        baseApiAddress.setText(BuildConfig.API_BASE_URL);
        connectionSuccessfulText.setVisibility(View.INVISIBLE);

        return view;
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

    @OnClick(R.id.button_change_api)
    public void onChangeApiClicked() {
        baseApiAddress.setEnabled(!baseApiAddress.isEnabled());
    }


    @OnClick(R.id.button_testConnection)
    public void onTestConnectionTapped() {
        APIServiceInterface apiService = new APIService();
        apiService.getMarkets(new APIService.OnMarketsLoadedCompleteListener() {
            @Override
            public void onComplete(List<Market> marketList) {
                connectionSuccessfulText.setVisibility(View.VISIBLE);
            }
        });
    }
}