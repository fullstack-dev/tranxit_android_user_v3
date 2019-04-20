package com.tranxitpro.user.ui.activity.upcoming_trip_detail;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.tranxitpro.user.R;
import com.tranxitpro.user.base.BaseActivity;
import com.tranxitpro.user.common.CancelRequestInterface;
import com.tranxitpro.user.data.network.model.Datum;
import com.tranxitpro.user.data.network.model.Provider;
import com.tranxitpro.user.ui.fragment.cancel_ride.CancelRideDialogFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.tranxitpro.user.MvpApplication.DATUM;

public class UpcomingTripDetailActivity extends BaseActivity implements
        CancelRequestInterface, UpcomingTripDetailsIView {

    @BindView(R.id.static_map)
    ImageView staticMap;
    @BindView(R.id.booking_id)
    TextView bookingId;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.rating)
    RatingBar rating;
    @BindView(R.id.avatar)
    CircleImageView avatar;
    @BindView(R.id.schedule_at)
    TextView scheduleAt;
    @BindView(R.id.s_address)
    TextView sAddress;
    @BindView(R.id.d_address)
    TextView dAddress;
    @BindView(R.id.payment_mode)
    TextView paymentMode;
    @BindView(R.id.cancel)
    Button cancel;
    @BindView(R.id.call)
    Button call;
    @BindView(R.id.payment_image)
    ImageView paymentImage;
    String providerPhoneNumber = null;
    private CancelRequestInterface callback;
    private UpcomingTripDetailsPresenter<UpcomingTripDetailActivity> presenter = new UpcomingTripDetailsPresenter();

    @Override
    public int getLayoutId() {
        return R.layout.activity_upcoming_trip_detail;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        presenter.attachView(this);
        setTitle(getString(R.string.upcoming_trip_details));

        callback = this;
        if (DATUM != null) {
            Datum datum = DATUM;
            showLoading();
            presenter.getUpcomingTripDetails(datum.getId());
        }
    }

    @OnClick({R.id.cancel, R.id.call})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cancel:
                CancelRideDialogFragment cancelRideDialogFragment = new CancelRideDialogFragment(callback);
                cancelRideDialogFragment.show(getSupportFragmentManager(), cancelRideDialogFragment.getTag());
                break;
            case R.id.call:
                callProvider();
                break;
        }
    }

    private void callProvider() {
        if (providerPhoneNumber != null && !providerPhoneNumber.isEmpty()) {
            if (ActivityCompat.checkSelfPermission(baseActivity(),
                    Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED)
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + providerPhoneNumber)));
            else
                ActivityCompat.requestPermissions(baseActivity(),
                        new String[]{Manifest.permission.CALL_PHONE}, PERMISSIONS_REQUEST_PHONE);
        }
    }

    @Override
    public void cancelRequestMethod() {
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_PHONE)
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Toast.makeText(baseActivity(), "Permission Granted. Try Again!", Toast.LENGTH_SHORT).show();
            }
    }

    @Override
    public void onSuccess(List<Datum> upcomingTripDetails) {
        try {
            hideLoading();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        Datum datum = upcomingTripDetails.get(0);
        if (datum != null) {
            bookingId.setText(datum.getBookingId());
            scheduleAt.setText(datum.getScheduleAt());
            Glide.with(this)
                    .load(datum.getStaticMap())
                    .apply(RequestOptions
                            .placeholderOf(R.drawable.ic_launcher_background)
                            .dontAnimate()
                            .error(R.drawable.ic_launcher_background))
                    .into(staticMap);
            initPayment(datum.getPaymentMode(), paymentMode, paymentImage);
            sAddress.setText(datum.getSAddress());
            dAddress.setText(datum.getDAddress());

            Provider provider = datum.getProvider();
            if (provider != null) {
                providerPhoneNumber = provider.getMobile();
                Glide.with(this)
                        .load(provider.getAvatar())
                        .apply(RequestOptions
                                .placeholderOf(R.drawable.ic_user_placeholder)
                                .dontAnimate()
                                .error(R.drawable.ic_user_placeholder))
                        .into(avatar);
                name.setText(String.format("%s %s", provider.getFirstName(), provider.getLastName()));
                rating.setRating(Float.parseFloat(provider.getRating()));
            }
        }
    }

    @Override
    public void onError(Throwable e) {
        handleError(e);
    }
}
