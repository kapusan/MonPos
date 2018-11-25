package id.rackspira.seedisaster.data.network.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataJumlahPengungsi(
    var idJmlPeng: String? = null,
    var idPosko: String? = null,
    var laki: String? = null,
    var prempuan: String? = null,
    var balita: String? = null,
    var anak: String? = null,
    var lansia: String? = null
) : Parcelable