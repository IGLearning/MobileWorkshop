package com.ig.igtradinggame.network;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ig.igtradinggame.network.client.CreateClientRequest;
import com.ig.igtradinggame.network.client.CreateClientResponse;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIService implements APIServiceInterface {
    private IGTradingAPI igTradingAPI;

    public APIService() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        this.igTradingAPI = retrofit.create(IGTradingAPI.class);
    }

    @Override
    public void createClient(String username, final OnCompleteListener onCompleteListener) {
        final CreateClientRequest request = new CreateClientRequest(username);

        igTradingAPI.createClient(request).enqueue(new Callback<CreateClientResponse>() {
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

    public interface OnCompleteListener {
        void onComplete(CreateClientResponse response);
    }
}
