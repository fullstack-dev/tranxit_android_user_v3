package com.tranxitpro.user.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Coupon {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("user_id")
    @Expose
    private int userId;
    @SerializedName("promocode_id")
    @Expose
    private int promocodeId;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("promocode")
    @Expose
    private Promocode promocode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPromocodeId() {
        return promocodeId;
    }

    public void setPromocodeId(int promocodeId) {
        this.promocodeId = promocodeId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Promocode getPromocode() {
        return promocode;
    }

    public void setPromocode(Promocode promocode) {
        this.promocode = promocode;
    }

}

