package com.ig.igtradinggame.network;

import com.ig.igtradinggame.network.requests.CreateClientRequest;
import com.ig.igtradinggame.models.ClientModel;
import com.ig.igtradinggame.models.MarketModel;
import com.ig.igtradinggame.models.PriceModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


interface IGTradingGameAPI {
    // CLIENT CONTROLLER: Operations relating to the client
    @POST("/workshop/client/createClient")
    Call<ClientModel> createClient(@Body CreateClientRequest createClientRequest);

    @GET("/workshop/client/funds/{clientId}")
    Observable<Integer> getAvailableFunds(@Path("clientId") String clientId);

    @GET("/workshop/client/funds/{clientId}")
    Call<Integer> getAvailableFundsSync(@Path("clientId") String clientId);

    // MARKET DATA CONTROLLER: Operations relating to market data
    @GET("/workshop/marketData/allMarkets")
    Call<List<MarketModel>> getAllMarkets();

    @GET("/workshop/marketData/allMarkets")
    Observable<List<MarketModel>> getAllMarketsObservable();

    @GET("/workshop/marketData/allPrices")
    Call<List<PriceModel>> getAllPrices();
}
