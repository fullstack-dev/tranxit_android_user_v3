package com.tranxitpro.user.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WalletResponse {

    @SerializedName("wallet_transation")
    @Expose
    private List<Wallet> walletTransation;
    @SerializedName("wallet_balance")
    @Expose
    private Float walletBalance;

    public List<Wallet> getWallets() {
        return walletTransation;
    }

    public void setWallets(List<Wallet> walletTransation) {
        this.walletTransation = walletTransation;
    }

    public Float getWalletBalance() {
        return walletBalance;
    }

    public void setWalletBalance(Float walletBalance) {
        this.walletBalance = walletBalance;
    }
}
