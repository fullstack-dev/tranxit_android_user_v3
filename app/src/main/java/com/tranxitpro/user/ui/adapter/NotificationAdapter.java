package com.tranxitpro.user.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.tranxitpro.user.R;
import com.tranxitpro.user.data.network.model.NotificationManager;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {

    private List<NotificationManager> notifications;
    private Context mContext;

    public NotificationAdapter(List<NotificationManager> notifications) {
        this.notifications = notifications;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        return new MyViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_notifications, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        TextView showMore = holder.tvShowMore;
        Glide.with(mContext)
                .load(notifications.get(position).getImage())
                .apply(RequestOptions.placeholderOf(R.drawable.ic_document_placeholder)
                        .dontAnimate().error(R.drawable.ic_document_placeholder))
                .into(holder.ivNotificationImg);
        holder.ivNotificationDesc.setText(notifications.get(position).getDescription());
        holder.ivNotificationDesc.post(() -> {
            if (holder.ivNotificationDesc.getLineCount() > 3) {
                holder.ivNotificationDesc.setMaxLines(3);
                holder.ivNotificationDesc.setEllipsize(TextUtils.TruncateAt.END);
            } else showMore.setVisibility(View.INVISIBLE);
        });

        showMore.setOnClickListener(v -> {
            if (showMore.getText().toString().equals(mContext.getString(R.string.show_more))) {
                showMore.setText(mContext.getString(R.string.show_less));
                holder.ivNotificationDesc.setMaxLines(Integer.MAX_VALUE);
            } else {
                showMore.setText(mContext.getString(R.string.show_more));
                holder.ivNotificationDesc.setMaxLines(3);
                holder.ivNotificationDesc.setEllipsize(TextUtils.TruncateAt.END);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivNotificationImg;
        private TextView ivNotificationDesc;
        private TextView tvShowMore;

        MyViewHolder(View view) {
            super(view);

            ivNotificationDesc = view.findViewById(R.id.ivNotificationDesc);
            ivNotificationImg = view.findViewById(R.id.ivNotificationImg);
            tvShowMore = view.findViewById(R.id.tvShowMore);
        }
    }
}
