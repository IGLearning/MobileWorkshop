package com.ig.igtradinggame.ui.fragments;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import com.ig.igtradinggame.models.MarketModel;
import com.ig.igtradinggame.models.OpenPositionModel;
import com.ig.igtradinggame.ui.cards.BaseCardView;
import com.ig.igtradinggame.ui.cards.BaseCardView.OnItemClickListener;
import com.ig.igtradinggame.ui.cards.CardModel;
import com.ig.igtradinggame.ui.cards.market.MarketCard;
import com.ig.igtradinggame.ui.cards.openpositions.OpenPositionsCard;

import java.util.ArrayList;

public class OpenPositionListAdapter extends RecyclerView.Adapter<BaseCardView> implements OnItemClickListener {
    private final OnItemClickListener onItemClickListener;
    private ArrayList<CardModel> cardViews;

    public OpenPositionListAdapter(ArrayList<CardModel> cardViews, OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        this.cardViews = cardViews;
    }

    @Override
    public int getItemViewType(int position) {
        return cardViews.get(position).getType();
    }

    @Override
    public BaseCardView onCreateViewHolder(ViewGroup parent, int viewType) {

        // TODO: Adding cards to the 'buy' or 'current positions' page? Add the types in as cases here
        switch (viewType) {
            case MarketModel.TYPE:
                return MarketCard.newInstance(parent);
            case OpenPositionModel.TYPE:
                return OpenPositionsCard.newInstance(parent);
        }

        Log.e("OpenPositionListAdapter", "onCreateViewHolder: You haven't added in the model with type " + viewType);
        return null;
    }

    @Override
    public void onBindViewHolder(BaseCardView holder, int position) {
        holder.setup(cardViews.get(position));
        holder.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return cardViews.size();
    }


    @Override
    public void onItemClick(CardModel cardModel) {
        onItemClickListener.onItemClick(cardModel);
    }
}
