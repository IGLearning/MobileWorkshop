package com.ig.igtradinggame.ui.cards.button;

import com.ig.igtradinggame.ui.cards.CardModel;

public class ButtonModel extends CardModel {
    public static final int TYPE = 3;

    public interface ButtonClickListener {
        void onButtonClick();
    }

    private ButtonClickListener buttonClickListener;

    public ButtonModel(ButtonClickListener buttonClickListener) {
        this.buttonClickListener = buttonClickListener;
    }

    @Override
    public int getType() {
        return TYPE;
    }

    public ButtonClickListener getButtonClickListener() {
        return buttonClickListener;
    }
}
