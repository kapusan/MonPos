package id.rackspira.seedisaster.ui.global

import id.rackspira.seedisaster.data.network.entity.ListNewsBencana

interface GlobalView {

    fun getListBencana(listBencana: List<ListNewsBencana>)

    fun onError(msg: String?)

}