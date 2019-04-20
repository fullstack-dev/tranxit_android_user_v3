package com.tranxitpro.user.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("payment_mode")
    @Expose
    private String paymentMode;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("country_code")
    @Expose
    private String countryCode;
    @SerializedName("picture")
    @Expose
    private String picture;
    @SerializedName("device_token")
    @Expose
    private String deviceToken;
    @SerializedName("device_id")
    @Expose
    private String deviceId;
    @SerializedName("device_type")
    @Expose
    private String deviceType;
    @SerializedName("login_by")
    @Expose
    private String loginBy;
    @SerializedName("social_unique_id")
    @Expose
    private String socialUniqueId;
    @SerializedName("latitude")
    @Expose
    private double latitude;
    @SerializedName("longitude")
    @Expose
    private double longitude;
    @SerializedName("stripe_cust_id")
    @Expose
    private String stripeCustId;
    @SerializedName("wallet_balance")
    @Expose
    private double walletBalance;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("otp")
    @Expose
    private double otp;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("sos")
    @Expose
    private String sos;
    @SerializedName("app_contact")
    @Expose
    private String appContact;
    @SerializedName("measurement")
    @Expose
    private String measurement;
    @SerializedName("stripe_secret_key")
    @Expose
    private String stripeSecretKey;
    @SerializedName("stripe_publishable_key")
    @Expose
    private String stripePublishableKey;
    @SerializedName("referral_count")
    @Expose
    private String referral_count;
    @SerializedName("referral_unique_id")
    @Expose
    private String referral_unique_id;

    @SerializedName("referral_text")
    @Expose
    private String referral_text;

    @SerializedName("referral_total_amount")
    @Expose
    private String referral_total_amount;

    @SerializedName("referral_total_count")
    @Expose
    private String referral_total_count;

    @SerializedName("referral_total_text")
    @Expose
    private String referral_total_text;

    @SerializedName("referral_amount")
    @Expose
    private String referral_amount;

    @SerializedName("ride_otp")
    @Expose
    private String ride_otp;

    @SerializedName("qrcode_url")
    @Expose
    private String qrcode_url;

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getQrcode_url() {
        return qrcode_url;
    }

    public void setQrcode_url(String qrcode_url) {
        this.qrcode_url = qrcode_url;
    }

    public String getReferral_text() {
        return referral_text;
    }

    public void setReferral_text(String referral_text) {
        this.referral_text = referral_text;
    }

    public String getReferral_total_amount() {
        return referral_total_amount;
    }

    public void setReferral_total_amount(String referral_total_amount) {
        this.referral_total_amount = referral_total_amount;
    }

    public String getReferral_total_count() {
        return referral_total_count;
    }

    public void setReferral_total_count(String referral_total_count) {
        this.referral_total_count = referral_total_count;
    }

    public String getReferral_total_text() {
        return referral_total_text;
    }

    public void setReferral_total_text(String referral_total_text) {
        this.referral_total_text = referral_total_text;
    }

    public String getReferral_amount() {
        return referral_amount;
    }

    public void setReferral_amount(String referral_amount) {
        this.referral_amount = referral_amount;
    }

    public String getReferral_count() {
        return referral_count;
    }

    public void setReferral_count(String referral_count) {
        this.referral_count = referral_count;
    }

    public String getReferral_unique_id() {
        return referral_unique_id;
    }

    public void setReferral_unique_id(String referral_unique_id) {
        this.referral_unique_id = referral_unique_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getLoginBy() {
        return loginBy;
    }

    public void setLoginBy(String loginBy) {
        this.loginBy = loginBy;
    }

    public String getSocialUniqueId() {
        return socialUniqueId;
    }

    public void setSocialUniqueId(String socialUniqueId) {
        this.socialUniqueId = socialUniqueId;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getStripeCustId() {
        return stripeCustId;
    }

    public void setStripeCustId(String stripeCustId) {
        this.stripeCustId = stripeCustId;
    }

    public double getWalletBalance() {
        return walletBalance;
    }

    public void setWalletBalance(double walletBalance) {
        this.walletBalance = walletBalance;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public double getOtp() {
        return otp;
    }

    public void setOtp(double otp) {
        this.otp = otp;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getSos() {
        return sos;
    }

    public void setSos(String sos) {
        this.sos = sos;
    }

    public String getAppContact() {
        return appContact;
    }

    public void setAppContact(String appContact) {
        this.appContact = appContact;
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    public String getStripeSecretKey() {
        return stripeSecretKey;
    }

    public void setStripeSecretKey(String stripeSecretKey) {
        this.stripeSecretKey = stripeSecretKey;
    }

    public String getStripePublishableKey() {
        return stripePublishableKey;
    }

    public void setStripePublishableKey(String stripePublishableKey) {
        this.stripePublishableKey = stripePublishableKey;
    }

    public String getRide_otp() {
        return ride_otp;
    }

    public void setRide_otp(String ride_otp) {
        this.ride_otp = ride_otp;
    }
}
