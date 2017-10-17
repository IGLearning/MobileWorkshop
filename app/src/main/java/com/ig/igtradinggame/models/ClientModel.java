package com.ig.igtradinggame.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClientModel {
    @SerializedName("userName")
    @Expose
    private String userName;

    @SerializedName("availableFunds")
    @Expose
    private double availableFunds;

    @SerializedName("runningProfitAndLoss")
    @Expose
    private double runningProfitAndLoss;

    @SerializedName("id")
    @Expose
    private String id;

    public ClientModel(String userName, double availableFunds, double runningProfitAndLoss, String id) {
        this.userName = userName;
        this.availableFunds = availableFunds;
        this.runningProfitAndLoss = runningProfitAndLoss;
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public double getAvailableFunds() {
        return availableFunds;
    }

    public double getRunningProfitAndLoss() {
        return runningProfitAndLoss;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "userName:" + userName +
                "\navailableFunds:" + availableFunds +
                "\nrunningProfitAndLoss:" + runningProfitAndLoss +
                "\nid:" + id;
    }
}
