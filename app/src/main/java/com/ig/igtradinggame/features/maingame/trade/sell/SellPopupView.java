package com.ig.igtradinggame.features.maingame.trade.sell;

import android.app.Dialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.ig.igtradinggame.R;
import com.ig.igtradinggame.models.CardModel;
import com.ig.igtradinggame.models.OpenPositionModel;
import com.ig.igtradinggame.network.retrofit_impl.IGAPIService;
import com.ig.igtradinggame.storage.AppStorage;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class SellPopupView extends BottomSheetDialogFragment {

    @BindView(R.id.progressBar_sell)
    ProgressBar progressBar;

    @BindView(R.id.button_bottomsheet_sell)
    Button sellButton;

    private Unbinder unbinder;
    private CardModel cardModel;
    private PopupCallback popupCallback;

    public SellPopupView() {
    } // required empty constructor

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

    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style); // not a real warning, is an Android Studio bug

        View content = View.inflate(getContext(), R.layout.bottomsheet_sell, null);
        dialog.setContentView(content);

        unbinder = ButterKnife.bind(this, content);
        progressBar.setVisibility(GONE);
    }

    @OnClick(R.id.button_bottomsheet_sell)
    public void sellTapped() {
        if (cardModel == null) {
            return;
        }

        if (cardModel.getType() == OpenPositionModel.TYPE) {
            final OpenPositionModel openPositionModel = (OpenPositionModel) cardModel;
            closePosition(openPositionModel);

            progressBar.setIndeterminate(true);
            progressBar.setVisibility(VISIBLE);
            sellButton.setVisibility(GONE);
        }
    }

    private void closePosition(OpenPositionModel openPositionModel) {
        final String baseUrl = AppStorage.getInstance(getActivity()).loadBaseUrl();
        final String clientId = AppStorage.getInstance(getActivity()).loadClientId();
        final IGAPIService igapiService = new IGAPIService(baseUrl);

        igapiService.closePosition(clientId, openPositionModel.getId(), new IGAPIService.OnClosePositionCompleteListener() {
            @Override
            public void onComplete() {
                if (popupCallback != null) {
                    popupCallback.onSuccess();
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

    public interface PopupCallback {
        void onSuccess();

        void onError(String errorMessage);
    }
}
