package com.tranxitpro.user.base;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.accountkit.PhoneNumber;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.tranxitpro.user.BuildConfig;
import com.tranxitpro.user.MvpApplication;
import com.tranxitpro.user.R;
import com.tranxitpro.user.common.ConnectivityReceiver;
import com.tranxitpro.user.common.Constants;
import com.tranxitpro.user.common.CustomDialog;
import com.tranxitpro.user.common.LocaleHelper;
import com.tranxitpro.user.data.SharedHelper;
import com.tranxitpro.user.ui.activity.OnBoardActivity;
import com.tranxitpro.user.ui.activity.login.EmailActivity;
import com.tranxitpro.user.ui.activity.login.PasswordActivity;
import com.tranxitpro.user.ui.activity.register.RegisterActivity;
import com.tranxitpro.user.ui.activity.social.SocialLoginActivity;
import com.tranxitpro.user.ui.activity.splash.SplashActivity;
import com.tranxitpro.user.ui.countrypicker.Country;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.NoRouteToHostException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Currency;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import es.dmoral.toasty.Toasty;
import okhttp3.ResponseBody;
import pl.aprilapps.easyphotopicker.EasyImage;
import retrofit2.HttpException;
import retrofit2.Response;

public abstract class BaseActivity extends AppCompatActivity
        implements MvpView, ConnectivityReceiver.ConnectivityReceiverListener {

    protected final int REQUEST_ACCESS_FINE_LOCATION = 1;
    protected final int REQUEST_PICK_LOCATION = 3;
    protected final int PERMISSIONS_REQUEST_PHONE = 4;
    protected final int REQUEST_CHECK_SETTINGS = 5;
    protected final int REQUEST_ACCOUNT_KIT = 99;
    protected final int REQUEST_GOOGLE_LOGIN = 98;
    protected final float DEFAULT_ZOOM = 15;
    protected LatLng mDefaultLocation = new LatLng(-33.8523341, 151.2106085);


    private Activity activity;
    private boolean isNetwork = false;
    private Dialog offlineDialog;
    private BasePresenter<BaseActivity> presenter = new BasePresenter<>();
    private CustomDialog customDialog;

    private Map<String, Object> jsonToMap(JSONObject json) throws JSONException {
        Map<String, Object> retMap = new HashMap<>();
        if (json != JSONObject.NULL) retMap = toMap(json);
        return retMap;
    }

    private Map<String, Object> toMap(JSONObject object) throws JSONException {
        Map<String, Object> map = new HashMap<>();
        Iterator<String> keysItr = object.keys();
        while (keysItr.hasNext()) {
            String key = keysItr.next();
            Object value = object.get(key);
            if (value instanceof JSONArray) value = toList((JSONArray) value);
            else if (value instanceof JSONObject) value = toMap((JSONObject) value);
            map.put(key, value);
        }
        return map;
    }

    private List<Object> toList(JSONArray array) throws JSONException {
        List<Object> list = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            Object value = array.get(i);
            if (value instanceof JSONArray) value = toList((JSONArray) value);
            else if (value instanceof JSONObject) value = toMap((JSONObject) value);
            list.add(value);
        }
        return list;
    }

    public static NumberFormat getNumberFormat() {
        String currencyCode = SharedHelper.getKey(MvpApplication.getInstance(), "currency_code", "USD");
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.getDefault());
        numberFormat.setCurrency(Currency.getInstance(currencyCode));
        numberFormat.setMinimumFractionDigits(2);
        return numberFormat;
    }


    @Override
    public Activity baseActivity() {
        return this;
    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        customDialog = new CustomDialog(this);

        presenter.attachView(this);

        initView();
        activity = this;
        checkConnection();
    }

    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        if (activity instanceof SplashActivity || activity instanceof EmailActivity ||
                activity instanceof PasswordActivity || activity instanceof OnBoardActivity ||
                activity instanceof RegisterActivity || activity instanceof SocialLoginActivity) {
            if (!isNetwork) showOfflineDialog(isConnected, 1);
            else {
                isNetwork = false;
                if (!isConnected) {
                    try {
                        hideLoading();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    Toast.makeText(activity, getString(R.string.current_alternative), Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        hideLoading();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    if (offlineDialog != null && offlineDialog.isShowing()) offlineDialog.dismiss();

                }
            }
        } else showOfflineDialog(isConnected, 0);

    }

    private void showOfflineDialog(boolean isConnected, int position) {
        if (!isConnected) if (activity != null) if (position == 0) try {
            final Dialog offlineDialog = new Dialog(this);
            offlineDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            offlineDialog.setCancelable(false);
            offlineDialog.setCanceledOnTouchOutside(false);
            offlineDialog.setContentView(R.layout.layout_offline);
            Window window = offlineDialog.getWindow();
            offlineDialog.show();
            ImageView iv_retry = offlineDialog.findViewById(R.id.iv_retry);
            Button btnSendLocation = offlineDialog.findViewById(R.id.btn_send_location);
            TextView no_thanks = offlineDialog.findViewById(R.id.no_thanks);
            no_thanks.setOnClickListener(view -> {
                offlineDialog.dismiss();
                finishAffinity();
            });
            btnSendLocation.setOnClickListener(view -> {
                if (btnSendLocation.getVisibility() == View.VISIBLE) {
                    offlineDialog.dismiss();
                    try {
                        Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                        smsIntent.setType("vnd.android-dir/mms-sms");
                        smsIntent.putExtra("address", SharedHelper.getKey(getApplicationContext(), "appContact"));
                        smsIntent.putExtra("sms_body", "I need a cab @" +
                                SharedHelper.getKey(baseActivity(), "latitude") + "," +
                                SharedHelper.getKey(baseActivity(), "longitude") + "( Please don't edit this SMS. Standard SMS charges of Rs.3 per SMS may apply )");
                        startActivity(smsIntent);
                    } catch (ActivityNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });
            assert window != null;
            WindowManager.LayoutParams param = window.getAttributes();
            param.gravity = Gravity.CENTER | Gravity.CENTER_HORIZONTAL;
            param.windowAnimations = R.style.DialogAnimation;
            window.setAttributes(param);
            Objects.requireNonNull(offlineDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        else try {
                offlineDialog = new Dialog(this);
                offlineDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                offlineDialog.setCancelable(false);
                offlineDialog.setCanceledOnTouchOutside(false);
                offlineDialog.setContentView(R.layout.layout_offline_alternative);
                Window window = offlineDialog.getWindow();
                offlineDialog.show();
                ImageView iv_retry = offlineDialog.findViewById(R.id.iv_retry);
                TextView no_thanks = offlineDialog.findViewById(R.id.no_thanks);
                no_thanks.setOnClickListener(view -> {
                    offlineDialog.dismiss();
                    finishAffinity();
                });
                iv_retry.setOnClickListener(v -> {
                    isNetwork = true;
                    showLoading();
                    checkConnection();
                });
                assert window != null;
                WindowManager.LayoutParams param = window.getAttributes();
                param.gravity = Gravity.CENTER | Gravity.CENTER_HORIZONTAL;
                param.windowAnimations = R.style.DialogAnimation;
                window.setAttributes(param);
                Objects.requireNonNull(offlineDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }

    @TargetApi(Build.VERSION_CODES.M)
    protected void requestPermissionsSafely(String[] permissions, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            requestPermissions(permissions, requestCode);
    }

    @TargetApi(Build.VERSION_CODES.M)
    protected boolean hasPermission(String permission) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void showLoading() {
        if (customDialog != null)
            customDialog.show();
    }

    @Override
    public void hideLoading() {
        if (customDialog != null)
            customDialog.cancel();
    }

    protected String getAddress(LatLng currentLocation) {
        String address = null;
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(currentLocation.latitude, currentLocation.longitude, 1);
            if ((addresses != null) && !addresses.isEmpty()) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder();
                if (returnedAddress.getMaxAddressLineIndex() > 0)
                    for (int j = 0; j < returnedAddress.getMaxAddressLineIndex(); j++)
                        strReturnedAddress.append(returnedAddress.getAddressLine(j)).append("");
                else strReturnedAddress.append(returnedAddress.getAddressLine(0)).append("");
                address = strReturnedAddress.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return address;
    }

    protected void pickImage() {
        EasyImage.openChooserWithGallery(this, "", 0);
    }

    protected void fbOtpVerify(String strCountryCode, String strCountryISOCode, String strPhoneNumber) {
        final Intent intent = new Intent(this, AccountKitActivity.class);
        AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder =
                new AccountKitConfiguration.AccountKitConfigurationBuilder(
                        LoginType.PHONE,
                        AccountKitActivity.ResponseType.TOKEN);
        configurationBuilder.setReadPhoneStateEnabled(true);
        configurationBuilder.setReceiveSMS(true);
        PhoneNumber phoneNumber = new PhoneNumber(strCountryISOCode, strPhoneNumber, strCountryCode);
        configurationBuilder.setInitialPhoneNumber(phoneNumber);
        intent.putExtra(
                AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,
                configurationBuilder.build());
        startActivityForResult(intent, REQUEST_ACCOUNT_KIT);
    }

    @SuppressLint("StringFormatInvalid")
    protected void shareApp() {
        try {
            String appName = getString(R.string.app_name);
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, appName);
            i.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_content, appName, BuildConfig.APPLICATION_ID));
            startActivity(Intent.createChooser(i, "choose one"));
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
        }
    }

    protected float bearingBetweenLocations(LatLng latLng1, LatLng latLng2) {
        double PI = 3.14159;
        double lat1 = latLng1.latitude * PI / 180;
        double long1 = latLng1.longitude * PI / 180;
        double lat2 = latLng2.latitude * PI / 180;
        double long2 = latLng2.longitude * PI / 180;

        double dLon = (long2 - long1);
        double y = Math.sin(dLon) * Math.cos(lat2);
        double x = Math.cos(lat1) * Math.sin(lat2) - Math.sin(lat1)
                * Math.cos(lat2) * Math.cos(dLon);

        double brng = Math.atan2(y, x);

        brng = Math.toDegrees(brng);
        brng = (brng + 360) % 360;
        return (float) brng;
    }

    public void handleError(Throwable e) {
        try {
            hideLoading();
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        if (e != null) try {
            if (e instanceof ConnectException || e instanceof UnknownHostException ||
                    e instanceof SocketTimeoutException || e instanceof NoRouteToHostException) {
                //     Toasty.error(this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
            } else if (e instanceof HttpException) {
                ResponseBody responseBody = ((HttpException) e).response().errorBody();
                int responseCode = ((HttpException) e).response().code();
                try {
                    JSONObject jsonObject = new JSONObject(responseBody.string());
                    if (responseCode == 400 || responseCode == 405)
                        Toasty.error(this, getErrorMessage(jsonObject, getString(R.string.something_went_wrong)), Toast.LENGTH_SHORT).show();
                    else if (responseCode == 404) {
                        if (PasswordActivity.TAG.equals("PasswordActivity")) {
                            Collection<Object> values = jsonToMap(jsonObject).values();
                            printIfContainsValue(values.toString()
                                    .replaceAll("[\\[\\],]", ""), "Password");
                        } else
                            Toasty.error(this, getErrorMessage(jsonObject, getString(R.string.something_went_wrong)), Toast.LENGTH_SHORT).show();
                    } else if (responseCode == 401) {
                        Toasty.error(this, getErrorMessage(jsonObject, getString(R.string.unauthenticated)), Toast.LENGTH_SHORT).show();
                        LogoutApp();
                    } else if (responseCode == 422)
                        Toasty.error(this, getErrorMessage(jsonObject, getString(R.string.invalid_input)), Toast.LENGTH_SHORT).show();
                    else if (responseCode == 503)
                        Toasty.error(this, getString(R.string.server_down), Toast.LENGTH_SHORT).show();
                    else if (responseCode == 500)
                        Toasty.error(this, getString(R.string.server_down), Toast.LENGTH_SHORT).show();
                    else if (responseCode == 429)
                        System.out.println("RRR Too many requests... check status API");
//                            Toasty.error(this, getString(R.string.internal_server_error), Toast.LENGTH_SHORT).show();
                    else
                        Toasty.error(this, getErrorMessage(jsonObject, getString(R.string.something_went_wrong)), Toast.LENGTH_SHORT).show();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    private String getErrorMessage(JSONObject jsonObject, String error) {
        try {
            if (jsonObject.has("message")) error = jsonObject.getString("message");
            else if (jsonObject.has("error")) error = jsonObject.getString("error");
            else if (jsonObject.has("email")) error = jsonObject.optString("email");
            else return error;
        } catch (Exception e) {
            e.printStackTrace();
            return getString(R.string.something_went_wrong);
        }
        return error;
    }

    @Override
    public void onSuccessLogout(Object object) {
        try {
            hideLoading();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        LogoutApp();
    }

    @Override
    public void onError(Throwable throwable) {
        try {
            hideLoading();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        throwable.printStackTrace();
    }

    private void printIfContainsValue(String stringValue, String value) {
        if (value.equals("Password")) {
            String[] parts = stringValue.split("\\.");
            String part1 = parts[0]; // 004
            Toasty.error(this, part1, Toast.LENGTH_LONG).show();
        } else Toasty.error(this, stringValue, Toast.LENGTH_LONG).show();
    }

    protected String printJSON(Object o) {
        return new Gson().toJson(o);
    }

    @Override
    public void onResume() {
        super.onResume();
        MvpApplication.getInstance().setConnectivityListener(this);
    }

    protected void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void showKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        }
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        checkConnection();
    }

    protected void initPayment(String mode, TextView paymentMode, ImageView paymentImage) {

        switch (mode) {
            case Constants.PaymentMode.CASH:
                paymentMode.setText(getString(R.string.cash));
                paymentImage.setImageResource(R.drawable.ic_money);
                break;
            case Constants.PaymentMode.CARD:
                paymentMode.setText(getString(R.string.card));
                paymentImage.setImageResource(R.drawable.ic_card);
                break;
            case Constants.PaymentMode.PAYPAL:
                paymentMode.setText(getString(R.string.paypal));
                break;
            case Constants.PaymentMode.WALLET:
                paymentMode.setText(getString(R.string.wallet));
                break;
            default:
                break;
        }
    }

    public void onErrorBase(Throwable e) {
        try {
            hideLoading();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        if (e instanceof HttpException) {
            Response response = ((HttpException) e).response();
            try {
                JSONObject jObjError = new JSONObject(response.errorBody().string());
                if (jObjError.has("message"))
                    Toast.makeText(baseActivity(), jObjError.optString("message"), Toast.LENGTH_SHORT).show();
                else if (jObjError.has("error"))
                    Toast.makeText(baseActivity(), jObjError.optString("error"), Toast.LENGTH_SHORT).show();
                else if (jObjError.has("email"))
                    Toast.makeText(baseActivity(), jObjError.optString("email"), Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(baseActivity(), R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
            } catch (Exception exp) {
                Log.e("Error", exp.getMessage());
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDetach();
    }

    protected void LogoutApp() {
//        Toasty.success(this, getString(R.string.logout_successfully), Toast.LENGTH_SHORT).show();
        SharedHelper.clearSharedPreferences(this);
        MvpApplication.RIDE_REQUEST.clear();
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
        startActivity(new Intent(this, OnBoardActivity.class));
        finishAffinity();
    }

    protected Country getDeviceCountry(Context context) {
        return Country.getCountryFromSIM(context) != null
                ? Country.getCountryFromSIM(context)
                : new Country("US", "United States", "+1", R.drawable.flag_in);
    }

    public Bitmap getBitmapFromURL(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String encodeBase64(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
    }

    protected Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }

}