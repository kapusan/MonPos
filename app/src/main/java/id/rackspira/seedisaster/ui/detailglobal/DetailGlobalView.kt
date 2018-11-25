package id.rackspira.seedisaster.ui.detailglobal

import id.rackspira.seedisaster.data.network.entity.Fields

interface DetailGlobalView {

    fun getDetail(field: Fields)

    fun onError(msg: String)

}