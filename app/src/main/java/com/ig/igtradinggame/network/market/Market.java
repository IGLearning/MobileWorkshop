package com.ig.igtradinggame.network.market;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Market {
    @SerializedName("id")
    @Expose
    private String marketId;

    @SerializedName("marketName")
    @Expose
    private String marketName;

    @SerializedName("currentPrice")
    @Expose
    private float currentPrice;

    public String getMarketId() {
        return marketId;
    }

    public String getMarketName() {
        return marketName;
    }

    public float getCurrentPrice() {
        return currentPrice;
    }

    @Override
    public String toString() {
        return "Market{" +
                "marketId='" + marketId + '\'' +
                ", marketName='" + marketName + '\'' +
                ", currentPrice=" + currentPrice +
                '}';
    }
}