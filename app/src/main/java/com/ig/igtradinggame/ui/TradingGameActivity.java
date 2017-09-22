package com.ig.igtradinggame.ui;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.ig.igtradinggame.R;
import com.ig.igtradinggame.network.APIService;
import com.ig.igtradinggame.network.APIServiceInterface;
import com.ig.igtradinggame.storage.ClientIDStorage;
import com.ig.igtradinggame.storage.SharedPreferencesStorage;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

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

    private String clientID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trading_game);

        setAllTextViewsToText("Activity started. Loading....");
        final ClientIDStorage clientIDStorage = new SharedPreferencesStorage(PreferenceManager.getDefaultSharedPreferences(this));
        clientID = clientIDStorage.loadClientId();
        clientIdText.setText("CLIENT ID: " + clientID);
        startViewUpdates();
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
        if (clientID == null) {
            //TODO
            return;
        }

        APIServiceInterface apiService = new APIService();
        apiService.getFundsForClient(clientID, 200)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull Integer integer) {
                        balanceText.setText(integer.toString());
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
