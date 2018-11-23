package id.rackspira.seedisaster.ui.detailPosko

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import id.rackspira.seedisaster.data.network.entity.KebPosko

class DetailPoskoPresenter(private val view: DetailPoskoView) {

    private val dataRef = FirebaseDatabase.getInstance().reference

    fun tambahKebutuhan(idBencana: String,idPosko: String, namaKeb: String, jumlahKeb: String,
                        satuanKeb: String, kategori: String) {
        val kebutuhan: KebPosko
        val ref = dataRef.child("Kebutuhan")
        val id = ref.push().key
        kebutuhan = KebPosko(id, namaKeb, jumlahKeb, satuanKeb)
        dataRef.child(idBencana).child(idPosko).child(kategori).child(id!!).setValue(kebutuhan)
            .addOnSuccessListener {
                view.onSuccess("Berhasil menambah kebutuhan")
            }
            .addOnFailureListener {
                view.onFailed("Gagal menambah kebutuhan")
            }
    }

    fun getDataKebutuhan(idBencana: String, idPosko: String, kategori: String) {
        var kebutuhan: KebPosko
        val ref = dataRef.child("Kebutuhan").child(idBencana).child(idPosko).child(kategori)
        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(p0: DataSnapshot) {
                val list = mutableListOf<KebPosko>()
                for (snap in p0.children) {
                    kebutuhan = snap.getValue(KebPosko::class.java)!!
                    list.add(kebutuhan)
                }
                view.getDataKebutuhan(list)
            }
        })
    }

    fun updateKebutuhan(idBencana: String, idPosko: String, kategori: String, idKeb: String, kebPosko: KebPosko) {
        val ref = dataRef.child("Kebutuhan").child(idBencana).child(idPosko).child(kategori).child(idKeb)
        ref.setValue(kebPosko).addOnSuccessListener {
            view.onSuccessUpdate("Berhasil update kebutuhan")
        }.addOnFailureListener {
            view.onFailedUpdate("Gagal update kebutuhan")
        }
    }

    fun deleteKebutuhan(idBencana: String, idPosko: String, kategori: String, idKeb: String) {
        val ref = dataRef.child("Kebutuhan").child(idBencana).child(idPosko).child(kategori).child(idKeb)
        ref.removeValue()
    }

}