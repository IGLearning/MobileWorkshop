package com.ig.igtradinggame.network.market;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Market {
    @SerializedName("marketId")
    @Expose
    private String marketId;

    @SerializedName("marketName")
    @Expose
    private String marketName;

    @SerializedName("currentPrice")
    @Expose
    private float currentPrice;
}