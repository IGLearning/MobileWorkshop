package com.ig.igtradinggame.features.maingame.about.button;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ig.igtradinggame.R;
import com.ig.igtradinggame.ui.BaseCardView;
import com.ig.igtradinggame.models.CardModel;

import butterknife.BindView;

public class ButtonCard extends BaseCardView {

    @BindView(R.id.button_cardview_simple)
    Button button;

    public ButtonCard(View itemView) {
        super(itemView);
    }

    public static ButtonCard newInstance(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_button, parent, false);
        return new ButtonCard(view);
    }

    @Override
    public void setup(CardModel cardModel) {
        super.setup(cardModel);

        if (cardModel.getType() == ButtonModel.TYPE) {
            final ButtonModel buttonModel = (ButtonModel) cardModel;

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    buttonModel.getButtonClickListener().onButtonClick();
                }
            });
        }
    }
}


