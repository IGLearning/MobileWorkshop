package com.ig.igtradinggame.features.maingame.about.profilecard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ig.igtradinggame.R;
import com.ig.igtradinggame.models.CardModel;
import com.ig.igtradinggame.ui.BaseCardView;

import butterknife.BindView;

public class ProfileCard extends BaseCardView {

    @BindView(R.id.textView_profile_name)
    TextView nameText;

    @BindView(R.id.textView_profile_available)
    TextView availableFundsText;

    @BindView(R.id.textView_profile_id)
    TextView clientId;

    @BindView(R.id.textView_profile_pnl)
    TextView currentPnl;

    private ProfileCard(View itemView) {
        super(itemView);
    }

    public static ProfileCard newInstance(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_profile, parent, false);
        return new ProfileCard(view);
    }

    @Override
    public void setup(CardModel cardModel) {
        super.setup(cardModel);

        if (cardModel instanceof ProfileModel) {
            ProfileModel model = (ProfileModel) cardModel;

            nameText.setText(model.getName());
            clientId.setText(model.getClientId());
        }
    }
}
