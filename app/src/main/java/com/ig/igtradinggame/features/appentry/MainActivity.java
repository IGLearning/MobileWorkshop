package com.ig.igtradinggame.features.appentry;

import android.content.Intent;
import android.os.Bundle;

import com.ig.igtradinggame.R;
import com.ig.igtradinggame.storage.AppStorage;
import com.ig.igtradinggame.ui.BaseActivity;
import com.ig.igtradinggame.features.maingame.TradingGameActivity;
import com.ig.igtradinggame.features.intropages.AppIntroActivity;

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
            String clientId = AppStorage.getInstance(this).loadClientId();

            if (clientId != null) {
                onContinueClicked();
            }
        }
    }
}
