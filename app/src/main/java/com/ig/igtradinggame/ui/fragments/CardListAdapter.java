package com.ig.igtradinggame.ui.fragments;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.ig.igtradinggame.ui.cards.CardModel;
import com.ig.igtradinggame.ui.cards.balance.BalanceCard;
import com.ig.igtradinggame.ui.cards.balance.BalanceModel;
import com.ig.igtradinggame.ui.cards.BaseCardView;
import com.ig.igtradinggame.ui.cards.userdetails.UserDetailsCard;
import com.ig.igtradinggame.ui.cards.userdetails.UserDetailsModel;

import java.util.ArrayList;

public class CardListAdapter extends RecyclerView.Adapter<BaseCardView> {
    private ArrayList<CardModel> cardViews;

    public CardListAdapter(ArrayList<CardModel> cardViews) {
        this.cardViews = cardViews;
    }

    @Override
    public int getItemViewType(int position) {
        return cardViews.get(position).getType();
    }

    @Override
    public BaseCardView onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case BalanceModel.TYPE:
                return BalanceCard.newInstance(parent);
            case UserDetailsModel.TYPE:
                return UserDetailsCard.newInstance(parent);
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