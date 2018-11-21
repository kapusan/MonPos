package id.rackspira.seedisaster.data.network.entity

import com.google.gson.annotations.SerializedName

data class BaseNewsBencana(

    @SerializedName("data")
    var data: List<ListNewsBencana>? = null

)
