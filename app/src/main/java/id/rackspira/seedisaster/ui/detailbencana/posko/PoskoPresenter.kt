package id.rackspira.seedisaster.ui.detailbencana.posko

import com.google.firebase.database.*
import id.rackspira.seedisaster.data.network.entity.DataPosko


class PoskoPresenter(private val view: PoskoView) {

    private val dataRef = FirebaseDatabase.getInstance().reference

    fun getDataPosko(idBencana: String) {
        var dataPosko: DataPosko
        val ref = dataRef.child("Posko").child(idBencana)
        ref.addValueEventListener(object : ValueEventListener {
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

    fun updateDataPosko(idBencana: String, idPosko: String, dataPosko: DataPosko) {
        val ref = dataRef.child("Posko").child(idBencana).child(idPosko)
        ref.setValue(dataPosko).addOnSuccessListener {
            view.onSuccessUpdate("Berhasil update data")
        }.addOnFailureListener {
            view.onFailedUpdate("Update data gagal")
        }
    }

    fun deleteDataPosko(idBencana: String, idPosko: String) {
        val ref = dataRef.child("Posko").child(idBencana).child(idPosko)
        ref.removeValue()
    }

}