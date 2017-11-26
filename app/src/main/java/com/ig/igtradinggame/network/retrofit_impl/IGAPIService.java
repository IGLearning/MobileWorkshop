package com.ig.igtradinggame.network.retrofit_impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ig.igtradinggame.models.ClientModel;
import com.ig.igtradinggame.models.MarketModel;
import com.ig.igtradinggame.models.OpenPositionIdResponse;
import com.ig.igtradinggame.models.OpenPositionModel;
import com.ig.igtradinggame.network.IGAPIServiceInterface;
import com.ig.igtradinggame.network.NetworkConfig;
import com.ig.igtradinggame.features.intropages.createuser.CreateClientRequest;
import com.ig.igtradinggame.features.maingame.trade.buy.OpenPositionRequest;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Contains the network calls for the entire MW application.
 * If you're running it locally, you should be able to find it at
 * http://localhost:8085/workshop/api/swagger-ui.html
 */
public final class IGAPIService implements IGAPIServiceInterface {
    private IGTradingGameRetrofitAPI igTradingGameRetrofitAPI;

    public IGAPIService(String baseURL) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(NetworkConfig.RETROFIT_LOGGING_LEVEL);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        this.igTradingGameRetrofitAPI = retrofit.create(IGTradingGameRetrofitAPI.class);
    }

    @Override
    public void createClient(String username, final OnClientLoadedListener onCompleteListener) {
        final CreateClientRequest request = new CreateClientRequest(username);
        igTradingGameRetrofitAPI.createClient(request).enqueue(new Callback<ClientModel>() {
            @Override
            public void onResponse(@NonNull Call<ClientModel> call, @NonNull Response<ClientModel> response) {
                if (response.isSuccessful()) {
                    onCompleteListener.onComplete(response.body());
                } else {
                    onCompleteListener.onError(unboxError(response.errorBody()));
                }
            }

            @Override
            public void onFailure(Call<ClientModel> call, Throwable t) {
                onCompleteListener.onError(t.getMessage());
            }
        });
    }

    @Override
    public void getClientInfo(String clientId, final OnClientLoadedListener onCompleteListener) {
        igTradingGameRetrofitAPI.getClientInfo(clientId).enqueue(new Callback<ClientModel>() {
            @Override
            public void onResponse(Call<ClientModel> call, Response<ClientModel> response) {
                if (response.isSuccessful()) {
                    onCompleteListener.onComplete(response.body());
                } else {
                    onCompleteListener.onError(unboxError(response.errorBody()));
                }
            }

            @Override
            public void onFailure(Call<ClientModel> call, Throwable t) {
                onCompleteListener.onError(t.getMessage());
            }
        });
    }

    @Override
    public Observable<ClientModel> getClientInfoStreaming(final String clientId, int updateFrequencyMillis) {
        return Observable
                .interval(updateFrequencyMillis, TimeUnit.MILLISECONDS, Schedulers.io())
                .flatMap(new Function<Long, ObservableSource<ClientModel>>() {
                    @Override
                    public ObservableSource<ClientModel> apply(@io.reactivex.annotations.NonNull Long aLong) throws Exception {
                        return igTradingGameRetrofitAPI.getClientInfoStreaming(clientId);
                    }
                })
                .retry();
    }

    @Override
    public void getAllMarkets(final OnMarketsLoadedCompleteListener onCompleteListener) {
        igTradingGameRetrofitAPI.getAllMarkets().enqueue(new Callback<List<MarketModel>>() {
            @Override
            public void onResponse(Call<List<MarketModel>> call, Response<List<MarketModel>> response) {
                if (response.isSuccessful()) {
                    onCompleteListener.onComplete(response.body());
                } else {
                    onCompleteListener.onError(unboxError(response.errorBody()));
                }
            }

            @Override
            public void onFailure(Call<List<MarketModel>> call, Throwable t) {
                onCompleteListener.onError(t.getMessage());
            }
        });
    }

    @Override
    public Observable<List<MarketModel>> getAllMarketsStreaming(int updateFrequencyMillis) {
        return Observable
                .interval(updateFrequencyMillis, TimeUnit.MILLISECONDS, Schedulers.io())
                .flatMap(new Function<Long, ObservableSource<List<MarketModel>>>() {
                    @Override
                    public ObservableSource<List<MarketModel>> apply(@io.reactivex.annotations.NonNull Long aLong) throws Exception {
                        return igTradingGameRetrofitAPI.getAllMarketsStreaming();
                    }
                })
                .retry();
    }

    @Override
    public void getOpenPositions(final String clientId, final OnOpenPositionsLoadedCompleteListener onCompleteListener) {
        igTradingGameRetrofitAPI.getOpenPositions(clientId).enqueue(new Callback<List<OpenPositionModel>>() {
            @Override
            public void onResponse(Call<List<OpenPositionModel>> call, Response<List<OpenPositionModel>> response) {
                if (response.isSuccessful()) {
                    onCompleteListener.onComplete(response.body());
                } else {
                    onCompleteListener.onError(unboxError(response.errorBody()));
                }
            }

            @Override
            public void onFailure(Call<List<OpenPositionModel>> call, Throwable t) {
                onCompleteListener.onError(t.getMessage());
            }
        });
    }

    @Override
    public Observable<List<OpenPositionModel>> getOpenPositionsStreaming(final String clientId, int updateFrequencyMillis) {

        return Observable
                .interval(updateFrequencyMillis, TimeUnit.MILLISECONDS, Schedulers.io())
                .flatMap(new Function<Long, ObservableSource<List<OpenPositionModel>>>() {
                    @Override
                    public ObservableSource<List<OpenPositionModel>> apply(@io.reactivex.annotations.NonNull Long aLong) throws Exception {
                        return igTradingGameRetrofitAPI.getOpenPositionsStreaming(clientId);
                    }
                })
                .retry();
    }

    @Override
    public void openPosition(String clientId, OpenPositionRequest openPositionRequest, final OnOpenPositionCompleteListener onCompleteListener) {
        igTradingGameRetrofitAPI.openPosition(clientId, openPositionRequest).enqueue(new Callback<OpenPositionIdResponse>() {
            @Override
            public void onResponse(Call<OpenPositionIdResponse> call, Response<OpenPositionIdResponse> response) {
                if (response.isSuccessful()) {
                    onCompleteListener.onComplete(response.body());
                } else {
                    onCompleteListener.onError(unboxError(response.errorBody()));
                }
            }

            @Override
            public void onFailure(Call<OpenPositionIdResponse> call, Throwable t) {
                onCompleteListener.onError(t.getMessage());
            }
        });
    }

    @Override
    public void closePosition(String clientId, String openPositionId, final OnClosePositionCompleteListener onCompleteListener) {
        igTradingGameRetrofitAPI.closePosition(clientId, openPositionId).enqueue(new Callback<Double>() {
            @Override
            public void onResponse(Call<Double> call, Response<Double> response) {
                if (response.isSuccessful()) {
                    onCompleteListener.onComplete();
                } else {
                    onCompleteListener.onError(unboxError(response.errorBody()));
                }
            }

            @Override
            public void onFailure(Call<Double> call, Throwable t) {
                onCompleteListener.onError(t.getMessage());
            }
        });
    }

    private String unboxError(final ResponseBody error) {
        try {
            return error.string();
        } catch (IOException e) {
            return "Unable to decode error.";
        }
    }

    public interface OnClientLoadedListener {
        void onComplete(ClientModel response);

        void onError(String errorMessage);
    }

    public interface OnMarketsLoadedCompleteListener {
        void onComplete(List<MarketModel> marketList);

        void onError(String errorMessage);
    }

    public interface OnOpenPositionsLoadedCompleteListener {
        void onComplete(List<OpenPositionModel> openPositionModels);

        void onError(String errorMessage);
    }

    public interface OnOpenPositionCompleteListener {
        void onComplete(OpenPositionIdResponse openPositionIdResponse);

        void onError(String errorMessage);
    }

    public interface OnClosePositionCompleteListener {
        void onComplete();

        void onError(String errorMessage);
    }
}
