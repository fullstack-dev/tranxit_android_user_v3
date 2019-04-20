package com.tranxitpro.user.ui.activity.forgot_password;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tranxitpro.user.R;
import com.tranxitpro.user.base.BaseActivity;
import com.tranxitpro.user.ui.activity.login.EmailActivity;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForgotPasswordActivity extends BaseActivity implements ForgotPasswordIView {

    @BindView(R.id.otp)
    EditText otp;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.password_confirmation)
    EditText passwordConfirmation;
    @BindView(R.id.submit)
    Button submit;
    String OTP;
    int Id;

    private ForgotPasswordPresenter<ForgotPasswordActivity> presenter = new ForgotPasswordPresenter<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_forgot_password;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        presenter.attachView(this);
        // Activity title will be updated after the locale has changed in Runtime
        setTitle(getString(R.string.reset_password));
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Id = extras.getInt("id");
            OTP = extras.getString("otp");
        }
    }

    @OnClick(R.id.submit)
    public void onViewClicked() {

        if (otp.getText().toString().isEmpty()) {
            Toast.makeText(this, getString(R.string.invalid_otp), Toast.LENGTH_SHORT).show();
            return;
        }

        if (!otp.getText().toString().equals(OTP)) {
            Toast.makeText(this, getString(R.string.wrong_otp), Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.getText().toString().isEmpty()) {
            Toast.makeText(this, getString(R.string.invalid_password), Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.getText().toString().length() < 6) {
            Toast.makeText(this, getString(R.string.invalid_password_length), Toast.LENGTH_SHORT).show();
            return;
        }
        if (!password.getText().toString().equals(passwordConfirmation.getText().toString())) {
            Toast.makeText(this, getString(R.string.password_should_be_same), Toast.LENGTH_SHORT).show();
            return;
        }

        HashMap<String, Object> map = new HashMap<>();
        map.put("password", password.getText().toString());
        map.put("password_confirmation", passwordConfirmation.getText().toString());
        map.put("id", Id);
        showLoading();
        presenter.resetPassword(map);

    }

    @Override
    public void onSuccess(Object object) {
        try {
            hideLoading();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        Toast.makeText(this, getString(R.string.password_changed_successfully), Toast.LENGTH_SHORT).show();
        finishAffinity();
        startActivity(new Intent(this, EmailActivity.class));
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


