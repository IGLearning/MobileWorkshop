package com.ig.igtradinggame.ui.cards;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;


public abstract class BaseCardView extends RecyclerView.ViewHolder implements CardView, View.OnClickListener {
    protected View view;
    private OnItemClickListener onClickListener;
    private CardModel model;

    public BaseCardView(View itemView) {
        super(itemView);
        this.view = itemView;
        view.setOnClickListener(this);
        ButterKnife.bind(this, itemView);
    }

    public void setOnClickListener(OnItemClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setup(CardModel cardView) {
        this.model = cardView;
    }

    @Override
    public void onClick(View v) {
        if (onClickListener != null) {
            onClickListener.onItemClick(model);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(CardModel cardModel);
    }
}