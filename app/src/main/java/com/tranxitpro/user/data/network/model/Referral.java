package com.tranxitpro.user.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Referral {

    @SerializedName("referral")
    @Expose
    private String referral;
    @SerializedName("count")
    @Expose
    private String count;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("ride_otp")
    @Expose
    private String rideOtp;

    public String getReferral() {
        return referral;
    }

    public void setReferral(String referral) {
        this.referral = referral;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getRideOtp() {
        return rideOtp;
    }

    public void setRideOtp(String rideOtp) {
        this.rideOtp = rideOtp;
    }
}
