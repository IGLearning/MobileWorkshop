package com.ig.igtradinggame.ui;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ig.igtradinggame.R;
import com.ig.igtradinggame.network.APIService;
import com.ig.igtradinggame.network.client.CreateClientResponse;
import com.ig.igtradinggame.presentation.CreateClientPresenter;
import com.ig.igtradinggame.storage.ClientIDStorage;
import com.ig.igtradinggame.storage.SharedPreferencesStorage;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    @BindView(R.id.textView_welcomeLabel)
    TextView welcomeText;

    @BindView(R.id.editText_teamName)
    EditText teamNameField;

    @BindView(R.id.button_submit)
    Button submitButton;

    @BindView(R.id.button_continue)
    Button continueButton;

    @BindView(R.id.progressBar_signupProgress)
    ProgressBar signupProgress;

    @BindView(R.id.textView_onComplete)
    TextView onCompleteText;

    private CreateClientPresenter presenter;
    private ClientIDStorage clientIDStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupViews();
        presenter = new CreateClientPresenter();
        clientIDStorage = new SharedPreferencesStorage(PreferenceManager.getDefaultSharedPreferences(this));
    }

    @OnClick(R.id.button_continue)
    public void onContinueClicked() {
        Log.d(TAG, "onContinueClicked");
        startActivity(new Intent(this, TradingGameActivity.class));
    }

    @OnClick(R.id.button_submit)
    public void onSubmitClicked() {
        //start the progressbar
        startProgressBar();

        //check if text is valid
        final String proposedName = teamNameField.getText().toString();

        if (proposedName.isEmpty()) {
            onCompleteText.setText("Team name is not valid");
            stopProgressBar();
            return;
        }

        //create the request
        presenter.submitCreateClientRequest(proposedName, new APIService.OnCreateClientCompleteListener() {
            @Override
            public void onComplete(CreateClientResponse response) {
                stopProgressBar();
                onCompleteText.setText("Success! Retrieved ID: " + response.getClientId());
                continueButton.setVisibility(View.VISIBLE);
                clientIDStorage.saveClientID(response.getClientId());
            }
        });
    }

    private void setupViews() {
        signupProgress.setVisibility(View.INVISIBLE);
        onCompleteText.setText("");
        continueButton.setVisibility(View.GONE);
    }

    private void startProgressBar() {
        signupProgress.setVisibility(View.VISIBLE);
        Log.e(TAG, "starting progressbar");
    }

    private void stopProgressBar() {
        signupProgress.setVisibility(View.GONE);
        Log.e(TAG, "hiding progressbar");
    }
}
