package com.ig.igtradinggame.ui.cards.market;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ig.igtradinggame.R;
import com.ig.igtradinggame.models.MarketModel;
import com.ig.igtradinggame.ui.cards.BaseCardView;
import com.ig.igtradinggame.ui.cards.CardModel;

import butterknife.BindView;

public class MarketCard extends BaseCardView {
    @BindView(R.id.textView_marketcard_id)
    TextView marketID;

    @BindView(R.id.textView_marketcard_name)
    TextView marketName;

    @BindView(R.id.textView_marketcard_price)
    TextView marketPrice;

    private MarketCard(View itemView) {
        super(itemView);
    }

    public static MarketCard newInstance(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_market, parent, false);
        return new MarketCard(view);
    }

    @Override
    public void setup(CardModel cardView) {
        super.setup(cardView);

        if (cardView.getType() == MarketModel.TYPE) {
            MarketModel marketModel = (MarketModel) cardView;

            marketID.setText(marketModel.getMarketId());
            marketName.setText(marketModel.getMarketName());
            marketPrice.setText(Float.toString(marketModel.getCurrentPrice()));
        }
    }
}
