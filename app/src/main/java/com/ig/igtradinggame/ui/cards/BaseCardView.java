package com.ig.igtradinggame.ui.cards;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

public abstract class BaseCardView extends RecyclerView.ViewHolder implements CardView {
    public BaseCardView(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public abstract void setup(CardModel cardView);
}