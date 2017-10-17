package com.ig.igtradinggame.ui.cards.balance;

import com.ig.igtradinggame.ui.cards.CardModel;

/**
 * Note that this is a MODEL, so therefore should not have to
 * import any Android dependencies. This is core to Clean
 * Architecture.
 */
public class BalanceModel extends CardModel {
    public static final int TYPE = 1;
    private double balance;

    public BalanceModel(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    @Override
    public int getType() {
        return TYPE;
    }
}
