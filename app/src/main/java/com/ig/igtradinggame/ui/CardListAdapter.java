package com.ig.igtradinggame.ui;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.ig.igtradinggame.ui.BaseCardView.OnItemClickListener;
import com.ig.igtradinggame.models.CardModel;

import java.util.ArrayList;

public class CardListAdapter extends RecyclerView.Adapter<BaseCardView> implements OnItemClickListener {
    private OnItemClickListener onItemClickListener;
    private ArrayList<CardModel> cardViews;

    public CardListAdapter(ArrayList<CardModel> cardViews, @Nullable OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        this.cardViews = cardViews;
    }

    @Override
    public int getItemViewType(int position) {
        return cardViews.get(position).getType();
    }

    @Override
    public BaseCardView onCreateViewHolder(ViewGroup parent, int viewType) {
        return CardViewFactory.generateFromType(viewType, parent);
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
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(cardModel);
        }
    }
}
