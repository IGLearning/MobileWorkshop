package com.ig.igtradinggame.features.intropages.networkconnection;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.ig.igtradinggame.R;
import com.ig.igtradinggame.models.MarketModel;
import com.ig.igtradinggame.network.NetworkConfig;
import com.ig.igtradinggame.network.retrofit_impl.IGAPIService;
import com.ig.igtradinggame.storage.AppStorage;
import com.ig.igtradinggame.ui.BaseFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NetworkConnectionSlide extends BaseFragment {
    private static final String ARG_LAYOUT_RES_ID = "layoutResId";

    @BindView(R.id.textView_connectionSuccessful)
    TextView connectionSuccessfulText;

    @BindView(R.id.button_testConnection)
    Button testConnectionButton;

    @BindView(R.id.switch_use_heroku)
    Switch endpointSwitch;

    private int layoutResId;

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

        setupBaseUrl();
        return view;
    }

    private void setupBaseUrl() {
        String currentBaseURL = AppStorage.getInstance(getActivity()).loadBaseUrl();

        endpointSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                AppStorage.getInstance(getActivity()).saveBaseURL(
                        isChecked ? NetworkConfig.LIVE_HEROKU_SERVER_URL : NetworkConfig.EMULATOR_DEFAULT_LOCALHOST_URL
                );
            }
        });

        if (currentBaseURL == null) {
            endpointSwitch.setChecked(true);
            AppStorage.getInstance(getActivity()).saveBaseURL(NetworkConfig.LIVE_HEROKU_SERVER_URL);
            return;
        }

        if (currentBaseURL.equals(NetworkConfig.EMULATOR_DEFAULT_LOCALHOST_URL)) {
            endpointSwitch.setChecked(false);
        } else {
            endpointSwitch.setChecked(true);
        }
    }

    @OnClick(R.id.button_testConnection)
    public void onTestConnectionTapped() {
        testConnectionButton.setVisibility(View.GONE);
        connectionSuccessfulText.setVisibility(View.GONE);

        final String baseUrl = AppStorage.getInstance(getActivity()).loadBaseUrl();

        IGAPIService apiService = new IGAPIService(baseUrl);
        apiService.getAllMarkets(new IGAPIService.OnMarketsLoadedCompleteListener() {
            @Override
            public void onComplete(List<MarketModel> marketList) {
                testConnectionButton.setVisibility(View.VISIBLE);

                connectionSuccessfulText.setVisibility(View.VISIBLE);
                connectionSuccessfulText.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
                connectionSuccessfulText.setText("Connected successfully.");
            }

            @Override
            public void onError(String errorMessage) {
                testConnectionButton.setVisibility(View.VISIBLE);

                connectionSuccessfulText.setVisibility(View.VISIBLE);
                connectionSuccessfulText.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                connectionSuccessfulText.setText(errorMessage);
            }
        });
    }
}