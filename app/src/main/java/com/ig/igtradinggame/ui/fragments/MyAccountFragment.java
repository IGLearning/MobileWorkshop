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
import com.ig.igtradinggame.models.ClientModel;
import com.ig.igtradinggame.network.IGAPIService;
import com.ig.igtradinggame.storage.ClientIDStorage;
import com.ig.igtradinggame.storage.SharedPreferencesStorage;
import com.ig.igtradinggame.ui.cards.CardModel;
import com.ig.igtradinggame.ui.cards.balance.BalanceModel;
import com.ig.igtradinggame.ui.cards.button.ButtonModel;
import com.ig.igtradinggame.ui.cards.userdetails.UserDetailsModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Predicate;


public final class MyAccountFragment extends BaseFragment {
    private static final int HEARTBEAT_FREQUENCY_MILLIS = 500;

    @BindView(R.id.recycerview_my_account)
    RecyclerView accountRecyclerView;

    private ArrayList<CardModel> cardModelList;
    private CardListAdapter adapter;
    private IGAPIService apiService;
    private String clientID;
    private ClientIDStorage clientIDStorage;
    private boolean shouldUpdatePrices = true;

    public MyAccountFragment() {
        cardModelList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_account, container, false);
        unbinder = ButterKnife.bind(this, view);
        setup();
        return view;
    }

    private void setup() {
        apiService = new IGAPIService();
        clientIDStorage = new SharedPreferencesStorage(PreferenceManager.getDefaultSharedPreferences(getActivity()));
        clientID = clientIDStorage.loadClientId();
        setupRecyclerView();
        setupCards();
    }

    @Override
    public void onDestroyView() {
        shouldUpdatePrices = false;
        super.onDestroyView();
    }

    private void setupRecyclerView() {
        accountRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        if (accountRecyclerView != null) {
            adapter = new CardListAdapter(cardModelList);
            accountRecyclerView.setAdapter(adapter);
        }

        accountRecyclerView.setLayoutManager(linearLayoutManager);

        // Turn off blinking when updating
        RecyclerView.ItemAnimator animator = accountRecyclerView.getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }
    }

    private void setupCards() {
        cardModelList.add(new BalanceModel(0));
        startUpdatingBalance(0);

        cardModelList.add(new UserDetailsModel(null));
        startUpdatingUserDetails(1);

        cardModelList.add(new ButtonModel(new ButtonModel.ButtonClickListener() {
            @Override
            public void onButtonClick() {
                Log.d("DEBUG BUTTON", "Button Tap Recognised. Now do something with it!");
            }
        }));
    }

    private void startUpdatingBalance(final int cardPosition) {
        apiService.getClientInfoStreaming(clientID, HEARTBEAT_FREQUENCY_MILLIS)
                .takeWhile(new Predicate<ClientModel>() {
                    @Override
                    public boolean test(@NonNull ClientModel clientModel) throws Exception {
                        return shouldUpdatePrices;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ClientModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull ClientModel clientModel) {
                        BalanceModel model = new BalanceModel(clientModel.getAvailableFunds());
                        cardModelList.set(cardPosition, model);
                        adapter.notifyItemChanged(cardPosition);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    private void startUpdatingUserDetails(final int cardPosition) {
        apiService.getClientInfoStreaming(clientID, HEARTBEAT_FREQUENCY_MILLIS)
                .takeWhile(new Predicate<ClientModel>() {
                    @Override
                    public boolean test(@NonNull ClientModel clientModel) throws Exception {
                        return shouldUpdatePrices;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ClientModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ClientModel clientModel) {
                        UserDetailsModel model = new UserDetailsModel(clientModel);
                        cardModelList.set(cardPosition, model);
                        adapter.notifyItemChanged(cardPosition);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
