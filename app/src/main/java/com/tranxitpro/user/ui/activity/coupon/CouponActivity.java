package com.tranxitpro.user.ui.activity.coupon;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tranxitpro.user.MvpApplication;
import com.tranxitpro.user.R;
import com.tranxitpro.user.base.BaseActivity;
import com.tranxitpro.user.data.network.model.PromoList;
import com.tranxitpro.user.data.network.model.PromoResponse;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CouponActivity extends BaseActivity implements CouponIView {

    @BindView(R.id.rvCoupon)
    RecyclerView rvCoupon;
    @BindView(R.id.tvNoData)
    TextView tvNoData;

    private CouponPresenter<CouponActivity> presenter = new CouponPresenter<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_coupon;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        presenter.attachView(this);
        // Activity title will be updated after the locale has changed in Runtime
        setTitle(getString(R.string.offer));

        showLoading();
        rvCoupon.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvCoupon.setItemAnimator(new DefaultItemAnimator());

        presenter.coupon();
    }

    @Override
    public void onSuccess(PromoResponse response) {
        try {
            hideLoading();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        if (!response.getPromoList().isEmpty()) {
            tvNoData.setVisibility(View.GONE);
            rvCoupon.setAdapter(new CouponAdapter(response.getPromoList()));
            rvCoupon.setVisibility(View.VISIBLE);
        } else {
            rvCoupon.setVisibility(View.GONE);
            tvNoData.setVisibility(View.VISIBLE);
        }
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

    private class CouponAdapter extends RecyclerView.Adapter<CouponAdapter.MyViewHolder> {

        private List<PromoList> list;
        private Context mContext;

        private CouponAdapter(@NotNull List<PromoList> list) {
            this.list = list;
        }

        @NotNull
        @Override
        public CouponAdapter.MyViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
            mContext = parent.getContext();
            return new CouponAdapter.MyViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_promo_code, parent, false));
        }

        @Override
        public void onBindViewHolder(@NotNull CouponAdapter.MyViewHolder holder, int position) {
            PromoList promoList = list.get(position);
            if (promoList != null) {
                holder.promId.setText(promoList.getPromoCode());
                holder.promoStatus.setVisibility(View.GONE);
                holder.promoDescription.setText(promoList.getPromoDescription());
                try {
                    Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(promoList.getExpiration());
                    String finalString = new SimpleDateFormat("dd/MM/yyyy").format(date);
                    holder.promoValidDate.setText(String.format("%s %s",
                            mContext.getString(R.string.valid_till), finalString));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            private TextView promId;
            private TextView promoStatus;
            private TextView promoDescription;
            private TextView promoValidDate;

            MyViewHolder(View view) {
                super(view);

                promId = view.findViewById(R.id.promoCode_id);
                promoStatus = view.findViewById(R.id.promoCode_status);
                promoDescription = view.findViewById(R.id.promoCode_description);
                promoValidDate = view.findViewById(R.id.promoCode_date);
            }
        }
    }
}
