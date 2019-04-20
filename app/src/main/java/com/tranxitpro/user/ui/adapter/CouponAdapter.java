package com.tranxitpro.user.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tranxitpro.user.R;
import com.tranxitpro.user.data.network.model.PromoList;
import com.tranxitpro.user.ui.fragment.book_ride.BookRideFragment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CouponAdapter extends RecyclerView.Adapter<CouponAdapter.MyViewHolder> {

    private Context context;
    private List<PromoList> mCouponList;
    private BookRideFragment.CouponListener mListener;
    private BottomSheetDialog sheetDialog;
    private int couponPosition;
    private String mPromoStatus;

    public CouponAdapter(Context mContext,
                         List<PromoList> couponList,
                         BookRideFragment.CouponListener mCouponListener,
                         BottomSheetDialog couponDialog, int lastSelectCoupon,
                         String promoStatus) {
        this.context = mContext;
        this.mCouponList = couponList;
        this.mListener = mCouponListener;
        this.sheetDialog = couponDialog;
        this.couponPosition = lastSelectCoupon;
        this.mPromoStatus = promoStatus;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_promo_code, parent, false);
        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        PromoList promoList = mCouponList.get(position);
        if (promoList != null) {
            holder.promId.setText(promoList.getPromoCode());
            if (couponPosition != promoList.getId())
                holder.promoStatus.setText(context.getString(R.string.use));
            else {
                if (mPromoStatus.equalsIgnoreCase(context.getString(R.string.view_coupon)))
                    holder.promoStatus.setText(context.getString(R.string.use));
                else holder.promoStatus.setText(context.getString(R.string.remove));
            }
            if (promoList.getPromoDescription() != null && !
                    promoList.getPromoDescription().isEmpty())
                holder.promoDescription.setText(promoList.getPromoDescription());
            @SuppressLint("SimpleDateFormat")
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-DD HH:mm:ss");
            Date date;
            try {
                date = formatter.parse(promoList.getExpiration());
                @SuppressLint("SimpleDateFormat")
                SimpleDateFormat newFormat = new SimpleDateFormat("dd/MM/yyyy");
                String finalString = newFormat.format(date);
                holder.promoValidDate.setText(context.getString(R.string.valid_till) + " " + finalString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            holder.promoStatus.setOnClickListener(view -> {
                if (sheetDialog != null) sheetDialog.dismiss();
                mListener.couponClicked(holder.getAdapterPosition(), promoList,
                        holder.promoStatus.getText().toString());
            });
        }
    }

    @Override
    public int getItemCount() {
        return mCouponList.size();
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
