package com.tranxitpro.user.ui.fragment.lost_item;

import com.tranxitpro.user.base.MvpPresenter;

import java.util.HashMap;

/**
 * Created by santhosh@appoets.com on 19-05-2018.
 */
public interface LostIPresenter<V extends LostIView> extends MvpPresenter<V> {
    void postLostItem(HashMap<String, Object> obj);
}
