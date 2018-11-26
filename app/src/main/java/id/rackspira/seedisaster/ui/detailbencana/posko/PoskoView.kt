package id.rackspira.seedisaster.ui.detailbencana.posko

import id.rackspira.seedisaster.data.network.entity.DataPosko
import id.rackspira.seedisaster.data.network.entity.DataUser

interface PoskoView {

    fun onSuccess(msg: String?)

    fun onFailed(ms: String?)

    fun onSuccessUpdate(msg: String?)

    fun onFailedUpdate(msg: String?)

    fun getDataPosko(dataPosko: List<DataPosko>)

}