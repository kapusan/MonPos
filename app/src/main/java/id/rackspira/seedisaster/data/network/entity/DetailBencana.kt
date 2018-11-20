package id.rackspira.seedisaster.data.network.entity

import com.google.gson.annotations.SerializedName

data class DetailBencana(
    @SerializedName("fields")
    var fields: Fields? = null,
    @SerializedName("id")
    var id: String? = null
)
