package id.rackspira.seedisaster.data.network.entity

import com.google.gson.annotations.SerializedName

data class ListNewsBencana(

    @SerializedName("href")
    var href: String? = null,
    @SerializedName("id")
    var id: String? = null,
    @SerializedName("score")
    var score: Long? = null,
    @SerializedName("fields")
    var fields: Fields? = null

)
