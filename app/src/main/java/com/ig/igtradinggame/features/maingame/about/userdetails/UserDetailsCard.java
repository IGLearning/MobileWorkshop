package com.ig.igtradinggame.features.maingame.about.userdetails;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ig.igtradinggame.R;
import com.ig.igtradinggame.models.ClientModel;
import com.ig.igtradinggame.models.UserDetailsModel;
import com.ig.igtradinggame.ui.BaseCardView;
import com.ig.igtradinggame.models.CardModel;

import butterknife.BindView;

public class UserDetailsCard extends BaseCardView {
    @BindView(R.id.textView_userdetails_name)
    TextView name;

    @BindView(R.id.textView_userdetails_available)
    TextView availableBalance;

    @BindView(R.id.textView_userdetails_pnl)
    TextView pnl;


    private UserDetailsCard(View itemView) {
        super(itemView);
    }

    public static UserDetailsCard newInstance(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_userdetails, parent, false);
        return new UserDetailsCard(view);
    }

    @Override
    public void setup(CardModel cardView) {
        if (cardView.getType() == UserDetailsModel.TYPE) {
            UserDetailsModel userDetailsModel = (UserDetailsModel) cardView;

            if (userDetailsModel.getClientModel() == null) {
                name.setText("loading");
                return;
            }

            final ClientModel clientModel = userDetailsModel.getClientModel();

            name.setText(clientModel.getUserName());
            availableBalance.setText(Double.toString(clientModel.getAvailableFunds()));
            pnl.setText(Double.toString(clientModel.getRunningProfitAndLoss()));
        }
    }
}
