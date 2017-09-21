package com.ig.igtradinggame.presentation;

import android.support.annotation.NonNull;

import com.ig.igtradinggame.network.APIService;
import com.ig.igtradinggame.network.APIServiceInterface;


public class CreateClientPresenter {
    private APIServiceInterface apiService;

    public CreateClientPresenter() {
        apiService = new APIService();
    }

    public void submitCreateClientRequest(@NonNull final String username, @NonNull final APIService.OnCompleteListener onCompleteListener) {
        apiService.createClient(username, onCompleteListener);
    }
}
