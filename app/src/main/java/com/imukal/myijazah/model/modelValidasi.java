package com.imukal.myijazah.model;
import com.google.gson.annotations.SerializedName;

public class modelValidasi {
    @SerializedName("nama")
    private String nama;
    @SerializedName("nim")
    private String nim;
    @SerializedName("progStudi")
    private String progStudi;
    @SerializedName("noIjazah")
    private String noIjazah;

    public modelValidasi(String nama, String nim, String progStudi, String noIjazah) {
        this.nama = nama;
        this.nim = nim;
        this.progStudi = progStudi;
        this.noIjazah = noIjazah;
    }

    public String getNama() {
        return nama;
    }

    public String getNim() {
        return nim;
    }

    public String getProgStudi() {
        return progStudi;
    }

    public String getNoIjazah() {
        return noIjazah;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public void setProgStudi(String progStudi) {
        this.progStudi = progStudi;
    }

    public void setNoIjazah(String noIjazah) {
        this.noIjazah = noIjazah;
    }
}
