package com.tranxitpro.user.ui.activity.notification_manager;

import com.tranxitpro.user.base.MvpView;
import com.tranxitpro.user.data.network.model.NotificationManager;

import java.util.List;

public interface NotificationManagerIView extends MvpView {

    void onSuccess(List<NotificationManager> notificationManager);

    void onError(Throwable e);

}