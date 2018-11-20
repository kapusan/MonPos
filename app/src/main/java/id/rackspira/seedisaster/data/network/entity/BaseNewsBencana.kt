package id.rackspira.seedisaster.data.network.entity

import com.google.gson.annotations.SerializedName

data class BaseNewsBencana(

    @SerializedName("count")
    var count: Long? = null,
    @SerializedName("data")
    var data: List<ListNewsBencana>? = null,
    @SerializedName("href")
    var href: String? = null,
    @SerializedName("time")
    var time: Long? = null,
    @SerializedName("totalCount")
    var totalCount: Long? = null

)
