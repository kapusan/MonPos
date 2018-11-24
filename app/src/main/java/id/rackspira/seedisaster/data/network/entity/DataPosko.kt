package id.rackspira.seedisaster.data.network.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataPosko(
    var idPosko: String? = null,
    var idBencana: String? = null,
    var uidUsr: String? = null,
    var namaPosko: String? = null,
    var lat: String? = null,
    var long: String? = null,
    var telp: String? = null,
    var urlPhoto: String? = null
) : Parcelable