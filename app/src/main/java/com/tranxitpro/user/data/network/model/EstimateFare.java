package com.tranxitpro.user.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class EstimateFare implements Serializable {
    @SerializedName("estimated_fare")
    @Expose
    private double estimatedFare;
    @SerializedName("distance")
    @Expose
    private double distance;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("surge")
    @Expose
    private int surge;
    @SerializedName("surge_value")
    @Expose
    private String surgeValue;
    @SerializedName("tax_price")
    @Expose
    private double taxPrice;
    @SerializedName("base_price")
    @Expose
    private double basePrice;
    @SerializedName("wallet_balance")
    @Expose
    private double walletBalance;
    @SerializedName("service")
    @Expose
    private Service service;

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public double getEstimatedFare() {
        return estimatedFare;
    }

    public void setEstimatedFare(double estimatedFare) {
        this.estimatedFare = estimatedFare;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getSurge() {
        return surge;
    }

    public void setSurge(int surge) {
        this.surge = surge;
    }

    public String getSurgeValue() {
        return surgeValue;
    }

    public void setSurgeValue(String surgeValue) {
        this.surgeValue = surgeValue;
    }

    public double getTaxPrice() {
        return taxPrice;
    }

    public void setTaxPrice(double taxPrice) {
        this.taxPrice = taxPrice;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public double getWalletBalance() {
        return walletBalance;
    }

    public void setWalletBalance(double walletBalance) {
        this.walletBalance = walletBalance;
    }
}
