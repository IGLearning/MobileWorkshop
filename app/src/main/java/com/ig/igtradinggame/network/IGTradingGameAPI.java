package com.ig.igtradinggame.network;

import com.ig.igtradinggame.network.client.CreateClientRequest;
import com.ig.igtradinggame.network.client.CreateClientResponse;
import com.ig.igtradinggame.network.market.Market;
import com.ig.igtradinggame.network.market.PriceSnapshot;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface IGTradingGameAPI {
    // CLIENT CONTROLLER: Operations relating to the client
    @POST("/workshop/client/createClient")
    Call<CreateClientResponse> createClient(@Body CreateClientRequest createClientRequest);

    @GET("/workshop/client/funds/{clientId}")
    Observable<Integer> getAvailableFunds(@Path("clientId") String clientId);

    // MARKET DATA CONTROLLER: Operations relating to market data
    @GET("/workshop/marketData/allMarkets")
    Call<List<Market>> getAllMarkets();

    @GET("/workshop/marketData/allMarkets")
    Observable<List<Market>> getAllMarketsObservable();

    @GET("/workshop/marketData/allPrices")
    Call<List<PriceSnapshot>> getAllPrices();
}
