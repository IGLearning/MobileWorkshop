package com.ig.igtradinggame.features.maingame.trade.sell;

import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetBehavior.BottomSheetCallback;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import android.widget.Toast;

import com.ig.igtradinggame.R;
import com.ig.igtradinggame.models.CardModel;
import com.ig.igtradinggame.models.OpenPositionModel;
import com.ig.igtradinggame.network.retrofit_impl.IGAPIService;
import com.ig.igtradinggame.storage.AppStorage;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SellPopupView extends BottomSheetDialogFragment {
    public interface PopupCallback {
        void onSuccess();

        void onError(String errorMessage);
    }

    private Unbinder unbinder;
    private CardModel cardModel;
    private PopupCallback popupCallback;
    private BottomSheetCallback callback;

    public SellPopupView() {
    }

    public void setPopupCallback(PopupCallback callback) {
        this.popupCallback = callback;
    }

    public void addModel(final CardModel cardmodel) {
        this.cardModel = cardmodel;
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

    private BottomSheetBehavior bottomSheetBehavior;

    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style); // not a real warning, is an Android Studio bug

        View content = View.inflate(getContext(), R.layout.bottomsheet_sell, null);
        unbinder = ButterKnife.bind(this, content);
        dialog.setContentView(content);

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) content.getParent()).getLayoutParams(); // ew
        CoordinatorLayout.Behavior behavior = params.getBehavior();

        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            this.bottomSheetBehavior = (BottomSheetBehavior) behavior;
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(callback);
        }
    }

    @OnClick(R.id.button_bottomsheet_sell)
    public void sellTapped() {
        if (cardModel == null) {
            return;
        }

        if (cardModel.getType() == OpenPositionModel.TYPE) {
            OpenPositionModel openPositionModel = (OpenPositionModel) cardModel;

            String baseUrl = AppStorage.getInstance(getActivity()).loadBaseUrl();
            String clientId = AppStorage.getInstance(getActivity()).loadClientId();

            IGAPIService igapiService = new IGAPIService(baseUrl);

            igapiService.closePosition(clientId, openPositionModel.getId(), new IGAPIService.OnClosePositionCompleteListener() {
                @Override
                public void onComplete() {
                    if (popupCallback != null) {
                        popupCallback.onSuccess();
                    }
                    Toast.makeText(getContext(), "Sold!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(String errorMessage) {
                    if (popupCallback != null) {
                        popupCallback.onError(errorMessage);
                    }

                    Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                }
            });


        }
    }
}
