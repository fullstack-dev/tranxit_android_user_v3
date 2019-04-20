package com.tranxitpro.user.ui.fragment.dispute;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.internal.LinkedTreeMap;
import com.tranxitpro.user.R;
import com.tranxitpro.user.base.BaseBottomSheetDialogFragment;
import com.tranxitpro.user.data.SharedHelper;
import com.tranxitpro.user.data.network.model.Datum;
import com.tranxitpro.user.data.network.model.DisputeResponse;
import com.tranxitpro.user.data.network.model.Help;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.tranxitpro.user.MvpApplication.DATUM;

public class DisputeFragment extends BaseBottomSheetDialogFragment implements DisputeIView {

    private static final int PERMISSIONS_REQUEST_PHONE = 321;
    @BindView(R.id.cancel_reason)
    EditText cancelReason;
    @BindView(R.id.rcvReason)
    RecyclerView rcvReason;
    private DisputePresenter<DisputeFragment> presenter = new DisputePresenter<>();
    private List<DisputeResponse> disputeResponseList = new ArrayList<>();
    private DisputeFragment.DisputeAdapter adapter;
    private int lastSelectedLocation = -1;
    private DisputeCallBack mCallBack;
    private String contactNumber;

    public DisputeFragment() {
    }

    public void setCallBack(DisputeCallBack mCallBack) {
        this.mCallBack = mCallBack;
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_fragment_dispute;
    }

    @Override
    public void initView(View view) {
        ButterKnife.bind(this, view);
        presenter.attachView(this);

        adapter = new DisputeFragment.DisputeAdapter(disputeResponseList);
        rcvReason.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));
        rcvReason.setItemAnimator(new DefaultItemAnimator());
        rcvReason.setAdapter(adapter);

        showLoading();
        presenter.getDispute();
        presenter.help();
    }

    @OnClick({R.id.dismiss, R.id.submit, R.id.ivSupportCall})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.dismiss:
                dismiss();
                break;
            case R.id.submit:
                if (lastSelectedLocation == -1) {
                    Toast.makeText(getContext(), getString(R.string.invalid_selection), Toast.LENGTH_SHORT).show();
                    return;
                }
                createDispute();
                break;

            case R.id.ivSupportCall:
                callContactNumber(contactNumber);
        }
    }

    private void callContactNumber(String ContactNumber) {
        if (ContactNumber != null) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE)
                    == PackageManager.PERMISSION_GRANTED)
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + ContactNumber)));
            else
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.CALL_PHONE}, PERMISSIONS_REQUEST_PHONE);
        }
    }

    private void createDispute() {
        if (DATUM != null) {
            Datum datum = DATUM;
            HashMap<String, Object> map = new HashMap<>();
            map.put("request_id", datum.getId());
            map.put("dispute_type", "user");
            map.put("user_id", SharedHelper.getKey(Objects.requireNonNull(getContext()), "user_id"));
            map.put("provider_id", datum.getProviderId());
            map.put("comments", cancelReason.getText().toString());
            map.put("dispute_name", disputeResponseList.get(lastSelectedLocation).getDispute_name());
            showLoading();
            presenter.dispute(map);
        }
    }

    @Override
    public void onSuccessDispute(List<DisputeResponse> responseList) {
        disputeResponseList.addAll(responseList);
        setDefaultSelection();
        hideLoading();
    }

    @Override
    public void onSuccess(Object object) {
        try {
            hideLoading();
            if (object instanceof LinkedTreeMap) {
                LinkedTreeMap responseMap = (LinkedTreeMap) object;
                if (responseMap.get("message") != null) {
                    mCallBack.onDisputeCreated();
                    Toast.makeText(getActivity().getApplicationContext(),
                            responseMap.get("message").toString(), Toast.LENGTH_SHORT)
                            .show();
                }
            } else {
                Toast.makeText(getActivity().getApplicationContext(),
                        getResources().getString(R.string.lost_item_error), Toast.LENGTH_SHORT)
                        .show();
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        dismiss();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_PHONE)
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                callContactNumber(contactNumber);
    }

    private void setDefaultSelection() {
        rcvReason.setVisibility(View.VISIBLE);
        lastSelectedLocation = -1;
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onError(Throwable e) {
        handleError(e);
    }

    @Override
    public void onSuccess(Help help) {
        hideLoading();
        contactNumber = help.getContactNumber();
    }

    private class DisputeAdapter extends RecyclerView.Adapter<DisputeFragment.DisputeAdapter.MyViewHolder> {

        private List<DisputeResponse> list;

        private DisputeAdapter(List<DisputeResponse> list) {
            this.list = list;
        }

        @NonNull
        @Override
        public DisputeFragment.DisputeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new DisputeFragment.DisputeAdapter.MyViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.cancel_reasons_inflate, parent, false));
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull DisputeFragment.DisputeAdapter.MyViewHolder holder, int position) {
            DisputeResponse data = list.get(position);
            holder.tvReason.setText(data.getDispute_name());
            if (lastSelectedLocation == position) holder.cbItem.setChecked(true);
            else holder.cbItem.setChecked(false);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            LinearLayout llItemView;
            TextView tvReason;
            CheckBox cbItem;

            MyViewHolder(View view) {
                super(view);
                llItemView = view.findViewById(R.id.llItemView);
                tvReason = view.findViewById(R.id.tvReason);
                cbItem = view.findViewById(R.id.cbItem);

                llItemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                lastSelectedLocation = getAdapterPosition();
                notifyDataSetChanged();
            }
        }
    }

}
