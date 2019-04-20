package com.appoets.braintreepayment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;

import java.util.MissingResourceException;


public class BrainTreePaymentActivity extends AppCompatActivity {

    public static final String EXTRAS_TOKEN = "braintree_token";
    public static final String PAYMENT_NONCE = "payment_token";
    public static final String PAYMENT_ERROR = "payment_error";
    private static final int REQUEST_CODE = 1002;

    private static final String TAG = "BrainTreePaymentActivit";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null && bundle.containsKey(EXTRAS_TOKEN)){
            String braintree_token = bundle.getString(EXTRAS_TOKEN);
            if (braintree_token != null &&!braintree_token.isEmpty()){
                showDropInUI(braintree_token);
            }else{
                throw new NullPointerException("EXTRAS_TOKEN is empty or null");
            }
        }else{
            throw new MissingResourceException("Cannot read EXTRAS_TOKEN bundle name from application",TAG,EXTRAS_TOKEN);
        }



    }

    private void showDropInUI(String clientToken) {
        DropInRequest dropInRequest = new DropInRequest()
                .clientToken(clientToken);
        startActivityForResult(dropInRequest.getIntent(this), REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE){
            if (RESULT_OK == resultCode){
                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                String paymentNonce = result.getPaymentMethodNonce().getNonce();
                Intent intent = new Intent();
                intent.putExtra(PAYMENT_NONCE, paymentNonce);
                setResult(RESULT_OK, intent);
                finish();
            }else if(resultCode == Activity.RESULT_CANCELED){
                setResult(RESULT_CANCELED, new Intent());
                finish();
            }else {
                Exception error = (Exception)data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
                Intent intent = new Intent();
                intent.putExtra(PAYMENT_ERROR, error.getLocalizedMessage());
                setResult(RESULT_OK, intent);
                finish();
                Log.d(TAG, " error exception");
            }
        }
    }
}
