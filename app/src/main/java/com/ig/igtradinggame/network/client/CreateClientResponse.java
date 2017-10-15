package com.ig.igtradinggame.network.client;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateClientResponse {
    @SerializedName("id")
    @Expose
    private String clientId;

    @SerializedName("userName")
    @Expose
    private String userName;

    @SerializedName("funds")
    @Expose
    private int funds;

    public CreateClientResponse(String clientId, String userName, Integer funds) {
        this.clientId = clientId;
        this.userName = userName;
        this.funds = funds;
    }

    public String getClientId() {
        return clientId;
    }

    public String getUserName() {
        return userName;
    }

    public int getFunds() {
        return funds;
    }
}
