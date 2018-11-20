package id.rackspira.seedisaster.data.network.entity

import com.google.gson.annotations.SerializedName

data class BaseDetailNewsBencana(

    @SerializedName("data")
    var data: List<DetailBencana>? = null

)
