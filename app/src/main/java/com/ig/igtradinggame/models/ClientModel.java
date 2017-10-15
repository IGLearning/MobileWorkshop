package com.ig.igtradinggame.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClientModel {
    @SerializedName("id")
    @Expose
    private String clientId;

    @SerializedName("userName")
    @Expose
    private String userName;

    @SerializedName("funds")
    @Expose
    private int funds;

    public ClientModel(String clientId, String userName, Integer funds) {
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
