package com.ig.igtradinggame.ui.cards.openpositions;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ig.igtradinggame.R;
import com.ig.igtradinggame.models.OpenPositionModel;
import com.ig.igtradinggame.ui.cards.BaseCardView;
import com.ig.igtradinggame.ui.cards.CardModel;

import butterknife.BindView;

public class OpenPositionsCard extends BaseCardView {

    @BindView(R.id.textView_openpositioncard_name)
    TextView marketName;

    @BindView(R.id.textView_openpositioncard_price)
    TextView marketPrice;

    @BindView(R.id.textView_openpositioncard_boughtAt)
    TextView marketBoughtAtPrice;

    public OpenPositionsCard(View itemView) {
        super(itemView);
    }

    public static OpenPositionsCard newInstance(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_open_positions, parent, false);
        return new OpenPositionsCard(view);
    }

    @Override
    public void setup(final CardModel cardView) {
        super.setup(cardView);

        if (cardView.getType() == OpenPositionModel.TYPE) {
            OpenPositionModel openPositionsModel = (OpenPositionModel) cardView;

            marketName.setText(openPositionsModel.getMarketId());
            marketBoughtAtPrice.setText(Double.toString(openPositionsModel.getProfitAndLoss()));
            marketPrice.setText(Double.toString(openPositionsModel.getOpeningPrice()));
        }
    }
}
