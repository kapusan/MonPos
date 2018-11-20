package id.rackspira.seedisaster.ui.detailbencana.posko

import com.google.firebase.database.*
import id.rackspira.seedisaster.data.network.entity.DataPosko


class PoskoPresenter(private val view: PoskoView) {

    private val dataRef = FirebaseDatabase.getInstance().reference

    fun tambahPosko(uidUsr: String, namaPngJwb: String,
                    namaPosko: String, lat: String,
                    long: String, telp: String, ketTelp: String, urlProfil: String) {
        val ref = dataRef.child("Posko")
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

    fun getDataPosko(uid: String) {
        var dataPosko: DataPosko
        dataRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(p0: DataSnapshot) {
                val list = mutableListOf<DataPosko>()
                for (snapshot in p0.children) {
                    dataPosko = snapshot.getValue(DataPosko::class.java)!!
                    list.add(dataPosko)
                }
                view.getDataPosko(list)
            }
        })

    }

    fun updateDataPosko(id: String, dataPosko: DataPosko) {
        val ref = dataRef.child("Posko").child(id)
        ref.setValue(dataPosko).addOnSuccessListener {
            view.onSuccessUpdate("Berhasil update data")
        }.addOnFailureListener {
            view.onFailedUpdate("Update data gagal")
        }
    }

    fun deleteDataPosko(id: String) {
        val ref = dataRef.child("Posko").child(id)
        ref.removeValue()
    }

}