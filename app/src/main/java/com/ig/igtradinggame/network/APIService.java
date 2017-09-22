package com.ig.igtradinggame.network;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ig.igtradinggame.network.client.CreateClientRequest;
import com.ig.igtradinggame.network.client.CreateClientResponse;

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

public class APIService implements APIServiceInterface {
    private IGTradingGameAPI igTradingGameAPI;

    public APIService() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        this.igTradingGameAPI = retrofit.create(IGTradingGameAPI.class);
    }

    @Override
    public void createClient(String username, final OnCompleteListener onCompleteListener) {
        final CreateClientRequest request = new CreateClientRequest(username);

        igTradingGameAPI.createClient(request).enqueue(new Callback<CreateClientResponse>() {
            @Override
            public void onResponse(@NonNull Call<CreateClientResponse> call, @NonNull Response<CreateClientResponse> response) {
                if (response.isSuccessful()) {
                    onCompleteListener.onComplete(response.body());
                } else {
                    Log.d("QuestionsCallback", "Code: " + response.code() + " Message: " + response.message());  // TODO: FIX LOGGING CHAOS
                }
            }

            @Override
            public void onFailure(Call<CreateClientResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
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

    public interface OnCompleteListener {
        void onComplete(CreateClientResponse response);
    }
}
