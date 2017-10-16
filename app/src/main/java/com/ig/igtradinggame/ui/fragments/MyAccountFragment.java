package com.ig.igtradinggame.ui.fragments;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ig.igtradinggame.R;
import com.ig.igtradinggame.ui.cards.CardListAdapter;
import com.ig.igtradinggame.ui.cards.CardViewModel;
import com.ig.igtradinggame.ui.cards.balance.BalanceCardViewModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public final class MyAccountFragment extends BaseFragment {
    @BindView(R.id.recycerview_my_account)
    RecyclerView accountRecyclerView;

    public MyAccountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_account, container, false);
        unbinder = ButterKnife.bind(this, view);

        setupRecyclerView();
        return view;
    }

    private void setupRecyclerView() {
        accountRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        ArrayList<CardViewModel> cardViews = new ArrayList<>();
        cardViews.add(new BalanceCardViewModel());

        if (accountRecyclerView != null) {
            accountRecyclerView.setAdapter(new CardListAdapter(cardViews));
        }

        accountRecyclerView.setLayoutManager(linearLayoutManager);
    }
}
