package com.tranxitpro.user.ui.activity.invite_friend;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tranxitpro.user.MvpApplication;
import com.tranxitpro.user.R;
import com.tranxitpro.user.base.BaseActivity;
import com.tranxitpro.user.data.SharedHelper;
import com.tranxitpro.user.data.network.model.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InviteFriendActivity extends BaseActivity implements InviteFriendIView {

    private static final String TAG = "InviteFriendActivity";
    @BindView(R.id.invite_friend)
    TextView invite_friend;
    @BindView(R.id.referral_code)
    TextView referral_code;
    @BindView(R.id.referral_amount)
    TextView referral_amount;
    @BindView(R.id.llReferral)
    LinearLayout llReferral;

    private InviteFriendPresenter<InviteFriendActivity> inviteFriendPresenter = new InviteFriendPresenter<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_invite_friend;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        inviteFriendPresenter.attachView(this);

        if (!SharedHelper.getKey(this, "referral_code").equalsIgnoreCase("")) {
            updateUI();
        } else {
            //To get updated referral details
            inviteFriendPresenter.profile();
        }
    }

    private void updateUI() {
        referral_code.setText(SharedHelper.getKey(this, "referral_code"));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            invite_friend.setText(Html.fromHtml(SharedHelper.getKey(this, "referral_text"), Html.FROM_HTML_MODE_COMPACT));
            referral_amount.setText(Html.fromHtml(SharedHelper.getKey(this, "referral_total_text"), Html.FROM_HTML_MODE_COMPACT));
        } else {
            invite_friend.setText(Html.fromHtml(SharedHelper.getKey(this, "referral_text")));
            referral_amount.setText(Html.fromHtml(SharedHelper.getKey(this, "referral_total_text")));
        }

        if(TextUtils.isEmpty(SharedHelper.getKey(this,"referral_count")) && Integer.parseInt(SharedHelper.getKey(this,"referral_count")) > 0){
            llReferral.setVisibility(View.VISIBLE);
        }else{
            llReferral.setVisibility(View.GONE);
        }
    }


    @Override
    public void onSuccess(User user) {
        SharedHelper.putKey(this, "referral_code", user.getReferral_unique_id());
        SharedHelper.putKey(this, "referral_count", user.getReferral_count());
        SharedHelper.putKey(this, "referral_text", user.getReferral_text());
        SharedHelper.putKey(this, "referral_total_text", user.getReferral_total_text());
        MvpApplication.showOTP = user.getRide_otp().equals("1");
        updateUI();
    }

    @Override
    public void onError(Throwable throwable) {
        handleError(throwable);
    }

    @SuppressLint("StringFormatInvalid")
    @OnClick({R.id.share})
    public void onClickAction(View view) {
        switch (view.getId()) {
            case R.id.share:
                try {
                    String appName = getString(R.string.app_name);
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("text/plain");
                    i.putExtra(Intent.EXTRA_SUBJECT, appName);
                    i.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_content_referral, appName, SharedHelper.getKey(this, "referral_code")));
                    startActivity(Intent.createChooser(i, "choose one"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
