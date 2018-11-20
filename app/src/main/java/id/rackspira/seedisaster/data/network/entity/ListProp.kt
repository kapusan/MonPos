package id.rackspira.seedisaster.data.network.entity

import com.google.gson.annotations.SerializedName

data class ListProp(

    @SerializedName("kode")
    var kode: String? = null,
    @SerializedName("nama")
    var nama: String? = null

)
