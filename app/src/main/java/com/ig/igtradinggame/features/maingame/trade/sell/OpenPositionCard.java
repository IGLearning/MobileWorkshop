package com.ig.igtradinggame.features.maingame.trade.sell;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ig.igtradinggame.R;
import com.ig.igtradinggame.models.OpenPositionModel;
import com.ig.igtradinggame.ui.BaseCardView;
import com.ig.igtradinggame.models.CardModel;

import java.text.DecimalFormat;

import butterknife.BindView;

public class OpenPositionCard extends BaseCardView {

    @BindView(R.id.textView_openpositioncard_name)
    TextView marketName;

    @BindView(R.id.textView_openpositioncard_price)
    TextView marketPrice;

    @BindView(R.id.textView_openpositioncard_boughtAt)
    TextView marketBoughtAtPrice;

    public OpenPositionCard(View itemView) {
        super(itemView);
    }

    public static OpenPositionCard newInstance(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_open_positions, parent, false);
        return new OpenPositionCard(view);
    }

    @Override
    public void setup(final CardModel cardView) {
        super.setup(cardView);

        if (cardView.getType() == OpenPositionModel.TYPE) {
            OpenPositionModel openPositionsModel = (OpenPositionModel) cardView;

            marketName.setText(openPositionsModel.getMarketId());

            DecimalFormat decimalFormat = new DecimalFormat("0.00");
            marketBoughtAtPrice.setText(decimalFormat.format(openPositionsModel.getProfitAndLoss()));
            marketPrice.setText(decimalFormat.format(openPositionsModel.getOpeningPrice()));
        }
    }
}
