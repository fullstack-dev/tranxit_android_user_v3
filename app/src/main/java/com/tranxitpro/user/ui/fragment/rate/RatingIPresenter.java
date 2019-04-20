package com.tranxitpro.user.ui.fragment.rate;

import com.tranxitpro.user.base.MvpPresenter;

import java.util.HashMap;

public interface RatingIPresenter<V extends RatingIView> extends MvpPresenter<V> {

    void rate(HashMap<String, Object> obj);
}
