package com.ig.igtradinggame.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro2;
import com.ig.igtradinggame.R;

public class IntroActivity extends AppIntro2 {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(SampleSlide.newInstance(R.layout.sample_slide));
        addSlide(CreateUserSlide.newInstance(R.layout.intro_slide_2));

        skipButtonEnabled = false;
    }

    public void onDonePressed(Fragment currentFragment) {
        setResult(RESULT_OK);
        finish();
    }
}
