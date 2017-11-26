package com.ig.igtradinggame.ui;

import android.util.Log;
import android.view.ViewGroup;

import com.ig.igtradinggame.features.maingame.about.BalanceCard;
import com.ig.igtradinggame.features.maingame.about.button.ButtonCard;
import com.ig.igtradinggame.features.maingame.about.button.ButtonModel;
import com.ig.igtradinggame.features.maingame.about.userdetails.UserDetailsCard;
import com.ig.igtradinggame.features.maingame.trade.buy.MarketCard;
import com.ig.igtradinggame.features.maingame.trade.sell.OpenPositionCard;
import com.ig.igtradinggame.models.BalanceModel;
import com.ig.igtradinggame.models.MarketModel;
import com.ig.igtradinggame.models.OpenPositionModel;
import com.ig.igtradinggame.models.UserDetailsModel;

/**
 * Acts as a mapping from a model to a card. Don't forget to register new cards when created!
 */
public class CardViewFactory {
    public static BaseCardView generateFromType(final int type, ViewGroup parent) {
        switch (type) {
            case MarketModel.TYPE:
                return MarketCard.newInstance(parent);

            case OpenPositionModel.TYPE:
                return OpenPositionCard.newInstance(parent);

            case BalanceModel.TYPE:
                return BalanceCard.newInstance(parent);

            case ButtonModel.TYPE:
                return ButtonCard.newInstance(parent);

            case UserDetailsModel.TYPE:
                return UserDetailsCard.newInstance(parent);
        }

        Log.e("CardViewFactory", "You have created a new card, but haven't mapped it correctly.");
        assert (false);
        return null;
    }
}
