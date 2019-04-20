package com.tranxit.payumoneypayment.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.payumoney.core.PayUmoneyConfig;
import com.payumoney.core.PayUmoneySdkInitializer;
import com.payumoney.core.entity.TransactionResponse;
import com.payumoney.sdkui.ui.utils.PayUmoneyFlowManager;
import com.payumoney.sdkui.ui.utils.ResultModel;
import com.tranxit.payumoneypayment.R;
import com.tranxit.payumoneypayment.model.CheckSumData;


public class PayuMoneyActivity extends AppCompatActivity {

    private static final String TAG = "PayuMoneyActivity";
    private String payu_txnid;
    private String payu_orderid;
    private int request_code;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        CheckSumData checksumresponse = (CheckSumData) getIntent().getSerializableExtra("payumoneyresponse");
        request_code = getIntent().getIntExtra("request_code", 0);

        if (checksumresponse != null)
            payuMoney(checksumresponse);
    }


    private void payuMoney(CheckSumData response) {

        PayUmoneyConfig payUmoneyConfig = PayUmoneyConfig.getInstance();

        //Use this to set your custom text on result screen button
        payUmoneyConfig.setDoneButtonText("Done");

        payu_txnid = response.getTxnid();
        payu_orderid = response.getProductinfo();

        //Use this to set your custom title for the activity
        payUmoneyConfig.setPayUmoneyActivityTitle("Tranxit Pay U Money");

        PayUmoneySdkInitializer.PaymentParam.Builder builder = new PayUmoneySdkInitializer.PaymentParam.Builder();

        builder.setAmount(String.valueOf(response.getAmount()))
                .setTxnId(response.getTxnid())
                .setPhone(response.getPhone())
                .setProductName(response.getProductinfo())
                .setFirstName(response.getFirstname())
                .setEmail(response.getEmail())
                .setsUrl(response.getSurl())
                .setfUrl(response.getCurl())
                .setUdf1("")
                .setUdf2("")
                .setUdf3("")
                .setUdf4("")
                .setUdf5("")
                .setUdf6("")
                .setUdf7("")
                .setUdf8("")
                .setUdf9("")
                .setUdf10("")
                .setIsDebug(false)
                .setKey(response.getKey())
                .setMerchantId(response.getMerchantId());

        try {
            PayUmoneySdkInitializer.PaymentParam mPaymentParams = builder.build();

            mPaymentParams.setMerchantHash(response.getHash());

            PayUmoneyFlowManager.startPayUMoneyFlow(mPaymentParams, PayuMoneyActivity.this, R.style.AppTheme_default, true);

        } catch (Exception e) {
            // some exception occurred
            e.printStackTrace();
            Log.d(TAG, "payuMoney: " + e.getMessage());
            Toast.makeText(PayuMoneyActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result Code is -1 send from Payumoney activity
        Log.d(TAG, "request code " + requestCode + " resultcode " + resultCode);
        Intent intent = new Intent();
        intent.putExtra("payu_txnid", payu_txnid);
        intent.putExtra("payu_orderid", payu_orderid);

        if (requestCode == PayUmoneyFlowManager.REQUEST_CODE_PAYMENT && data != null) {
            TransactionResponse transactionResponse = data.getParcelableExtra(PayUmoneyFlowManager
                    .INTENT_EXTRA_TRANSACTION_RESPONSE);

            ResultModel resultModel = data.getParcelableExtra(PayUmoneyFlowManager.ARG_RESULT);


            // Check which object is non-null
            if (transactionResponse != null && transactionResponse.getPayuResponse() != null) {
                if (transactionResponse.getTransactionStatus().equals(TransactionResponse.TransactionStatus.SUCCESSFUL)) {
                    Log.d(TAG, "onActivityResult: " + TransactionResponse.TransactionStatus.SUCCESSFUL);
                    intent.putExtra("status", "completed");
                    setResult(request_code, intent);
                    finish();
                } else {
                    Log.d(TAG, "onActivityResult: " + transactionResponse.getPayuResponse());
                    intent.putExtra("status", "failed");
                    setResult(request_code, intent);
                    finish();
                }

                // Response from SURl and FURL
                String merchantResponse = transactionResponse.getTransactionDetails();
                System.out.println("RRR merchantResponse = " + merchantResponse);


            } else {
                if (resultModel != null && resultModel.getError() != null)
                    Log.d("", "Error response : " + resultModel.getError().getTransactionResponse());

                intent.putExtra("status", "failed");
                setResult(request_code, intent);
                finish();
            }
        } else {
            intent.putExtra("status", "failed");
            setResult(100, intent);
            finish();
            Toast.makeText(this, "PayUmoney data null", Toast.LENGTH_SHORT).show();
        }
    }
}
