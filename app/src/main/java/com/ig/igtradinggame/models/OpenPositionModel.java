package com.ig.igtradinggame.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ig.igtradinggame.ui.cards.CardModel;

public class OpenPositionModel extends CardModel {
    public static final int TYPE = 5;

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("marketId")
    @Expose
    public String marketId;
    @SerializedName("profitAndLoss")
    @Expose
    public double profitAndLoss;
    @SerializedName("openingPrice")
    @Expose
    public double openingPrice;
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

    public double getProfitAndLoss() {
        return profitAndLoss;
    }

    public double getOpeningPrice() {
        return openingPrice;
    }

    public int getBuySize() {
        return buySize;
    }

    @Override
    public int getType() {
        return TYPE;
    }
}
