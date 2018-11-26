package id.rackspira.seedisaster.ui.buatposko

import id.rackspira.seedisaster.data.network.entity.DataUser

interface BuatPoskoView {

    fun onSuccess(msg: String?)

    fun onFailed(ms: String?)

    fun getDataUser(dataUser: DataUser)

}