package com.tranxitpro.user.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProviderService {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("provider_id")
    @Expose
    private int providerId;
    @SerializedName("service_type_id")
    @Expose
    private int serviceTypeId;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("service_number")
    @Expose
    private String serviceNumber;
    @SerializedName("service_model")
    @Expose
    private String serviceModel;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProviderId() {
        return providerId;
    }

    public void setProviderId(int providerId) {
        this.providerId = providerId;
    }

    public int getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(int serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getServiceNumber() {
        return serviceNumber;
    }

    public void setServiceNumber(String serviceNumber) {
        this.serviceNumber = serviceNumber;
    }

    public String getServiceModel() {
        return serviceModel;
    }

    public void setServiceModel(String serviceModel) {
        this.serviceModel = serviceModel;
    }

}
