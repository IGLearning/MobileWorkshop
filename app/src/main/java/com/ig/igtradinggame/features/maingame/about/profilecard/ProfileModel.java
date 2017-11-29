package com.ig.igtradinggame.features.maingame.about.profilecard;

import com.ig.igtradinggame.models.CardModel;
import com.ig.igtradinggame.models.ClientModel;

import java.text.DecimalFormat;

public class ProfileModel extends CardModel {
    public static final int TYPE = 6;

    private String name;
    private String clientId;
    private String availableFunds;
    private String pnl;

    public ProfileModel(String name, String clientId, String availableFunds, String pnl) {
        this.name = name;
        this.clientId = clientId;
        this.availableFunds = availableFunds;
        this.pnl = pnl;
    }

    public ProfileModel(ClientModel clientModel) {
        this.name = clientModel.getUserName();
        this.clientId = clientModel.getId();

        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        this.availableFunds = decimalFormat.format(clientModel.getAvailableFunds());
        this.pnl = decimalFormat.format(clientModel.getRunningProfitAndLoss());
    }

    @Override
    public int getType() {
        return TYPE;
    }

    public String getName() {
        return name;
    }

    public String getClientId() {
        return clientId;
    }

    public String getAvailableFunds() {
        return availableFunds;
    }

    public String getPnl() {
        return pnl;
    }
}
