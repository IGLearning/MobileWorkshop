package com.ig.igtradinggame.ui.fragments;


import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ig.igtradinggame.R;
import com.ig.igtradinggame.models.MarketModel;
import com.ig.igtradinggame.network.IGAPIService;
import com.ig.igtradinggame.storage.BaseUrlStorage;
import com.ig.igtradinggame.storage.SharedPreferencesStorage;
import com.ig.igtradinggame.ui.bottomsheets.OpenPositionBottomsheetFragment;
import com.ig.igtradinggame.ui.cards.BaseCardView;
import com.ig.igtradinggame.ui.cards.CardModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Predicate;

import static android.content.ContentValues.TAG;

public class OpenPositionFragment extends BaseFragment implements BaseCardView.OnItemClickListener {
    private static final int HEARTBEAT_FREQUENCY_MILLIS = 500;

    @BindView(R.id.recyclerView_buy)
    RecyclerView buyRecyclerView;

    private IGAPIService apiService;
    private ArrayList<CardModel> cardModelList;
    private OpenPositionListAdapter adapter;
    private boolean shouldUpdatePrices = true;

    public OpenPositionFragment() {
        cardModelList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_open_position, container, false);
        unbinder = ButterKnife.bind(this, view);
        setup();
        return view;
    }

    @Override
    public void onDestroyView() {
        shouldUpdatePrices = false;
        super.onDestroyView();
    }

    private void setup() {
        BaseUrlStorage storage = new SharedPreferencesStorage(PreferenceManager.getDefaultSharedPreferences(getActivity()));
        this.apiService = new IGAPIService(storage.loadBaseUrl());

        setupRecyclerView();
        setupCards();
    }

    private void setupCards() {
        apiService.getAllMarketsStreaming(HEARTBEAT_FREQUENCY_MILLIS)
                .takeWhile(new Predicate<List<MarketModel>>() {
                    @Override
                    public boolean test(@NonNull List<MarketModel> marketModels) throws Exception {
                        return shouldUpdatePrices;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<MarketModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull List<MarketModel> marketModels) {
                        cardModelList.clear();
                        cardModelList.addAll(marketModels);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void setupRecyclerView() {
        buyRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        if (buyRecyclerView != null) {
            adapter = new OpenPositionListAdapter(cardModelList, this);
            buyRecyclerView.setAdapter(adapter);
        }

        buyRecyclerView.setLayoutManager(linearLayoutManager);

        // Turn off blinking when updating
        RecyclerView.ItemAnimator animator = buyRecyclerView.getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }
    }

    @Override
    public void onItemClick(CardModel cardModel) {
        // OPEN THE BOTTOMSHEET
        Log.e(TAG, "onItemClick: yaeh");
        OpenPositionBottomsheetFragment bottomsheet = new OpenPositionBottomsheetFragment();
        bottomsheet.show(getActivity().getSupportFragmentManager(), "Bottomsheet");
    }
}
