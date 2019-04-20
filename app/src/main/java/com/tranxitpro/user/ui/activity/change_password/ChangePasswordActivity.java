package com.tranxitpro.user.ui.activity.change_password;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tranxitpro.user.R;
import com.tranxitpro.user.base.BaseActivity;
import com.tranxitpro.user.ui.activity.OnBoardActivity;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChangePasswordActivity extends BaseActivity implements ChangePasswordIView {

    @BindView(R.id.old_password)
    EditText oldPassword;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.password_confirmation)
    EditText passwordConfirmation;
    @BindView(R.id.change_password)
    Button changePassword;
    private ChangePasswordPresenter<ChangePasswordActivity> presenter = new ChangePasswordPresenter<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_change_password;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        presenter.attachView(this);
        // Activity title will be updated after the locale has changed in Runtime
        setTitle(getString(R.string.change_password));
    }


    @Override
    public void onSuccess(Object object) {
        try {
            hideLoading();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        Intent intent = new Intent(this, OnBoardActivity.class);
        startActivity(intent);
        Toast.makeText(this, getString(R.string.password_changed_successfully), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(Throwable e) {
        handleError(e);
    }

    @OnClick(R.id.change_password)
    public void onViewClicked() {
        if (oldPassword.getText().toString().isEmpty()) {
            Toast.makeText(this, getString(R.string.invalid_current_password), Toast.LENGTH_SHORT).show();
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
        if (passwordConfirmation.getText().toString().isEmpty()) {
            Toast.makeText(this, getString(R.string.invalid_confirm_password), Toast.LENGTH_SHORT).show();
            return;
        }
        if (!password.getText().toString().equals(passwordConfirmation.getText().toString())) {
            Toast.makeText(this, getString(R.string.password_should_be_same), Toast.LENGTH_SHORT).show();
            return;
        }


        HashMap<String, Object> map = new HashMap<>();
        map.put("old_password", oldPassword.getText().toString());
        map.put("password", password.getText().toString());
        map.put("password_confirmation", passwordConfirmation.getText().toString());
        showLoading();
        presenter.changePassword(map);
    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }
}
