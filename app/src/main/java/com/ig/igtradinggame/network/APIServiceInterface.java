package com.ig.igtradinggame.network;


import com.ig.igtradinggame.network.market.Market;

import java.util.List;

import io.reactivex.Observable;

public interface APIServiceInterface {
    void createClient(final String username, final APIService.OnCreateClientCompleteListener listener);
    Observable<Integer> getFundsForClient(final String clientId, final int updateFrequencyMillis);

    void getMarkets(final APIService.OnMarketsLoadedCompleteListener listener);

    Observable<List<Market>> getTickingMarketList(final int updateFrequencyMillis);
}
