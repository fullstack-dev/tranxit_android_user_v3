package com.tranxitpro.user.ui.activity.social;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.PhoneNumber;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.tranxitpro.user.BuildConfig;
import com.tranxitpro.user.R;
import com.tranxitpro.user.base.BaseActivity;
import com.tranxitpro.user.data.SharedHelper;
import com.tranxitpro.user.data.network.model.Token;
import com.tranxitpro.user.ui.activity.main.MainActivity;
import com.tranxitpro.user.ui.activity.wallet.WalletActivity;
import com.tranxitpro.user.ui.countrypicker.Country;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class SocialLoginActivity extends BaseActivity implements SocialIView {

    private GoogleSignInClient mGoogleSignInClient;
    private SocialPresenter<SocialLoginActivity> presenter = new SocialPresenter<>();
    private CallbackManager callbackManager;
    private HashMap<String, Object> map = new HashMap<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_social_login;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        presenter.attachView(this);
        callbackManager = CallbackManager.Factory.create();

        map.put("device_token", SharedHelper.getKey(this, "device_token"));
        map.put("device_id", SharedHelper.getKey(this, "device_id"));
        map.put("device_type", BuildConfig.DEVICE_TYPE);

        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.google_signin_server_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    @OnClick({R.id.facebook, R.id.google})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.facebook:
                fbLogin();
                break;
            case R.id.google:
                showLoading();
                startActivityForResult(mGoogleSignInClient.getSignInIntent(), REQUEST_GOOGLE_LOGIN);
                break;
        }
    }

    void fbLogin() {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email"));
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            public void onSuccess(LoginResult loginResult) {
                if (AccessToken.getCurrentAccessToken() != null) {
                    map.put("login_by", "facebook");
                    map.put("accessToken", loginResult.getAccessToken().getToken());
                    Country mCountry = getDeviceCountry(SocialLoginActivity.this);
                    fbOtpVerify(mCountry.getCode(), mCountry.getDialCode(), "");
                }
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException exception) {
                exception.printStackTrace();
                String s = exception.getMessage();
                if (exception instanceof FacebookAuthorizationException) {
                    if (AccessToken.getCurrentAccessToken() != null)
                        LoginManager.getInstance().logOut();
                } else if (s.contains("GraphQLHttpFailureDomain"))
                    Toasty.info(SocialLoginActivity.this, getString(R.string.fb_session_expired), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_GOOGLE_LOGIN) {
            try {
                hideLoading();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            String TAG = "REQUEST_GOOGLE_LOGIN";
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                //String token = account.getIdToken();
                map.put("login_by", "google");
                Runnable runnable = () -> {
                    try {
                        String scope = "oauth2:" + Scopes.EMAIL + " " + Scopes.PROFILE;
                        String accessToken = GoogleAuthUtil.getToken(getApplicationContext(), Objects.requireNonNull(Objects.requireNonNull(account).getAccount()), scope, new Bundle());
                        Log.d(TAG, "accessToken:" + accessToken);
                        map.put("accessToken", accessToken);
                        Country mCountry = getDeviceCountry(SocialLoginActivity.this);
                        fbOtpVerify(mCountry.getCode(), mCountry.getDialCode(), "");
                    } catch (IOException | GoogleAuthException e) {
                        e.printStackTrace();
                    }
                };
                AsyncTask.execute(runnable);

            } catch (ApiException e) {
                Log.w(TAG, "signInResult:failed code = " + e.getStatusCode());
            }
        } else if (requestCode == REQUEST_ACCOUNT_KIT && data != null) {
            AccountKitLoginResult loginResult = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);
            if (!loginResult.wasCancelled())
                AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
                    @Override
                    public void onSuccess(Account account) {
                        Log.d("REQUEST_ACCOUNT_KIT", "onSuccess: Account Kit" + Objects.requireNonNull(AccountKit.getCurrentAccessToken()).getToken());
                        if (Objects.requireNonNull(AccountKit.getCurrentAccessToken()).getToken() != null) {
                            PhoneNumber phoneNumber = account.getPhoneNumber();
                            SharedHelper.putKey(SocialLoginActivity.this, "country_code", "+" + phoneNumber.getCountryCode());
                            SharedHelper.putKey(SocialLoginActivity.this, "mobile", phoneNumber.getPhoneNumber());
                            register();
                        }
                    }

                    @Override
                    public void onError(AccountKitError accountKitError) {
                        Log.e("REQUEST_ACCOUNT_KIT", "onError: Account Kit" + accountKitError);
                    }
                });
        }
    }

    private void register() {
        map.put("mobile", SharedHelper.getKey(this, "mobile"));
        map.put("country_code", SharedHelper.getKey(this, "country_code"));
        if (Objects.equals(map.get("login_by"), "google")) presenter.loginGoogle(map);
        else if (Objects.equals(map.get("login_by"), "facebook")) presenter.loginFacebook(map);

        showLoading();
    }

    @Override
    public void onSuccess(Token token) {
        try {
            hideLoading();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        String accessToken = token.getTokenType() + " " + token.getAccessToken();
        SharedHelper.putKey(this, "access_token", accessToken);
        SharedHelper.putKey(this, "logged_in", true);
        finishAffinity();
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void onError(Throwable e) {
        handleError(e);
    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }
}
