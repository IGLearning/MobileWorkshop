package com.ig.igtradinggame.models;

import com.ig.igtradinggame.ui.cards.CardModel;

public class UserDetailsModel extends CardModel {
    public static final int TYPE = 2;

    private ClientModel clientModel;

    public UserDetailsModel(ClientModel clientModel) {
        this.clientModel = clientModel;
    }

    public ClientModel getClientModel() {
        return clientModel;
    }

    @Override
    public int getType() {
        return TYPE;
    }
}