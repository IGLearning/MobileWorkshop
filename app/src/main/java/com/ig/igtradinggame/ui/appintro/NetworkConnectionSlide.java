package com.ig.igtradinggame.ui.appintro;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ig.igtradinggame.R;
import com.ig.igtradinggame.models.MarketModel;
import com.ig.igtradinggame.network.IGAPIService;
import com.ig.igtradinggame.network.NetworkConfig;
import com.ig.igtradinggame.storage.BaseUrlStorage;
import com.ig.igtradinggame.storage.SharedPreferencesStorage;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class NetworkConnectionSlide extends Fragment {
    private static final String ARG_LAYOUT_RES_ID = "layoutResId";

    @BindView(R.id.editText_baseApiAddress)
    EditText baseApiAddress;

    @BindView(R.id.textView_connectionSuccessful)
    TextView connectionSuccessfulText;

    @BindView(R.id.button_testConnection)
    Button testConnectionButton;

    private Unbinder unbinder;
    private int layoutResId;
    private BaseUrlStorage storage;

    public static NetworkConnectionSlide newInstance(int layoutResId) {
        NetworkConnectionSlide networkConnectionSlide = new NetworkConnectionSlide();

        Bundle args = new Bundle();
        args.putInt(ARG_LAYOUT_RES_ID, layoutResId);
        networkConnectionSlide.setArguments(args);
        return networkConnectionSlide;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        storage = new SharedPreferencesStorage(PreferenceManager.getDefaultSharedPreferences(getActivity()));

        final String baseURL = storage.loadBaseUrl();

        if (baseURL != null) {
            baseApiAddress.setText(baseURL);
        } else {
            baseApiAddress.setText(NetworkConfig.EMULATOR_DEFAULT_LOCALHOST_URL);
            storage.saveBaseURL(NetworkConfig.EMULATOR_DEFAULT_LOCALHOST_URL);
        }

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

        final String currentBaseURL = storage.loadBaseUrl();

        if (currentBaseURL != null) {
            if (currentBaseURL.equals(NetworkConfig.EMULATOR_DEFAULT_LOCALHOST_URL)) {
                baseApiAddress.setText(NetworkConfig.LIVE_HEROKU_SERVER_URL);
                storage.saveBaseURL(NetworkConfig.LIVE_HEROKU_SERVER_URL);
            } else {
                baseApiAddress.setText(NetworkConfig.EMULATOR_DEFAULT_LOCALHOST_URL);
                storage.saveBaseURL(NetworkConfig.EMULATOR_DEFAULT_LOCALHOST_URL);
            }
        }
    }


    @OnClick(R.id.button_testConnection)
    public void onTestConnectionTapped() {
        testConnectionButton.setText("Loading...");
        IGAPIService apiService = new IGAPIService(storage.loadBaseUrl());
        apiService.getAllMarkets(new IGAPIService.OnMarketsLoadedCompleteListener() {
            @Override
            public void onComplete(List<MarketModel> marketList) {
                connectionSuccessfulText.setVisibility(View.VISIBLE);
                testConnectionButton.setVisibility(View.GONE);
            }

            @Override
            public void onError(String errorMessage) {
                testConnectionButton.setVisibility(View.GONE);
                connectionSuccessfulText.setVisibility(View.VISIBLE);
                connectionSuccessfulText.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                connectionSuccessfulText.setText("Error: " + errorMessage);
            }
        });
    }
}