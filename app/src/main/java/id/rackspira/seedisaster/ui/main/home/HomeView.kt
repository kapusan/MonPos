package id.rackspira.seedisaster.ui.main.home

import id.rackspira.seedisaster.data.network.entity.ListBencana

interface HomeView {

    fun getListBencana(listBencana: List<ListBencana>)

    fun setMap()

    fun addMapMarker()

}