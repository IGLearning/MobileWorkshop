package com.ig.igtradinggame.ui.fragments;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.ig.igtradinggame.ui.cards.CardViewModel;
import com.ig.igtradinggame.ui.cards.balance.BalanceCard;
import com.ig.igtradinggame.ui.cards.balance.BalanceCardViewModel;
import com.ig.igtradinggame.ui.cards.balance.BaseCardView;

import java.util.ArrayList;

public class CardListAdapter extends RecyclerView.Adapter<BaseCardView> {
    private ArrayList<CardViewModel> cardViews;

    public CardListAdapter(ArrayList<CardViewModel> cardViews) {
        this.cardViews = cardViews;
    }

    @Override
    public int getItemViewType(int position) {
        return cardViews.get(position).getType();
    }

    @Override
    public BaseCardView onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case BalanceCardViewModel.TYPE_ID:
                return BalanceCard.newInstance(parent);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(BaseCardView holder, int position) {
        holder.setup(cardViews.get(position));
    }

    @Override
    public int getItemCount() {
        return cardViews.size();
    }
}