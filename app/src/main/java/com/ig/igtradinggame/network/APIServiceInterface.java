package com.ig.igtradinggame.network;


import io.reactivex.Observable;

public interface APIServiceInterface {
    void createClient(final String username, final APIService.OnCompleteListener listener);
    Observable<Integer> getFundsForClient(final String clientId, final int updateFrequencyMillis);
}
