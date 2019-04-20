package com.appoets.paytmpayment;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public interface PaytmConstants {

    String ENVIRONMENT_STAGING = "staging";
    String ENVIRONMENT_LIVE = "live";

    String MESSAGE_SUCCESS = "Transaction is successful";
    String MESSAGE_FAILED = "Transaction cancelled";
    String MESSAGE_NETWORK_NOT_AVAILABLE = "Network not available";
    String MESSAGE_BACK_PRESSED = "Back pressed transaction cancelled";

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({ENVIRONMENT_STAGING,ENVIRONMENT_LIVE})
    @interface ENVIRONMENT{}


}
