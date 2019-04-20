package com.tranxitpro.user.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rating {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("request_id")
    @Expose
    private int requestId;
    @SerializedName("user_id")
    @Expose
    private int userId;
    @SerializedName("provider_id")
    @Expose
    private int providerId;
    @SerializedName("user_rating")
    @Expose
    private int userRating;
    @SerializedName("provider_rating")
    @Expose
    private Float providerRating;
    @SerializedName("user_comment")
    @Expose
    private String userComment;
    @SerializedName("provider_comment")
    @Expose
    private String providerComment;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProviderId() {
        return providerId;
    }

    public void setProviderId(int providerId) {
        this.providerId = providerId;
    }

    public int getUserRating() {
        return userRating;
    }

    public void setUserRating(int userRating) {
        this.userRating = userRating;
    }

    public Float getProviderRating() {
        return providerRating;
    }

    public void setProviderRating(Float providerRating) {
        this.providerRating = providerRating;
    }

    public String getUserComment() {
        return userComment;
    }

    public void setUserComment(String userComment) {
        this.userComment = userComment;
    }

    public String getProviderComment() {
        return providerComment;
    }

    public void setProviderComment(String providerComment) {
        this.providerComment = providerComment;
    }
}
