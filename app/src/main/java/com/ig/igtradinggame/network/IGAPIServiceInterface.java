package com.ig.igtradinggame.network;

import com.ig.igtradinggame.models.ClientModel;
import com.ig.igtradinggame.models.MarketModel;
import com.ig.igtradinggame.models.OpenPositionModel;
import com.ig.igtradinggame.features.maingame.trade.buy.OpenPositionRequest;
import com.ig.igtradinggame.network.retrofit_impl.IGAPIService;

import java.util.List;

import io.reactivex.Observable;

public interface IGAPIServiceInterface {
    /**
     * Creates a client
     *
     * @param username           Player's name. Note, can be rejected by server.
     * @param onCompleteListener
     */
    void createClient(String username, IGAPIService.OnClientLoadedListener onCompleteListener);

    /**
     * Retrieves client info
     *
     * @param clientId
     * @param onCompleteListener
     */
    void getClientInfo(String clientId, IGAPIService.OnClientLoadedListener onCompleteListener);

    /**
     * Get's a client's details
     *
     * @param clientId
     * @param updateFrequencyMillis
     * @return Client model observable
     */
    Observable<ClientModel> getClientInfoStreaming(String clientId, int updateFrequencyMillis);

    /**
     * Retrieves all markets
     *
     * @param onCompleteListener
     */
    void getAllMarkets(IGAPIService.OnMarketsLoadedCompleteListener onCompleteListener);

    /**
     * Retrieves all markets
     *
     * @param updateFrequencyMillis
     * @return Market model observable
     */
    Observable<List<MarketModel>> getAllMarketsStreaming(int updateFrequencyMillis);

    /**
     * Retrieves all open positions
     *
     * @param clientId
     * @param onCompleteListener
     */
    void getOpenPositions(String clientId, IGAPIService.OnOpenPositionsLoadedCompleteListener onCompleteListener);

    /**
     * Streams all open positions
     *
     * @param clientId
     * @param updateFrequencyMillis
     * @return Open position Model observable
     */
    Observable<List<OpenPositionModel>> getOpenPositionsStreaming(String clientId, int updateFrequencyMillis);

    /**
     * Creates a new position
     *
     * @param clientId
     * @param openPositionRequest
     * @param onCompleteListener
     */
    void openPosition(String clientId, OpenPositionRequest openPositionRequest, IGAPIService.OnOpenPositionCompleteListener onCompleteListener);

    /**
     * Closes a position.
     *
     * @param clientId
     * @param openPositionId
     * @param onCompleteListener
     */
    void closePosition(String clientId, String openPositionId, IGAPIService.OnClosePositionCompleteListener onCompleteListener);
}
