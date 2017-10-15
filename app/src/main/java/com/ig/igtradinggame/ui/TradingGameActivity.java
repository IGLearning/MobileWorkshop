package com.ig.igtradinggame.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.ig.igtradinggame.R;
import com.ig.igtradinggame.network.APIService;
import com.ig.igtradinggame.network.APIServiceInterface;
import com.ig.igtradinggame.network.market.Market;
import com.ig.igtradinggame.storage.ClientIDStorage;
import com.ig.igtradinggame.storage.SharedPreferencesStorage;

import java.util.List;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class TradingGameActivity extends BaseActivity {
    private static int HEARTBEAT_FREQUENCY_MILLIS = 500;

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
    private APIServiceInterface apiService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trading_game);

        apiService = new APIService();
        setAllTextViewsToText("Activity started. Loading....");
        final ClientIDStorage clientIDStorage = new SharedPreferencesStorage(PreferenceManager.getDefaultSharedPreferences(this));
        clientID = clientIDStorage.loadClientId();
        clientIdText.setText("CLIENT ID: " + clientID);
        startViewUpdates();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }

    private void setAllTextViewsToText(@NonNull final String text) {
        balanceText.setText(text);
        marketsText.setText(text);
        pricesText.setText(text);
        openPositionsText.setText(text);
    }

    private void startViewUpdates() {
        loadBalance();
        //setupMarkets();
        loadMarkets();

    }


    private void loadOpenPositions() {

    }

    private void loadMarkets() {
        apiService.getTickingMarketList(HEARTBEAT_FREQUENCY_MILLIS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Market>>() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull List<Market> markets) {
                        StringBuilder builder = new StringBuilder();

                        for (Market market : markets) {
                            builder.append(market.getMarketName());
                            builder.append("\t\t\t");
                            builder.append(market.getMarketId());
                            builder.append("\t\t\t");
                            builder.append(market.getCurrentPrice());
                            builder.append("\t\t\t");
                            builder.append("\n\n");
                        }

                        marketsText.setText(builder.toString());

                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void loadBalance() {
        if (clientID == null) {
            //TODO
            assert (false);
            return;
        }

        apiService.getFundsForClient(clientID, HEARTBEAT_FREQUENCY_MILLIS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                    }

                    @SuppressLint("SetTextI18n")
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

        //FIXME: activity still streams, even when the activity is destroyed.
    }
}
