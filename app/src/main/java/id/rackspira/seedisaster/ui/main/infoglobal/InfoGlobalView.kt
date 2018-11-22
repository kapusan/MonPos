package id.rackspira.seedisaster.ui.main.infoglobal

import id.rackspira.seedisaster.data.network.entity.ListNewsBencana

interface InfoGlobalView {

    fun getListBencana(listBencana: List<ListNewsBencana>)

    fun onError(msg: String?)


}