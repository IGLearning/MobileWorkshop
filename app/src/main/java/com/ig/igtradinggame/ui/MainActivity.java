package com.ig.igtradinggame.ui;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import com.ig.igtradinggame.R;
import com.ig.igtradinggame.storage.ClientIDStorage;
import com.ig.igtradinggame.storage.SharedPreferencesStorage;
import com.ig.igtradinggame.ui.appintro.AppIntroActivity;

import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    private static final int APP_INTRO_RESULT = 10001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onAppIntroClicked();
    }

    @OnClick(R.id.button_continue)
    public void onContinueClicked() {
        Log.d(TAG, "onContinueClicked");
        startActivity(new Intent(this, TradingGameActivity.class));
    }

    @OnClick(R.id.button_appIntro)
    public void onAppIntroClicked() {
        startActivityForResult(new Intent(this, AppIntroActivity.class), APP_INTRO_RESULT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode != APP_INTRO_RESULT) {
            return;
        }

        if (resultCode == RESULT_OK) {
            final ClientIDStorage storage = new SharedPreferencesStorage(PreferenceManager.getDefaultSharedPreferences(this));
            if (storage.loadClientId() != null) {
                onContinueClicked();
            }
        }
    }
}
