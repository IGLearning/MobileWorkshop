package com.ig.igtradinggame.ui.cards.balance;

import com.ig.igtradinggame.ui.cards.CardViewModel;

public class BalanceCardViewModel implements CardViewModel {
    public static final int TYPE_ID = 1;
    private final int balance;

    public BalanceCardViewModel(int balance) {
        this.balance = balance;
    }

    public int getBalance() {
        return balance;
    }

    @Override
    public int getType() {
        return TYPE_ID;
    }
}
