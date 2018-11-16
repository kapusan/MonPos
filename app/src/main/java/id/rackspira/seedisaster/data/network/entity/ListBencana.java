
package id.rackspira.seedisaster.data.network.entity;

import com.google.gson.annotations.SerializedName;

public class ListBencana {

    @SerializedName("hilang")
    private String mHilang;
    @SerializedName("kejadian")
    private String mKejadian;
    @SerializedName("keterangan")
    private String mKeterangan;
    @SerializedName("kib")
    private String mKib;
    @SerializedName("latitude")
    private Double mLatitude;
    @SerializedName("longitude")
    private Double mLongitude;
    @SerializedName("menderita")
    private String mMenderita;
    @SerializedName("meninggal")
    private String mMeninggal;
    @SerializedName("nkab")
    private String mNkab;
    @SerializedName("nprop")
    private String mNprop;
    @SerializedName("rumah_rusak_berat")
    private Object mRumahRusakBerat;
    @SerializedName("rumah_rusak_ringan")
    private Object mRumahRusakRingan;
    @SerializedName("rumah_rusak_sedang")
    private Object mRumahRusakSedang;
    @SerializedName("terluka")
    private String mTerluka;

    public String getHilang() {
        return mHilang;
    }

    public void setHilang(String hilang) {
        mHilang = hilang;
    }

    public String getKejadian() {
        return mKejadian;
    }

    public void setKejadian(String kejadian) {
        mKejadian = kejadian;
    }

    public String getKeterangan() {
        return mKeterangan;
    }

    public void setKeterangan(String keterangan) {
        mKeterangan = keterangan;
    }

    public String getKib() {
        return mKib;
    }

    public void setKib(String kib) {
        mKib = kib;
    }

    public Double getLatitude() {
        return mLatitude;
    }

    public void setLatitude(Double latitude) {
        mLatitude = latitude;
    }

    public Double getLongitude() {
        return mLongitude;
    }

    public void setLongitude(Double longitude) {
        mLongitude = longitude;
    }

    public String getMenderita() {
        return mMenderita;
    }

    public void setMenderita(String menderita) {
        mMenderita = menderita;
    }

    public String getMeninggal() {
        return mMeninggal;
    }

    public void setMeninggal(String meninggal) {
        mMeninggal = meninggal;
    }

    public String getNkab() {
        return mNkab;
    }

    public void setNkab(String nkab) {
        mNkab = nkab;
    }

    public String getNprop() {
        return mNprop;
    }

    public void setNprop(String nprop) {
        mNprop = nprop;
    }

    public Object getRumahRusakBerat() {
        return mRumahRusakBerat;
    }

    public void setRumahRusakBerat(Object rumahRusakBerat) {
        mRumahRusakBerat = rumahRusakBerat;
    }

    public Object getRumahRusakRingan() {
        return mRumahRusakRingan;
    }

    public void setRumahRusakRingan(Object rumahRusakRingan) {
        mRumahRusakRingan = rumahRusakRingan;
    }

    public Object getRumahRusakSedang() {
        return mRumahRusakSedang;
    }

    public void setRumahRusakSedang(Object rumahRusakSedang) {
        mRumahRusakSedang = rumahRusakSedang;
    }

    public String getTerluka() {
        return mTerluka;
    }

    public void setTerluka(String terluka) {
        mTerluka = terluka;
    }

}
