package com.tranxitpro.user.ui.activity.card;

import com.tranxitpro.user.base.MvpView;
import com.tranxitpro.user.data.network.model.Card;

import java.util.List;

/**
 * Created by santhosh@appoets.com on 19-05-2018.
 */
public interface CardsIView extends MvpView {
    void onSuccess(List<Card> cardList);

    void onError(Throwable e);
}
