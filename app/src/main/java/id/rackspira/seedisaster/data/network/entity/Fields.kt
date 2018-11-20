package id.rackspira.seedisaster.data.network.entity

import com.google.gson.annotations.SerializedName

data class Fields(

    @SerializedName("description")
    var description: String? = null,
    @SerializedName("id")
    var id: Long? = null,
    @SerializedName("name")
    var name: String? = null

)
