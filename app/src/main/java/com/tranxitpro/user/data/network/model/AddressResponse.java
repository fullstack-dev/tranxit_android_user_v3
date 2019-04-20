package com.tranxitpro.user.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by santhosh@appoets.com on 27-06-2018.
 */
public class AddressResponse {
    @SerializedName("home")
    @Expose
    private List<UserAddress> home = null;
    @SerializedName("work")
    @Expose
    private List<UserAddress> work = null;
    @SerializedName("others")
    @Expose
    private List<UserAddress> others = null;
    @SerializedName("recent")
    @Expose
    private List<UserAddress> recent = null;

    public List<UserAddress> getHome() {
        return home;
    }

    public void setHome(List<UserAddress> home) {
        this.home = home;
    }

    public List<UserAddress> getWork() {
        return work;
    }

    public void setWork(List<UserAddress> work) {
        this.work = work;
    }

    public List<UserAddress> getOthers() {
        return others;
    }

    public void setOthers(List<UserAddress> others) {
        this.others = others;
    }

    public List<UserAddress> getRecent() {
        return recent;
    }

    public void setRecent(List<UserAddress> recent) {
        this.recent = recent;
    }

}
