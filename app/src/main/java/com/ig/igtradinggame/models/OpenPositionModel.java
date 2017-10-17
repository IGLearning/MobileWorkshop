package com.ig.igtradinggame.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OpenPositionModel {
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("marketId")
    @Expose
    public String marketId;
    @SerializedName("profitAndLoss")
    @Expose
    public int profitAndLoss;
    @SerializedName("openingPrice")
    @Expose
    public int openingPrice;
    @SerializedName("buySize")
    @Expose
    public int buySize;

    public OpenPositionModel(String id, String marketId, int profitAndLoss, int openingPrice, int buySize) {
        this.id = id;
        this.marketId = marketId;
        this.profitAndLoss = profitAndLoss;
        this.openingPrice = openingPrice;
        this.buySize = buySize;
    }

    public String getId() {
        return id;
    }

    public String getMarketId() {
        return marketId;
    }

    public int getProfitAndLoss() {
        return profitAndLoss;
    }

    public int getOpeningPrice() {
        return openingPrice;
    }

    public int getBuySize() {
        return buySize;
    }
}
