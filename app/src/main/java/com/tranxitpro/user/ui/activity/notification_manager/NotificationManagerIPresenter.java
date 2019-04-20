package com.tranxitpro.user.ui.activity.notification_manager;

import com.tranxitpro.user.base.MvpPresenter;

public interface NotificationManagerIPresenter<V extends NotificationManagerIView> extends MvpPresenter<V> {
    void getNotificationManager();
}
