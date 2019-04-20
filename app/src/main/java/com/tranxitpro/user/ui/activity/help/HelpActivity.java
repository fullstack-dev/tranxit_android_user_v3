package com.tranxitpro.user.ui.activity.help;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.View;

import com.tranxitpro.user.BuildConfig;
import com.tranxitpro.user.R;
import com.tranxitpro.user.base.BaseActivity;
import com.tranxitpro.user.data.network.model.Help;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class HelpActivity extends BaseActivity implements HelpIView {

    private String ContactNumber = null;
    private String Mail = null;
    private HelpPresenter<HelpActivity> presenter = new HelpPresenter<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_help;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        presenter.attachView(this);
        setTitle(getString(R.string.help));
        presenter.help();
    }

    @Override
    public void onSuccess(Help help) {
        ContactNumber = help.getContactNumber();
        Mail = help.getContactEmail();
    }

    @Override
    public void onError(Throwable e) {
        handleError(e);
    }

    private void callContactNumber() {
        if (ContactNumber != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                    == PackageManager.PERMISSION_GRANTED)
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + ContactNumber)));
            else
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CALL_PHONE}, PERMISSIONS_REQUEST_PHONE);
        }
    }

    private void sendMail() {
        if (Mail != null) {
            String appName = getString(R.string.app_name) + " " + getString(R.string.help);
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
            emailIntent.setData(Uri.parse("mailto: " + Mail));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, appName);
            startActivity(Intent.createChooser(emailIntent, "Send feedback"));
        }
    }

    @OnClick({R.id.call, R.id.mail, R.id.web})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.call:
                callContactNumber();
                break;
            case R.id.mail:
                sendMail();
                break;
            case R.id.web:
                startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(BuildConfig.BASE_URL)));
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_PHONE)
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                callContactNumber();
    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }
}
