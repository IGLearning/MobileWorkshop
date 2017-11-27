package com.ig.igtradinggame.features.maingame.trade.buy;

import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ig.igtradinggame.R;
import com.ig.igtradinggame.models.CardModel;
import com.ig.igtradinggame.models.MarketModel;
import com.ig.igtradinggame.models.OpenPositionIdResponse;
import com.ig.igtradinggame.network.retrofit_impl.IGAPIService;
import com.ig.igtradinggame.storage.AppStorage;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ConfirmationPopupView extends BottomSheetDialogFragment {
    public interface PopupCallback {
        void onSuccess(OpenPositionIdResponse response);

        void onError(String errorMessage);
    }

    private Unbinder unbinder;
    private CardModel cardModel;
    private PopupCallback popupCallback;

    private BottomSheetBehavior.BottomSheetCallback callback = new BottomSheetBehavior.BottomSheetCallback() {
        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            //TODO: Use me if you'd like
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {

        }
    };

    public ConfirmationPopupView() {
    }

    public void addModel(final CardModel cardmodel) {
        this.cardModel = cardmodel;
    }

    public void setPopupCallback(PopupCallback callback) {
        this.popupCallback = callback;
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style); // not a real warning, is an Android Studio bug
        View content = View.inflate(getContext(), R.layout.bottomsheet_buy, null);
        unbinder = ButterKnife.bind(this, content);
        dialog.setContentView(content);

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) content.getParent()).getLayoutParams(); // ew
        CoordinatorLayout.Behavior behavior = params.getBehavior();

        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(callback);
        }
    }

    @OnClick(R.id.button_bottomsheet_buy)
    public void buyTapped() {
        if (cardModel == null) {
            return;
        }

        if (cardModel.getType() == MarketModel.TYPE) {
            MarketModel marketModel = (MarketModel) cardModel;

            String baseUrl = AppStorage.getInstance(getActivity()).loadBaseUrl();
            String clientId = AppStorage.getInstance(getActivity()).loadClientId();

            IGAPIService igapiService = new IGAPIService(baseUrl);
            OpenPositionRequest openPositionRequest = new OpenPositionRequest(((MarketModel) cardModel).getMarketId(), 1);

            igapiService.openPosition(clientId, openPositionRequest, new IGAPIService.OnOpenPositionCompleteListener() {
                @Override
                public void onComplete(OpenPositionIdResponse openPositionIdResponse) {
                    if (popupCallback != null) {
                        popupCallback.onSuccess(openPositionIdResponse);
                    }
                }

                @Override
                public void onError(String errorMessage) {
                    if (popupCallback != null) {
                        popupCallback.onError(errorMessage);
                    }
                }
            });
        }
    }
}
