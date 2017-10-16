package com.ig.igtradinggame.ui.cards.balance;

import com.ig.igtradinggame.ui.cards.CardViewModel;

public class BalanceCardViewModel implements CardViewModel {
    public static final int TYPE_ID = 1;

    @Override
    public int getType() {
        return TYPE_ID;
    }
}
