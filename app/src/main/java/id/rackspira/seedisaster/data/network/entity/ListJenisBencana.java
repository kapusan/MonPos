
package id.rackspira.seedisaster.data.network.entity;

import com.google.gson.annotations.SerializedName;

public class ListJenisBencana {

    @SerializedName("kode")
    private String mKode;
    @SerializedName("nama")
    private String mNama;

    public String getKode() {
        return mKode;
    }

    public void setKode(String kode) {
        mKode = kode;
    }

    public String getNama() {
        return mNama;
    }

    public void setNama(String nama) {
        mNama = nama;
    }

}
