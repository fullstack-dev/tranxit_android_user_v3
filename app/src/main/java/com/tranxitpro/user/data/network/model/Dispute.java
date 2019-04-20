package com.tranxitpro.user.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tranxitpro.user.BuildConfig;

/**
 * Created by santhosh@appoets.com on 07-06-2018.
 */
public class Dispute {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("request_id")
    @Expose
    private Integer requestId;
    @SerializedName("dispute_type")
    @Expose
    private String disputeType;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("provider_id")
    @Expose
    private Object providerId;
    @SerializedName("dispute_name")
    @Expose
    private String disputeName;
    @SerializedName("comments")
    @Expose
    private String comments;
    @SerializedName("refund_amount")
    @Expose
    private Integer refundAmount;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("is_admin")
    @Expose
    private Integer isAdmin;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

    public String getDisputeType() {
        return disputeType;
    }

    public void setDisputeType(String disputeType) {
        this.disputeType = disputeType;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Object getProviderId() {
        return providerId;
    }

    public void setProviderId(Object providerId) {
        this.providerId = providerId;
    }

    public String getDisputeName() {
        return disputeName;
    }

    public void setDisputeName(String disputeName) {
        this.disputeName = disputeName;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Integer getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(Integer refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Integer isAdmin) {
        this.isAdmin = isAdmin;
    }
}
