package com.appoets.paytmpayment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import java.util.HashMap;
import java.util.Objects;

public class PaytmPayment {

    private PaytmObject paytmObject;
    private String environment = PaytmConstants.ENVIRONMENT_STAGING;
    private Context context;
    private PaytmCallback paytmCallback;


    public PaytmPayment(@NonNull Context context, @NonNull PaytmObject paytmObject, @NonNull PaytmCallback paytmCallback) {
        this.context = Objects.requireNonNull(context, "Context is null");
        this.paytmObject = Objects.requireNonNull(paytmObject, "PaytmObject is null");
        this.paytmCallback = Objects.requireNonNull(paytmCallback, "PaytmCallback is null");
    }

    public PaytmPayment setEnvironment(@PaytmConstants.ENVIRONMENT String environment) {
        this.environment = environment;
        return this;
    }


    private PaytmPGService getService(){
        return environment.equals(PaytmConstants.ENVIRONMENT_STAGING) ? PaytmPGService.getStagingService() : PaytmPGService.getProductionService();
    }


    public void startPayment(){

        PaytmPGService paytmPGService = getService();
        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("MID", paytmObject.getMID());
        paramMap.put("ORDER_ID", paytmObject.getORDER_ID());
        paramMap.put("CUST_ID", paytmObject.getCUST_ID());
        paramMap.put("MOBILE_NO", paytmObject.getMOBILE_NO());
        paramMap.put("EMAIL", paytmObject.getEMAIL());
        paramMap.put("CHANNEL_ID", paytmObject.getCHANNEL_ID());
        paramMap.put("TXN_AMOUNT", paytmObject.getTXN_AMOUNT());
        paramMap.put("WEBSITE", paytmObject.getWEBSITE());
        paramMap.put("INDUSTRY_TYPE_ID", paytmObject.getINDUSTRY_TYPE_ID());
        paramMap.put("CALLBACK_URL", paytmObject.getCALLBACK_URL());
        paramMap.put("CHECKSUMHASH", paytmObject.getCHECKSUMHASH());

        PaytmOrder Order = new PaytmOrder(paramMap);
        paytmPGService.initialize(Order, null);

        paytmPGService.startPaymentTransaction(context, true, true, new PaytmPaymentTransactionCallback() {
            @Override
            public void onTransactionResponse(Bundle inResponse) {
                String status = inResponse.getString("STATUS");
                String message = Objects.requireNonNull(status).equals("TXN_SUCCESS") ? PaytmConstants.MESSAGE_SUCCESS : PaytmConstants.MESSAGE_FAILED;
                if (message.equals(PaytmConstants.MESSAGE_SUCCESS)) {
                    String paymentmode = inResponse.getString("PAYMENTMODE");
                    String txnid = inResponse.getString("TXNID");
                    paytmCallback.onPaytmSuccess(status, message,paymentmode,txnid);
                }
                else
                    paytmCallback.onPaytmFailure(message);
            }

            @Override
            public void networkNotAvailable() {
                paytmCallback.onPaytmFailure(PaytmConstants.MESSAGE_NETWORK_NOT_AVAILABLE);
            }

            @Override
            public void clientAuthenticationFailed(String inErrorMessage) {
                paytmCallback.onPaytmFailure(inErrorMessage);
            }

            @Override
            public void someUIErrorOccurred(String inErrorMessage) {
                paytmCallback.onPaytmFailure(inErrorMessage);
            }

            @Override
            public void onErrorLoadingWebPage(int iniErrorCode, String inErrorMessage, String inFailingUrl) {
                paytmCallback.onPaytmFailure(inErrorMessage);
            }

            @Override
            public void onBackPressedCancelTransaction() {
                paytmCallback.onPaytmFailure(PaytmConstants.MESSAGE_BACK_PRESSED);
            }

            @Override
            public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {
                paytmCallback.onPaytmFailure(inErrorMessage);
            }
        });
    }
}
