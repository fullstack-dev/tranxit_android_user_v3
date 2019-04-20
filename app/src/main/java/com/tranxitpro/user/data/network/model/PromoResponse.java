package com.tranxitpro.user.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PromoResponse {

    @SerializedName("promo_list")
    @Expose
    private List<PromoList> promoList;

    public List<PromoList> getPromoList() {
        return promoList;
    }

    public void setPromoList(List<PromoList> promoList) {
        this.promoList = promoList;
    }
}
