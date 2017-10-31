package com.ig.igtradinggame.network;

import com.ig.igtradinggame.models.ClientModel;
import com.ig.igtradinggame.models.MarketModel;
import com.ig.igtradinggame.models.OpenPositionIdResponse;
import com.ig.igtradinggame.models.OpenPositionModel;
import com.ig.igtradinggame.network.requests.CreateClientRequest;
import com.ig.igtradinggame.network.requests.OpenPositionRequest;

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

    @GET("/workshop/client/{clientId}")
    Call<ClientModel> getClientInfo(@Path("clientId") String clientId);

    @GET("/workshop/client/{clientId}")
    Observable<ClientModel> getClientInfoStreaming(@Path("clientId") String clientId);


    // MARKET DATA CONTROLLER: Operations relating to market data
    @GET("/workshop/marketData/allMarkets")
    Call<List<MarketModel>> getAllMarkets();

    @GET("/workshop/marketData/allMarkets")
    Observable<List<MarketModel>> getAllMarketsStreaming();


    // OPEN POSITIONS CONTROLLER: Operations relating to open positions
    @GET("/workshop/openPositions/{clientId}")
    Call<List<OpenPositionModel>> getOpenPositions(@Path("clientId") String clientId);

    @GET("/workshop/openPositions/{clientId}")
    Observable<List<OpenPositionModel>> getOpenPositionsStreaming(@Path("clientId") String clientId);

    @POST("/workshop/openPositions/{clientId}")
    Call<OpenPositionIdResponse> openPosition(@Path("clientId") String clientId,
                                              @Body OpenPositionRequest openPositionRequest);

    @POST("/workshop/openPositions/{clientId}/{openPositionId}")
    Call<Double> closePosition(@Path("clientId") String clientId,
                               @Path("openPositionId") String openPositionId);
}
