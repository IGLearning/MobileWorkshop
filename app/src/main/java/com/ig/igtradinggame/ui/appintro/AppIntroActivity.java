package com.ig.igtradinggame.ui.appintro;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro2;
import com.ig.igtradinggame.R;

public class AppIntroActivity extends AppIntro2 {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(NetworkConnectionSlide.newInstance(R.layout.network_connection_slide));
        addSlide(CreateUserSlide.newInstance(R.layout.create_user_slide));

        skipButtonEnabled = true;
    }

    // Uncomment this function to enable the skip button
    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        setResult(RESULT_OK);
        finish();
    }

    public void onDonePressed(Fragment currentFragment) {
        setResult(RESULT_OK);
        finish();
    }
}
