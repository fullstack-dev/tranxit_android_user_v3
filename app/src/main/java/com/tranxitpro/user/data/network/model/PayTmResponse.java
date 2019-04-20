package com.tranxitpro.user.data.network.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PayTmResponse {
    @SerializedName("MID")
    @Expose
    private String mID;
    @SerializedName("ORDER_ID")
    @Expose
    private String oRDERID;
    @SerializedName("CUST_ID")
    @Expose
    private String cUSTID;
    @SerializedName("INDUSTRY_TYPE_ID")
    @Expose
    private String iNDUSTRYTYPEID;
    @SerializedName("CHANNEL_ID")
    @Expose
    private String cHANNELID;
    @SerializedName("TXN_AMOUNT")
    @Expose
    private Integer tXNAMOUNT;
    @SerializedName("WEBSITE")
    @Expose
    private String wEBSITE;
    @SerializedName("CALLBACK_URL")
    @Expose
    private String cALLBACKURL;
    @SerializedName("MOBILE_NO")
    @Expose
    private String mOBILENO;
    @SerializedName("EMAIL")
    @Expose
    private String eMAIL;
    @SerializedName("CHECKSUMHASH")
    @Expose
    private String cHECKSUMHASH;

    public String getMID() {
        return mID;
    }

    public void setMID(String mID) {
        this.mID = mID;
    }

    public String getORDERID() {
        return oRDERID;
    }

    public void setORDERID(String oRDERID) {
        this.oRDERID = oRDERID;
    }

    public String getCUSTID() {
        return cUSTID;
    }

    public void setCUSTID(String cUSTID) {
        this.cUSTID = cUSTID;
    }

    public String getINDUSTRYTYPEID() {
        return iNDUSTRYTYPEID;
    }

    public void setINDUSTRYTYPEID(String iNDUSTRYTYPEID) {
        this.iNDUSTRYTYPEID = iNDUSTRYTYPEID;
    }

    public String getCHANNELID() {
        return cHANNELID;
    }

    public void setCHANNELID(String cHANNELID) {
        this.cHANNELID = cHANNELID;
    }

    public Integer getTXNAMOUNT() {
        return tXNAMOUNT;
    }

    public void setTXNAMOUNT(Integer tXNAMOUNT) {
        this.tXNAMOUNT = tXNAMOUNT;
    }

    public String getWEBSITE() {
        return wEBSITE;
    }

    public void setWEBSITE(String wEBSITE) {
        this.wEBSITE = wEBSITE;
    }

    public String getCALLBACKURL() {
        return cALLBACKURL;
    }

    public void setCALLBACKURL(String cALLBACKURL) {
        this.cALLBACKURL = cALLBACKURL;
    }

    public String getMOBILENO() {
        return mOBILENO;
    }

    public void setMOBILENO(String mOBILENO) {
        this.mOBILENO = mOBILENO;
    }

    public String getEMAIL() {
        return eMAIL;
    }

    public void setEMAIL(String eMAIL) {
        this.eMAIL = eMAIL;
    }

    public String getCHECKSUMHASH() {
        return cHECKSUMHASH;
    }

    public void setCHECKSUMHASH(String cHECKSUMHASH) {
        this.cHECKSUMHASH = cHECKSUMHASH;
    }
}
