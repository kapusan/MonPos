package id.rackspira.seedisaster.ui.detailPosko

import id.rackspira.seedisaster.data.network.entity.DataJumlahPengungsi
import id.rackspira.seedisaster.data.network.entity.KebPosko


interface DetailPoskoView {

    fun onSuccess(msg: String?)

    fun onFailed(ms: String?)

    fun onSuccessUpdate(msg: String?)

    fun onFailedUpdate(msg: String?)

    fun getDataKebutuhan(dataKebutuhan: List<KebPosko>)

    fun getUpdateDataKebutuhan(dataKebutuhan: List<KebPosko>)

    fun getJumlahPengungsu(dataJumlahPengungsi: DataJumlahPengungsi)

}