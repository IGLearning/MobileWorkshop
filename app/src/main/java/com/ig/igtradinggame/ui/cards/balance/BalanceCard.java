package com.ig.igtradinggame.ui.cards.balance;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ig.igtradinggame.R;
import com.ig.igtradinggame.ui.cards.CardView;
import com.ig.igtradinggame.ui.cards.CardViewModel;

import butterknife.BindView;


public final class BalanceCard extends BaseCardView implements CardView {
    @BindView(R.id.textView_balance)
    TextView balanceText;

    private BalanceCardViewModel viewModel;

    public BalanceCard(View itemView) {
        super(itemView);
    }

    @Override
    public void setup(CardViewModel cardViewModel) {
        if (cardViewModel.getType() == BalanceCardViewModel.TYPE_ID) {
            viewModel = (BalanceCardViewModel) cardViewModel;
        }
    }

    public static BalanceCard newInstance(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_balance, parent, false);
        return new BalanceCard(view);
    }
}