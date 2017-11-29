package com.ig.igtradinggame.ui;

import android.util.Log;
import android.view.ViewGroup;

import com.ig.igtradinggame.features.maingame.about.BalanceCard;
import com.ig.igtradinggame.features.maingame.about.button.ButtonCard;
import com.ig.igtradinggame.features.maingame.about.button.ButtonModel;
import com.ig.igtradinggame.features.maingame.about.profilecard.ProfileCard;
import com.ig.igtradinggame.features.maingame.about.profilecard.ProfileModel;
import com.ig.igtradinggame.features.maingame.trade.buy.MarketCard;
import com.ig.igtradinggame.features.maingame.trade.sell.OpenPositionCard;
import com.ig.igtradinggame.models.BalanceModel;
import com.ig.igtradinggame.models.MarketModel;
import com.ig.igtradinggame.models.OpenPositionModel;

/**
 * Acts as a mapping from a model to a card. Don't forget to register new cards when created!
 */
public class CardViewFactory {
    public static BaseCardView generateFromType(final int type, ViewGroup parent) {
        switch (type) {
            case MarketModel.TYPE: // 4
                return MarketCard.newInstance(parent);

            case OpenPositionModel.TYPE: // 5
                return OpenPositionCard.newInstance(parent);

            case BalanceModel.TYPE: // 1
                return BalanceCard.newInstance(parent);

            case ButtonModel.TYPE: // 3
                return ButtonCard.newInstance(parent);

            case ProfileModel.TYPE: // 6
                return ProfileCard.newInstance(parent);
        }

        Log.e("CardViewFactory", "You have created a new card, but haven't mapped it correctly.");
        assert (false);
        return null;
    }
}
