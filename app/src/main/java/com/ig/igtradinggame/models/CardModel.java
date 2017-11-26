package com.ig.igtradinggame.models;

public abstract class CardModel {
    /**
     * This is required to view a model! Make sure keys are unique.
     * @return Mapping key (@see {@link com.ig.igtradinggame.ui.CardViewFactory})
     */
    public abstract int getType();
}
