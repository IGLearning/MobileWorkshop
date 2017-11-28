package com.ig.igtradinggame.features.maingame.trade.buy;

import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.ig.igtradinggame.R;
import com.ig.igtradinggame.models.CardModel;
import com.ig.igtradinggame.models.MarketModel;
import com.ig.igtradinggame.models.OpenPositionIdResponse;
import com.ig.igtradinggame.network.IGAPIServiceInterface;
import com.ig.igtradinggame.network.retrofit_impl.IGAPIService;
import com.ig.igtradinggame.storage.AppStorage;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class BuyPopupView extends BottomSheetDialogFragment {

    @BindView(R.id.progressBar_buy)
    ProgressBar progressBar;

    @BindView(R.id.button_bottomsheet_buy)
    Button buyButton;

    private Unbinder unbinder;
    private CardModel cardModel;
    private PopupCallback popupCallback;

    public BuyPopupView() {
    } // required empty constructor

    public void addModel(@NonNull final CardModel cardmodel) {
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
        super.setupDialog(dialog, style); // not a real error, is an Android Studio bug
        View content = View.inflate(getContext(), R.layout.bottomsheet_buy, null);
        dialog.setContentView(content);

        unbinder = ButterKnife.bind(this, content);
        progressBar.setVisibility(GONE);
    }

    @OnClick(R.id.button_bottomsheet_buy)
    public void buyTapped() {
        if (cardModel == null) {
            return;
        }

        if (cardModel.getType() == MarketModel.TYPE) {
            MarketModel marketModel = (MarketModel) cardModel;
            openPositionForMarket(marketModel);

            progressBar.setIndeterminate(true);
            progressBar.setVisibility(VISIBLE);
            buyButton.setVisibility(GONE);
        }
    }

    private void openPositionForMarket(MarketModel marketModel) {
        final String baseUrl = AppStorage.getInstance(getActivity()).loadBaseUrl();
        final String clientId = AppStorage.getInstance(getActivity()).loadClientId();
        final IGAPIServiceInterface igapiService = new IGAPIService(baseUrl);

        final OpenPositionRequest openPositionRequest = new OpenPositionRequest(marketModel.getMarketId(), 1);

        igapiService.openPosition(clientId, openPositionRequest, new IGAPIService.OnOpenPositionCompleteListener() {
            @Override
            public void onComplete(OpenPositionIdResponse openPositionIdResponse) {
                if (popupCallback != null) {
                    progressBar.setProgress(100);
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

    interface PopupCallback {
        void onSuccess(OpenPositionIdResponse response);

        void onError(String errorMessage);
    }
}
