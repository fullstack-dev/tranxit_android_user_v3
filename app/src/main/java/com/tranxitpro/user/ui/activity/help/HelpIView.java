package com.tranxitpro.user.ui.activity.help;

import com.tranxitpro.user.base.MvpView;
import com.tranxitpro.user.data.network.model.Help;

public interface HelpIView extends MvpView {

    void onSuccess(Help help);

    void onError(Throwable e);
}
