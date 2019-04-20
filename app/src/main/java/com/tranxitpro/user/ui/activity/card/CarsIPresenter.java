package com.tranxitpro.user.ui.activity.card;

import com.tranxitpro.user.base.MvpPresenter;


public interface CarsIPresenter<V extends CardsIView> extends MvpPresenter<V> {
    void card();
}
