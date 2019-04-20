package com.tranxitpro.user.ui.fragment.upcoming_trip;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.tranxitpro.user.MvpApplication;
import com.tranxitpro.user.R;
import com.tranxitpro.user.base.BaseActivity;
import com.tranxitpro.user.base.BaseFragment;
import com.tranxitpro.user.common.CancelRequestInterface;
import com.tranxitpro.user.data.network.model.Datum;
import com.tranxitpro.user.data.network.model.Provider;
import com.tranxitpro.user.ui.activity.upcoming_trip_detail.UpcomingTripDetailActivity;
import com.tranxitpro.user.ui.fragment.cancel_ride.CancelRideDialogFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

public class UpcomingTripFragment extends BaseFragment implements UpcomingTripIView, CancelRequestInterface {

    @BindView(R.id.upcoming_trip_rv)
    RecyclerView upcomingTripRv;
    @BindView(R.id.error_layout)
    LinearLayout errorLayout;
    @BindView(R.id.progress_bar)
    LottieAnimationView lottieAnimationView;
    @BindView(R.id.tv_error)
    TextView error;
    Unbinder unbinder;
    List<Datum> list = new ArrayList<>();
    TripAdapter adapter;
    private UpcomingTripPresenter<UpcomingTripFragment> presenter = new UpcomingTripPresenter<>();
    private CancelRequestInterface callback;

    public UpcomingTripFragment() {
        // Required empty public constructor
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_upcoming_trip;
    }
/*
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }*/

    @Override
    public View initView(View view) {
        unbinder = ButterKnife.bind(this, view);
        presenter.attachView(this);
        error.setText(getString(R.string.no_upcoming_found));
        callback = this;
        adapter = new TripAdapter(list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        upcomingTripRv.setLayoutManager(mLayoutManager);
        upcomingTripRv.setItemAnimator(new DefaultItemAnimator());
        upcomingTripRv.setAdapter(adapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        lottieAnimationView.setVisibility(View.VISIBLE);
        presenter.upcomingTrip();
    }

    private void alertCancel(Integer requestId) {
        new AlertDialog.Builder(getContext())
                .setTitle(getString(R.string.cancel_request))
                .setMessage(R.string.are_sure_you_want_to_cancel_the_request)
                .setCancelable(true)
                .setPositiveButton(getString(R.string.yes), (dialog, which) -> {
                    showLoading();
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("request_id", requestId);
                    presenter.cancelRequest(map);
                }).setNegativeButton(getString(R.string.no), (dialog, which) -> dialog.cancel())
                .show();
    }

    @Override
    public void onSuccess(List<Datum> datumList) {
        lottieAnimationView.setVisibility(View.GONE);

        list.clear();
        list.addAll(datumList);
        adapter.notifyDataSetChanged();

        if (list.isEmpty()) {
            errorLayout.setVisibility(View.VISIBLE);
        } else {
            errorLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onSuccess(Object object) {
        try {
            hideLoading();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        presenter.upcomingTrip();
    }

    @Override
    public void onError(Throwable e) {
        lottieAnimationView.setVisibility(View.GONE);
        handleError(e);
    }

    @Override
    public void cancelRequestMethod() {
        presenter.upcomingTrip();
    }

    @Override
    public void onDestroyView() {
        presenter.onDetach();
        super.onDestroyView();
    }

    private class TripAdapter extends RecyclerView.Adapter<TripAdapter.MyViewHolder> {

        private List<Datum> list;

        private TripAdapter(List<Datum> list) {
            this.list = list;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_upcoming_trip, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            Datum datum = list.get(position);
            holder.scheduleAt.setText(datum.getScheduleAt());
            holder.bookingId.setText(datum.getBookingId());
            Glide.with(baseActivity()).load(datum.getStaticMap()).apply(RequestOptions.
                    placeholderOf(R.drawable.ic_launcher_background).dontAnimate().
                    error(R.drawable.ic_launcher_background)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)).into(holder.staticMap);

            Provider provider = datum.getProvider();
            if (provider != null)
                Glide.with(baseActivity()).load(provider.getAvatar()).
                        apply(RequestOptions.placeholderOf(R.drawable.ic_user_placeholder).
                                dontAnimate().error(R.drawable.ic_user_placeholder).
                                diskCacheStrategy(DiskCacheStrategy.ALL)).into(holder.avatar);


        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            CircleImageView avatar;
            private CardView itemView;
            private TextView bookingId;
            private TextView scheduleAt;
            private ImageView staticMap;
            private Button cancel;

            MyViewHolder(View view) {
                super(view);
                itemView = view.findViewById(R.id.item_view);
                bookingId = view.findViewById(R.id.booking_id);
                scheduleAt = view.findViewById(R.id.schedule_at);
                staticMap = view.findViewById(R.id.static_map);
                cancel = view.findViewById(R.id.cancel);
                avatar = view.findViewById(R.id.avatar);
                itemView.setOnClickListener(this);
                cancel.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                int position = getAdapterPosition();
                MvpApplication.DATUM = list.get(position);
                if (view.getId() == R.id.item_view) {
                    ActivityOptionsCompat options = ActivityOptionsCompat
                            .makeSceneTransitionAnimation(baseActivity(), staticMap,
                                    ViewCompat.getTransitionName(staticMap));
                    Intent intent = new Intent(getActivity(), UpcomingTripDetailActivity.class);
                    startActivity(intent, options.toBundle());
                } else if (view.getId() == R.id.cancel) {
                    CancelRideDialogFragment cancelRideDialogFragment = new CancelRideDialogFragment(callback);
                    cancelRideDialogFragment.show(baseActivity().getSupportFragmentManager(), cancelRideDialogFragment.getTag());
                    //alertCancel(BaseActivity.DATUM.getId());
                }
            }
        }
    }
}
