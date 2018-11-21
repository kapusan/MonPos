package id.rackspira.seedisaster.ui.buatposko

import com.google.firebase.database.FirebaseDatabase
import id.rackspira.seedisaster.data.network.entity.DataPosko

class BuatPoskoPresenter(private val view: BuatPoskoView) {

    private val dataRef = FirebaseDatabase.getInstance().reference

    fun tambahPosko(idBencana: String,uidUsr: String, namaPngJwb: String,
                    namaPosko: String, lat: String,
                    long: String, telp: String, ketTelp: String, urlProfil: String) {
        val ref = dataRef.child("Posko").child(idBencana)
        val key = ref.push().key
        val dataPosko = DataPosko(key, uidUsr, namaPngJwb, namaPosko, lat, long, telp, ketTelp, urlProfil)
        ref.child(key!!).setValue(dataPosko)
            .addOnSuccessListener {
                view.onSuccess("Berhasil membuat posko")
            }
            .addOnFailureListener {
                view.onFailed("Gagal mebuat posko")
            }
    }

}