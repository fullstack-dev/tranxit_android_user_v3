package com.tranxitpro.user.data.network.model;

import com.tranxitpro.user.BuildConfig;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Provider {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
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
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("fleet")
    @Expose
    private int fleet;
    @SerializedName("latitude")
    @Expose
    private double latitude;
    @SerializedName("longitude")
    @Expose
    private double longitude;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("city_latitude")
    @Expose
    private double cityLatitude;
    @SerializedName("city_longitude")
    @Expose
    private double cityLongitude;
    @SerializedName("referral_id")
    @Expose
    private Object referralId;
    @SerializedName("referral_code")
    @Expose
    private String referralCode;
    @SerializedName("usage_count")
    @Expose
    private Object usageCount;
    @SerializedName("referral_earning")
    @Expose
    private Object referralEarning;
    @SerializedName("usage_limit")
    @Expose
    private Object usageLimit;
    @SerializedName("expired_at")
    @Expose
    private String expiredAt;
    @SerializedName("otp")
    @Expose
    private int otp;
    @SerializedName("trip_type")
    @Expose
    private String tripType;
    @SerializedName("outstation_type")
    @Expose
    private String outstationType;
    @SerializedName("wallet_balance")
    @Expose
    private Object walletBalance;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("login_by")
    @Expose
    private String loginBy;
    @SerializedName("social_unique_id")
    @Expose
    private Object socialUniqueId;
    @SerializedName("service")
    @Expose
    private ProviderService providerService;

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

    public String getAvatar() {
        return BuildConfig.BASE_URL + "storage/" + avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getFleet() {
        return fleet;
    }

    public void setFleet(int fleet) {
        this.fleet = fleet;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public double getCityLatitude() {
        return cityLatitude;
    }

    public void setCityLatitude(double cityLatitude) {
        this.cityLatitude = cityLatitude;
    }

    public double getCityLongitude() {
        return cityLongitude;
    }

    public void setCityLongitude(double cityLongitude) {
        this.cityLongitude = cityLongitude;
    }

    public Object getReferralId() {
        return referralId;
    }

    public void setReferralId(Object referralId) {
        this.referralId = referralId;
    }

    public String getReferralCode() {
        return referralCode;
    }

    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }

    public Object getUsageCount() {
        return usageCount;
    }

    public void setUsageCount(Object usageCount) {
        this.usageCount = usageCount;
    }

    public Object getReferralEarning() {
        return referralEarning;
    }

    public void setReferralEarning(Object referralEarning) {
        this.referralEarning = referralEarning;
    }

    public Object getUsageLimit() {
        return usageLimit;
    }

    public void setUsageLimit(Object usageLimit) {
        this.usageLimit = usageLimit;
    }

    public String getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(String expiredAt) {
        this.expiredAt = expiredAt;
    }

    public int getOtp() {
        return otp;
    }

    public void setOtp(int otp) {
        this.otp = otp;
    }

    public String getTripType() {
        return tripType;
    }

    public void setTripType(String tripType) {
        this.tripType = tripType;
    }

    public String getOutstationType() {
        return outstationType;
    }

    public void setOutstationType(String outstationType) {
        this.outstationType = outstationType;
    }

    public Object getWalletBalance() {
        return walletBalance;
    }

    public void setWalletBalance(Object walletBalance) {
        this.walletBalance = walletBalance;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getLoginBy() {
        return loginBy;
    }

    public void setLoginBy(String loginBy) {
        this.loginBy = loginBy;
    }

    public Object getSocialUniqueId() {
        return socialUniqueId;
    }

    public void setSocialUniqueId(Object socialUniqueId) {
        this.socialUniqueId = socialUniqueId;
    }

    public ProviderService getProviderService() {
        return providerService;
    }

    public void setProviderService(ProviderService providerService) {
        this.providerService = providerService;
    }

}
