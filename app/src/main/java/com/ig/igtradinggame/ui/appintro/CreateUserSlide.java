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
import com.ig.igtradinggame.models.ClientModel;
import com.ig.igtradinggame.network.IGAPIService;
import com.ig.igtradinggame.network.NetworkConfig;
import com.ig.igtradinggame.storage.ClientIDStorage;
import com.ig.igtradinggame.storage.SharedPreferencesStorage;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class CreateUserSlide extends Fragment {
    private static final String ARG_LAYOUT_RES_ID = "layoutResId";

    @BindView(R.id.textView_success)
    TextView successText;
    @BindView(R.id.textView_clientfoundstats)
    TextView clientFoundStatsText;
    @BindView(R.id.textView_createSuccessStats)
    TextView successStatsText;
    @BindView(R.id.editText_playername)
    EditText nameEditText;
    @BindView(R.id.button_createPlayer)
    Button createPlayerButton;
    @BindView(R.id.button_createPlayerSubmit)
    Button submitButton;

    private Unbinder unbinder;
    private int layoutResId;

    public static CreateUserSlide newInstance(int layoutResId) {
        CreateUserSlide sampleSlide = new CreateUserSlide();
        Bundle args = new Bundle();
        args.putInt(ARG_LAYOUT_RES_ID, layoutResId);
        sampleSlide.setArguments(args);
        return sampleSlide;
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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(layoutResId, container, false);
        unbinder = ButterKnife.bind(this, view);
        initialiseViews();
        return view;
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

    private void initialiseViews() {
        // hide the success views
        nameEditText.setVisibility(View.GONE);
        successStatsText.setVisibility(View.GONE);
        successText.setVisibility(View.GONE);
        clientFoundStatsText.setVisibility(View.GONE);
        submitButton.setVisibility(View.GONE);

        // check sharedprefs for an existing clientid
        ClientIDStorage storage = new SharedPreferencesStorage(PreferenceManager.getDefaultSharedPreferences(getContext()));
        final String clientId = storage.loadClientId();

        if (clientId == null) {
            onClientNotFound();
        } else {
            clientFoundStatsText.setVisibility(View.VISIBLE);

            IGAPIService apiService = new IGAPIService(NetworkConfig.API_BASE_URL);

            apiService.getClientInfo(clientId, new IGAPIService.OnClientLoadedListener() {
                @Override
                public void onComplete(ClientModel response) {
                    clientFoundStatsText.setText("Found an existing client!\n" + response.toString());
                    createPlayerButton.setText("Discard, and create a new player");
                }

                @Override
                public void onError(String errorMessage) {
                    onClientNotFound();
                }
            });
        }
    }

    private void onClientNotFound() {
        if (clientFoundStatsText != null) {
            clientFoundStatsText.setText("Could not find a player on this device.\nCreate a new player below!");
            onClickCreatePlayer();
        }
    }

    @OnClick(R.id.button_createPlayer)
    public void onClickCreatePlayer() {
        createPlayerButton.setVisibility(View.GONE);
        nameEditText.setVisibility(View.VISIBLE);
        submitButton.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.button_createPlayerSubmit)
    public void onClickSubmit() {
        final String playerName = nameEditText.getText().toString();

        if (playerName.equals("")) {
            return;
        }

        IGAPIService apiService = new IGAPIService(NetworkConfig.API_BASE_URL);
        apiService.createClient(playerName, new IGAPIService.OnClientLoadedListener() {
            @Override
            public void onComplete(ClientModel response) {
                successStatsText.setVisibility(View.VISIBLE);
                successStatsText.setText("Success!\n" + response.toString());

                ClientIDStorage storage = new SharedPreferencesStorage(PreferenceManager.getDefaultSharedPreferences(getActivity()));
                storage.saveClientID(response.getId());
            }

            @Override
            public void onError(String errorMessage) {
                successStatsText.setVisibility(View.VISIBLE);
                successStatsText.setText(errorMessage);
            }
        });
    }
}