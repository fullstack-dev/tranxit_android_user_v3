package com.tranxitpro.user.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DisputeResponse {

    @SerializedName("dispute_name")
    @Expose
    private String dispute_name;

    public String getDispute_name() {
        return dispute_name;
    }

    public void setDispute_name(String dispute_name) {
        this.dispute_name = dispute_name;
    }
}
