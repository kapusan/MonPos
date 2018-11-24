package id.rackspira.seedisaster.ui.buatposko

import com.google.firebase.database.FirebaseDatabase
import id.rackspira.seedisaster.data.network.entity.DataPosko

class BuatPoskoPresenter(private val view: BuatPoskoView) {

    private val dataRef = FirebaseDatabase.getInstance().reference

    fun tambahPosko(idBencana: String,uidUsr: String,
                    namaPosko: String, lat: String,
                    long: String, telp: String, urlProfil: String) {
        val ref = dataRef.child("Posko")
        val key = ref.push().key
        val dataPosko = DataPosko(key,idBencana, uidUsr, namaPosko, lat, long, telp, urlProfil)
        ref.child(key!!).setValue(dataPosko)
            .addOnSuccessListener {
                view.onSuccess("Berhasil membuat posko")
            }
            .addOnFailureListener {
                view.onFailed("Gagal mebuat posko")
            }
    }

}