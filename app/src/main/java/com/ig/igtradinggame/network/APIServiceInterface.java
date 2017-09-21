package com.ig.igtradinggame.network;


public interface APIServiceInterface {
    void createClient(final String username, final APIService.OnCompleteListener listener);
}
