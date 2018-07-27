package com.imukal.myijazah.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetValidasi {
    @SerializedName("status")
    String status;
    @SerializedName("result")
    List<modelValidasi> listData;
    @SerializedName("pesan")
    String pesan;

    public void setStatus(String status) {
        this.status = status;
    }

    public void setListData(List<modelValidasi> listData) {
        this.listData = listData;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    public String getStatus() {
        return status;
    }

    public List<modelValidasi> getListData() {
        return listData;
    }

    public String getPesan() {
        return pesan;
    }
}
