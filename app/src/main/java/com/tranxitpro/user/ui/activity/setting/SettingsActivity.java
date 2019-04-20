package com.tranxitpro.user.ui.activity.setting;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.tranxitpro.user.R;
import com.tranxitpro.user.base.BaseActivity;
import com.tranxitpro.user.common.Constants;
import com.tranxitpro.user.common.LocaleHelper;
import com.tranxitpro.user.data.network.model.AddressResponse;
import com.tranxitpro.user.data.network.model.UserAddress;
import com.tranxitpro.user.ui.activity.location_pick.LocationPickActivity;
import com.tranxitpro.user.ui.activity.main.MainActivity;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.tranxitpro.user.common.Constants.RIDE_REQUEST.SRC_ADD;
import static com.tranxitpro.user.common.Constants.RIDE_REQUEST.SRC_LAT;
import static com.tranxitpro.user.common.Constants.RIDE_REQUEST.SRC_LONG;

public class SettingsActivity extends BaseActivity implements SettingsIView {

    @BindView(R.id.choose_language)
    RadioGroup chooseLanguage;
    @BindView(R.id.english)
    RadioButton english;
    @BindView(R.id.arabic)
    RadioButton arabic;
    @BindView(R.id.home_status)
    TextView homeStatus;
    @BindView(R.id.home_address)
    TextView homeAddress;
    @BindView(R.id.work_status)
    TextView workStatus;
    @BindView(R.id.work_address)
    TextView workAddress;


    private String type = "home";
    private String language;
    private SettingsPresenter<SettingsActivity> presenter = new SettingsPresenter<>();
    private UserAddress work = null, home = null;

    @Override
    public int getLayoutId() {
        return R.layout.activity_settings;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        presenter.attachView(this);

        // Activity title will be updated after the locale has changed in Runtime
        setTitle(getString(R.string.settings));
        showLoading();
        presenter.address();

        languageReset();

        chooseLanguage.setOnCheckedChangeListener((group, checkedId) -> {
            showLoading();
            switch (checkedId) {
                case R.id.english:
                    language = Constants.Language.ENGLISH;
                    break;
                case R.id.arabic:
                    language = Constants.Language.ARABIC;
                    break;
            }
            presenter.changeLanguage(language);

        });
    }

    private void languageReset() {
        String dd = LocaleHelper.getLanguage(getApplicationContext());
        switch (dd) {
            case Constants.Language.ENGLISH:
                english.setChecked(true);
                break;
            case Constants.Language.ARABIC:
                arabic.setChecked(true);
                break;
            default:
                english.setChecked(true);
                break;
        }
    }

    @Override
    public void onSuccessAddress(Object object) {
        presenter.address();
    }

    @Override
    public void onLanguageChanged(Object object) {
        try {
            hideLoading();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        LocaleHelper.setLocale(getApplicationContext(), language);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_NEW_TASK));
        this.overridePendingTransition(R.anim.rotate_in, R.anim.rotate_out);
    }

    @Override
    public void onSuccess(AddressResponse address) {
        if (address.getHome().isEmpty()) {
            homeAddress.setText("");
            homeStatus.setText(getString(R.string.add));
            homeStatus.setTag("add");
            home = null;
        } else {
            home = address.getHome().get(address.getHome().size() - 1);
            homeAddress.setText(home.getAddress());
            homeStatus.setText(getString(R.string.delete));
        }

        if (address.getWork().isEmpty()) {
            workAddress.setText("");
            workStatus.setText(getString(R.string.add));
            workStatus.setTag("add");
            work = null;
        } else {
            work = address.getWork().get(address.getWork().size() - 1);
            workAddress.setText(work.getAddress());
            workStatus.setText(getString(R.string.delete));
        }
        try {
            hideLoading();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void onError(Throwable e) {
        handleError(e);
    }

    @OnClick({R.id.home_status, R.id.work_status})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.home_status:
                if (home == null) {
                    type = "home";
                    Intent intent = new Intent(this, LocationPickActivity.class);
                    intent.putExtra("actionName", Constants.LocationActions.SELECT_HOME);
                    startActivityForResult(intent, REQUEST_PICK_LOCATION);
                } else {
                    new AlertDialog.Builder(this)
                            .setMessage(getString(R.string.are_sure_you_want_to_delete))
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(getString(R.string.delete), (dialog, whichButton) -> {
                                showLoading();
                                presenter.deleteAddress(home.getId(), new HashMap<>());
                            }).setNegativeButton(getString(R.string.no), null).show();
                }
                break;
            case R.id.work_status:
                if (work == null) {
                    type = "work";
                    Intent workIntent = new Intent(this, LocationPickActivity.class);
                    workIntent.putExtra("actionName", Constants.LocationActions.SELECT_WORK);
                    startActivityForResult(workIntent, REQUEST_PICK_LOCATION);
                } else {
                    new AlertDialog.Builder(this)
                            .setMessage(getString(R.string.are_sure_you_want_to_delete))
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(getString(R.string.delete), (dialog, whichButton) -> {
                                showLoading();
                                presenter.deleteAddress(work.getId(), new HashMap<>());
                            }).setNegativeButton(getString(R.string.no), null).show();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_PICK_LOCATION) if (resultCode == Activity.RESULT_OK) {
            if (data != null) {
                showLoading();
                HashMap<String, Object> map = new HashMap<>();
                map.put("type", type);
                map.put("address", data.getStringExtra(SRC_ADD));
                map.put("latitude", data.getDoubleExtra(SRC_LAT, 0.0));
                map.put("longitude", data.getDoubleExtra(SRC_LONG, 0.0));
                presenter.addAddress(map);
            }

        }
           /* if (RIDE_REQUEST.containsKey(SRC_ADD)) {
                showLoading();
                HashMap<String, Object> map = new HashMap<>();
                map.put("type", type);
                map.put("address", RIDE_REQUEST.get(SRC_ADD));
                map.put("latitude", RIDE_REQUEST.get(SRC_LAT));
                map.put("longitude", RIDE_REQUEST.get(SRC_LONG));
                presenter.addAddress(map);
            }*/
    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }
}