package com.tranxitpro.user.data.network.model;


/**
 * Created by Tranxit Technologies Pvt Ltd., Chennai.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CheckSumData {@SerializedName("key")
@Expose
private String key;
    @SerializedName("txnid")
    @Expose
    private String txnid;
    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("productinfo")
    @Expose
    private String productinfo;
    @SerializedName("firstname")
    @Expose
    private String firstname;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("surl")
    @Expose
    private String surl;
    @SerializedName("curl")
    @Expose
    private String curl;
    @SerializedName("service_provider")
    @Expose
    private String serviceProvider;
    @SerializedName("merchant_id")
    @Expose
    private String merchantId;
    @SerializedName("payu_salt")
    @Expose
    private String payuSalt;
    @SerializedName("hash_string")
    @Expose
    private String hashString;
    @SerializedName("hash")
    @Expose
    private String hash;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTxnid() {
        return txnid;
    }

    public void setTxnid(String txnid) {
        this.txnid = txnid;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getProductinfo() {
        return productinfo;
    }

    public void setProductinfo(String productinfo) {
        this.productinfo = productinfo;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSurl() {
        return surl;
    }

    public void setSurl(String surl) {
        this.surl = surl;
    }

    public String getCurl() {
        return curl;
    }

    public void setCurl(String curl) {
        this.curl = curl;
    }

    public String getServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(String serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getPayuSalt() {
        return payuSalt;
    }

    public void setPayuSalt(String payuSalt) {
        this.payuSalt = payuSalt;
    }

    public String getHashString() {
        return hashString;
    }

    public void setHashString(String hashString) {
        this.hashString = hashString;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
