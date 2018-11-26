package id.rackspira.seedisaster.ui.main.home

import id.rackspira.seedisaster.data.network.entity.ListBencana
import id.rackspira.seedisaster.data.network.entity.ListJenisBencana

interface HomeView {

    fun getListBencana(listBencana: List<ListBencana>)

    fun onError(msg: String?)

    fun addMapMarker()

    fun getListJenis(listJenisBencana: List<ListJenisBencana>)

}