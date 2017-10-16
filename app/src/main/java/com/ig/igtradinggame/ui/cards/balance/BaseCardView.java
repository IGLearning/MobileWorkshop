package com.ig.igtradinggame.ui.cards.balance;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ig.igtradinggame.ui.cards.CardView;
import com.ig.igtradinggame.ui.cards.CardViewModel;

import butterknife.ButterKnife;

public abstract class BaseCardView extends RecyclerView.ViewHolder implements CardView {
    public BaseCardView(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public abstract void setup(CardViewModel cardView);
}