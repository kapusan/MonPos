package id.rackspira.seedisaster.data.network.entity

import com.google.gson.annotations.SerializedName

data class ListBencana(

    @SerializedName("hilang")
    var hilang: String? = null,
    @SerializedName("kejadian")
    var kejadian: String? = null,
    @SerializedName("keterangan")
    var keterangan: String? = null,
    @SerializedName("kib")
    var kib: String? = null,
    @SerializedName("latitude")
    var latitude: Double? = null,
    @SerializedName("longitude")
    var longitude: Double? = null,
    @SerializedName("menderita")
    var menderita: String? = null,
    @SerializedName("meninggal")
    var meninggal: String? = null,
    @SerializedName("nkab")
    var nkab: String? = null,
    @SerializedName("nprop")
    var nprop: String? = null,
    @SerializedName("rumah_rusak_berat")
    var rumahRusakBerat: Any? = null,
    @SerializedName("rumah_rusak_ringan")
    var rumahRusakRingan: Any? = null,
    @SerializedName("rumah_rusak_sedang")
    var rumahRusakSedang: Any? = null,
    @SerializedName("terluka")
    var terluka: String? = null

)
