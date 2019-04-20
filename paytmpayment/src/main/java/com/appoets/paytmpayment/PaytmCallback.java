package com.appoets.paytmpayment;

public interface PaytmCallback {
        void onPaytmSuccess(String status, String message,String paymentmode,String txid);
        void onPaytmFailure(String errorMessage);
}
