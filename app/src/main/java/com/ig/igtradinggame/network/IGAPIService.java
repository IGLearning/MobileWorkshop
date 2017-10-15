package com.ig.igtradinggame.network;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ig.igtradinggame.models.ClientModel;
import com.ig.igtradinggame.models.MarketModel;
import com.ig.igtradinggame.network.requests.CreateClientRequest;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public final class IGAPIService {
    private IGTradingGameAPI igTradingGameAPI;

    public IGAPIService() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetworkConfig.API_BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        this.igTradingGameAPI = retrofit.create(IGTradingGameAPI.class);
    }

    public void getMarkets(final OnMarketsLoadedCompleteListener listener) {
        igTradingGameAPI.getAllMarkets().enqueue(new Callback<List<MarketModel>>() {
            @Override
            public void onResponse(Call<List<MarketModel>> call, Response<List<MarketModel>> response) {
                if (response.isSuccessful()) {
                    listener.onComplete(response.body());
                } else {
                    Log.d("QuestionsCallback", "Code: " + response.code() + " Message: " + response.message());  // TODO: FIX LOGGING CHAOS
                }
            }

            @Override
            public void onFailure(Call<List<MarketModel>> call, Throwable t) {
                Log.e("CREATE CLIENT", "onFailure: " + t.getMessage());
            }
        });
    }

    public void createClient(String username, final OnCreateClientCompleteListener onCompleteListener) {
        final CreateClientRequest request = new CreateClientRequest(username);

        igTradingGameAPI.createClient(request).enqueue(new Callback<ClientModel>() {
            @Override
            public void onResponse(@NonNull Call<ClientModel> call, @NonNull Response<ClientModel> response) {
                if (response.isSuccessful()) {
                    onCompleteListener.onComplete(response.body());
                } else {
                    Log.d("QuestionsCallback", "Code: " + response.code() + " Message: " + response.message());  // TODO: FIX LOGGING CHAOS
                }
            }

            @Override
            public void onFailure(Call<ClientModel> call, Throwable t) {
                Log.e("CREATE CLIENT", "onFailure: " + t.getMessage());
                t.printStackTrace();
            }
        });
    }

    public Observable<Integer> getFundsForClient(final String clientId, int updateFrequencyMillis) {
        return Observable
                .interval(updateFrequencyMillis, TimeUnit.MILLISECONDS, Schedulers.io())
                .flatMap(new Function<Long, ObservableSource<Integer>>() {
                    @Override
                    public ObservableSource<Integer> apply(@io.reactivex.annotations.NonNull Long aLong) throws Exception {
                        return igTradingGameAPI.getAvailableFunds(clientId);
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        //TODO: Log the error
                        throwable.printStackTrace();
                    }
                })
                .retry();
    }

    public void getFundsForClient(final String clientID, final Callback<Integer> callback) {
        igTradingGameAPI.getAvailableFundsSync(clientID).enqueue(callback);
    }

    public Observable<List<MarketModel>> getTickingMarketList(final int updateFrequencyMillis) {
        return Observable
                .interval(updateFrequencyMillis, TimeUnit.MILLISECONDS)
                .flatMap(new Function<Long, ObservableSource<List<MarketModel>>>() {
                    @Override
                    public ObservableSource<List<MarketModel>> apply(@io.reactivex.annotations.NonNull Long aLong) throws Exception {
                        return igTradingGameAPI.getAllMarketsObservable();
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        //TODO: Log the error
                        throwable.printStackTrace();
                    }
                })
                .retry();
    }

    public interface OnCreateClientCompleteListener {
        void onComplete(ClientModel response);
    }

    public interface OnMarketsLoadedCompleteListener {
        void onComplete(List<MarketModel> marketList);
    }
}
