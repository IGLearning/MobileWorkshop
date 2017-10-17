package com.ig.igtradinggame.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class OpenPositionIdResponse {

    @SerializedName("openPositionId")
    @Expose
    private String openPositionId;

    public String getOpenPositionId() {
        return openPositionId;
    }
}