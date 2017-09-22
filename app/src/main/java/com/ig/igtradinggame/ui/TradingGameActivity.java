package com.ig.igtradinggame.ui;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.ig.igtradinggame.R;
import com.ig.igtradinggame.storage.ClientIDStorage;
import com.ig.igtradinggame.storage.SharedPreferencesStorage;

import butterknife.BindView;

public class TradingGameActivity extends BaseActivity {

    @BindView(R.id.textView_clientId)
    TextView clientIdText;

    @BindView(R.id.textView_balance)
    TextView balanceText;

    @BindView(R.id.textView_markets)
    TextView marketsText;

    @BindView(R.id.textView_prices)
    TextView pricesText;

    @BindView(R.id.textView_openPositions)
    TextView openPositionsText;

    private ClientIDStorage clientIDStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trading_game);

        setAllTextViewsToText("Activity started. Loading....");
        clientIDStorage = new SharedPreferencesStorage(PreferenceManager.getDefaultSharedPreferences(this));
        clientIdText.setText("FOUND CLIENT ID: " + clientIDStorage.loadClientId());
    }

    private void setAllTextViewsToText(@NonNull final String text) {
        balanceText.setText(text);
        marketsText.setText(text);
        pricesText.setText(text);
        openPositionsText.setText(text);
    }

    private void startViewUpdates() {
        loadBalance();
    }

    private void loadBalance() {

    }
}
