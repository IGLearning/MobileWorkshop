package com.ig.igtradinggame.network;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ig.igtradinggame.models.ClientModel;
import com.ig.igtradinggame.models.MarketModel;
import com.ig.igtradinggame.models.OpenPositionIdResponse;
import com.ig.igtradinggame.models.OpenPositionModel;
import com.ig.igtradinggame.network.requests.CreateClientRequest;
import com.ig.igtradinggame.network.requests.OpenPositionRequest;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
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

    /**
     * Creates a client
     * @param username Player's name. Note, can be rejected by server.
     * @param onCompleteListener
     */
    public void createClient(String username, final OnClientLoadedListener onCompleteListener) {
        final CreateClientRequest request = new CreateClientRequest(username);
        igTradingGameAPI.createClient(request).enqueue(new Callback<ClientModel>() {
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

    /**
     * Retrieves client info
     * @param clientId
     * @param onCompleteListener
     */
    public void getClientInfo(String clientId, final OnClientLoadedListener onCompleteListener) {
        igTradingGameAPI.getClientInfo(clientId).enqueue(new Callback<ClientModel>() {
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

    /**
     * Get's a client's details
     * @param clientId
     * @param updateFrequencyMillis
     * @return Client model observable
     */
    public Observable<ClientModel> getClientInfoStreaming(final String clientId, int updateFrequencyMillis) {
        return Observable
                .interval(updateFrequencyMillis, TimeUnit.MILLISECONDS, Schedulers.io())
                .flatMap(new Function<Long, ObservableSource<ClientModel>>() {
                    @Override
                    public ObservableSource<ClientModel> apply(@io.reactivex.annotations.NonNull Long aLong) throws Exception {
                        return igTradingGameAPI.getClientInfoStreaming(clientId);
                    }
                })
                .retry();
    }

    /**
     * Retrieves all markets
     * @param onCompleteListener
     */
    public void getAllMarkets(final OnMarketsLoadedCompleteListener onCompleteListener) {
        igTradingGameAPI.getAllMarkets().enqueue(new Callback<List<MarketModel>>() {
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

    /**
     * Retrieves all markets
     * @param updateFrequencyMillis
     * @return Market model observable
     */
    public Observable<List<MarketModel>> getAllMarketsStreaming(int updateFrequencyMillis) {
        return Observable
                .interval(updateFrequencyMillis, TimeUnit.MILLISECONDS, Schedulers.io())
                .flatMap(new Function<Long, ObservableSource<List<MarketModel>>>() {
                    @Override
                    public ObservableSource<List<MarketModel>> apply(@io.reactivex.annotations.NonNull Long aLong) throws Exception {
                        return igTradingGameAPI.getAllMarketsStreaming();
                    }
                })
                .retry();
    }

    /**
     * Retrieves all open positions
     * @param clientId
     * @param onCompleteListener
     */
    public void getOpenPositions(final String clientId, final OnOpenPositionsLoadedCompleteListener onCompleteListener) {
        igTradingGameAPI.getOpenPositions(clientId).enqueue(new Callback<List<OpenPositionModel>>() {
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

    /**
     * Streams all open positions
     * @param clientId
     * @param updateFrequencyMillis
     * @return Open position Model observable
     */
    public Observable<List<OpenPositionModel>> getOpenPositionsStreaming(final String clientId, int updateFrequencyMillis) {
        return Observable
                .interval(updateFrequencyMillis, TimeUnit.MILLISECONDS, Schedulers.io())
                .flatMap(new Function<Long, ObservableSource<List<OpenPositionModel>>>() {
                    @Override
                    public ObservableSource<List<OpenPositionModel>> apply(@io.reactivex.annotations.NonNull Long aLong) throws Exception {
                        return igTradingGameAPI.getOpenPositionsStreaming(clientId);
                    }
                })
                .retry();
    }

    /**
     * Creates a new position
     * @param clientId
     * @param openPositionRequest
     * @param onCompleteListener
     */
    public void openPosition(String clientId, OpenPositionRequest openPositionRequest, final OnOpenPositionCompleteListener onCompleteListener) {
        igTradingGameAPI.openPosition(clientId, openPositionRequest).enqueue(new Callback<OpenPositionIdResponse>() {
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

    /**
     * Closes a position.
     * @param clientId
     * @param openPositionId
     * @param onCompleteListener
     */
    public void closePosition(String clientId, String openPositionId, final OnClosePositionCompleteListener onCompleteListener) {
        igTradingGameAPI.closePosition(clientId, openPositionId).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful()) {
                    onCompleteListener.onComplete();
                } else {
                     onCompleteListener.onError(unboxError(response.errorBody())); 
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                onCompleteListener.onError(t.getMessage());
            }
        });
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

    private String unboxError(final ResponseBody error) {
        try {
            return error.string();
        } catch (IOException e) {
            return "Unable to decode error.";
        }
    }
}
