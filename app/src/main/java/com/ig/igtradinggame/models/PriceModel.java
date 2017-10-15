package com.ig.igtradinggame.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class PriceModel {
    @SerializedName("marketId")
    @Expose
    private String marketId;

    @SerializedName("price")
    @Expose
    private double price;

    public String getMarketId() {
        return marketId;
    }

    public double getPrice() {
        return price;
    }
}
