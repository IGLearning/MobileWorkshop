package com.ig.igtradinggame.ui.fragments;


import android.os.Bundle;
import android.os.Handler;
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
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;


public final class MyAccountFragment extends BaseFragment {
    @BindView(R.id.recycerview_my_account)
    RecyclerView accountRecyclerView;

    private ArrayList<CardViewModel> cardViewModelList;
    private CardListAdapter adapter;

    public MyAccountFragment() {
        cardViewModelList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_account, container, false);
        unbinder = ButterKnife.bind(this, view);
        setupRecyclerView();
        setupCards();
        return view;
    }

    private void setupRecyclerView() {
        accountRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        if (accountRecyclerView != null) {
            adapter = new CardListAdapter(cardViewModelList);
            accountRecyclerView.setAdapter(adapter);
        }

        accountRecyclerView.setLayoutManager(linearLayoutManager);
    }

    private void setupCards() {
        cardViewModelList.add(new BalanceCardViewModel(50));
        adapter.notifyDataSetChanged();

        (new Handler()).postDelayed(new TimerTask() {
            @Override
            public void run() {
                cardViewModelList.set(0, new BalanceCardViewModel(100));
                adapter.notifyItemChanged(0);
            }
        }, 5000);
    }
}
