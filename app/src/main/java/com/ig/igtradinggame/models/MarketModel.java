package com.ig.igtradinggame.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MarketModel extends CardModel {
    public static final int TYPE = 4;

    @SerializedName("id")
    @Expose
    private String marketId;

    @SerializedName("marketName")
    @Expose
    private String marketName;

    @SerializedName("currentPrice")
    @Expose
    private double currentPrice;

    public String getMarketId() {
        return marketId;
    }

    public String getMarketName() {
        return marketName;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    @Override
    public String toString() {
        return "MarketModel{" +
                "marketId='" + marketId + '\'' +
                ", marketName='" + marketName + '\'' +
                ", currentPrice=" + currentPrice +
                '}';
    }

    @Override
    public int getType() {
        return TYPE;
    }
}