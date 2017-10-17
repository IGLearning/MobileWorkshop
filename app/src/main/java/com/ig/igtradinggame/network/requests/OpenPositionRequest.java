package com.ig.igtradinggame.network.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OpenPositionRequest {
    @SerializedName("marketId")
    @Expose
    public String marketId;
    @SerializedName("openingPrice")
    @Expose
    public int openingPrice;
    @SerializedName("buySize")
    @Expose
    public int buySize;

    public OpenPositionRequest(String marketId, int openingPrice, int buySize) {
        this.marketId = marketId;
        this.openingPrice = openingPrice;
        this.buySize = buySize;
    }

    public String getMarketId() {
        return marketId;
    }

    public int getOpeningPrice() {
        return openingPrice;
    }

    public int getBuySize() {
        return buySize;
    }
}
