package com.tranxitpro.user.ui.activity.invite_friend;

import com.tranxitpro.user.base.MvpView;
import com.tranxitpro.user.data.network.model.User;

public interface InviteFriendIView extends MvpView {

    void onSuccess(User user);

    void onError(Throwable e);

}
