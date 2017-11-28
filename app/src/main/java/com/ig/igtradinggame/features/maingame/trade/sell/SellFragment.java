package com.ig.igtradinggame.features.maingame.trade.sell;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ig.igtradinggame.R;
import com.ig.igtradinggame.features.maingame.trade.sell.SellPopupView.PopupCallback;
import com.ig.igtradinggame.models.MarketModel;
import com.ig.igtradinggame.models.OpenPositionModel;
import com.ig.igtradinggame.network.retrofit_impl.IGAPIService;
import com.ig.igtradinggame.storage.AppStorage;
import com.ig.igtradinggame.ui.BaseFragment;
import com.ig.igtradinggame.ui.CardListAdapter;
import com.ig.igtradinggame.ui.BaseCardView;
import com.ig.igtradinggame.models.CardModel;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

import static android.content.ContentValues.TAG;


public class SellFragment extends BaseFragment implements BaseCardView.OnItemClickListener {
    public static final int HEARTBEAT_FREQUENCY_MILLIS = 500;

    @BindView(R.id.recyclerView_current_positions)
    RecyclerView currentPositionsRecyclerView;

    private IGAPIService apiService;
    private CardListAdapter adapter;
    private ArrayList<CardModel> cardModelList;
    private boolean shouldUpdatePrices = true;

    private String clientId;
    private String baseUrl;


    public SellFragment() {
        this.cardModelList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_current_positions, container, false);
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
        clientId = AppStorage.getInstance(getActivity()).loadClientId();
        baseUrl = AppStorage.getInstance(getActivity()).loadBaseUrl();

        this.apiService = new IGAPIService(baseUrl);
        setupRecyclerView();
        setupCards();
    }

    private void setupCards() {
        apiService.getOpenPositionsStreaming(clientId, HEARTBEAT_FREQUENCY_MILLIS)
                .takeWhile(new Predicate<List<OpenPositionModel>>() {
                    @Override
                    public boolean test(@NonNull List<OpenPositionModel> openPositionModels) throws Exception {
                        return shouldUpdatePrices;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<OpenPositionModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull List<OpenPositionModel> openPositionModels) {
                        cardModelList.clear();
                        cardModelList.addAll(openPositionModels);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void setupRecyclerView() {
        currentPositionsRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        if (currentPositionsRecyclerView != null) {
            adapter = new CardListAdapter(cardModelList, this);
            currentPositionsRecyclerView.setAdapter(adapter);
        }

        currentPositionsRecyclerView.setLayoutManager(linearLayoutManager);

        // Turn off blinking when updating
        RecyclerView.ItemAnimator animator = currentPositionsRecyclerView.getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }
    }

    @Override
    public void onItemClick(final CardModel cardModel) {
        final SellPopupView bottomsheet = new SellPopupView();
        bottomsheet.addModel(cardModel);
        bottomsheet.show(getActivity().getSupportFragmentManager(), "close_bottomsheet");

        bottomsheet.setPopupCallback(new PopupCallback() {
            @Override
            public void onSuccess() {
                bottomsheet.dismiss();
                cardModelList = new ArrayList<>();
                adapter.notifyDataSetChanged();
                setup();

                OpenPositionModel openPositionModel = (OpenPositionModel) cardModel;

                DecimalFormat decimalFormat = new DecimalFormat("0.00");
                String closingPnl = decimalFormat.format(openPositionModel.getProfitAndLoss());
                showMessage("Sold, with approx closing pnl: " + closingPnl, false);
            }

            @Override
            public void onError(String errorMessage) {
                bottomsheet.dismiss();
                showMessage(errorMessage, true);
            }
        });

    }

}
