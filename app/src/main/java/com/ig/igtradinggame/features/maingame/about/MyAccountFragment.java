package com.ig.igtradinggame.features.maingame.about;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ig.igtradinggame.R;
import com.ig.igtradinggame.features.maingame.about.button.ButtonModel;
import com.ig.igtradinggame.features.maingame.about.profilecard.ProfileModel;
import com.ig.igtradinggame.models.CardModel;
import com.ig.igtradinggame.models.ClientModel;
import com.ig.igtradinggame.network.retrofit_impl.IGAPIService;
import com.ig.igtradinggame.storage.AppStorage;
import com.ig.igtradinggame.ui.BaseFragment;
import com.ig.igtradinggame.ui.CardListAdapter;

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
    private String clientId;

    private String baseUrl;


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
        clientId = AppStorage.getInstance(getActivity()).loadClientId();

        baseUrl = AppStorage.getInstance(getActivity()).loadBaseUrl();
        apiService = new IGAPIService(baseUrl);

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
            adapter = new CardListAdapter(cardModelList, null);
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
        cardModelList.add(new ProfileModel(
                "Loading....",
                "Loading",
                "Loading",
                "Loading"
        ));

        startUpdatingProfile(0);

        cardModelList.add(new ButtonModel(new ButtonModel.ButtonClickListener() {
            @Override
            public void onButtonClick() {
                Log.d("DEBUG BUTTON", "Button Tap Recognised. Now do something with it!");
            }
        }));
    }

    private void startUpdatingProfile(final int cardPosition) {
        apiService.getClientInfoStreaming(clientId, HEARTBEAT_FREQUENCY_MILLIS)
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
                        ProfileModel model = new ProfileModel(clientModel);
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
