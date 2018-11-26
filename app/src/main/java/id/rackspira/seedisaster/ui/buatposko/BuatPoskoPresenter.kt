package id.rackspira.seedisaster.ui.buatposko

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import id.rackspira.seedisaster.data.network.entity.DataPosko
import id.rackspira.seedisaster.data.network.entity.DataUser

class BuatPoskoPresenter(private val view: BuatPoskoView) {

    private val dataRef = FirebaseDatabase.getInstance().reference
    private val mAuth = FirebaseAuth.getInstance()

    fun tambahPosko(idBencana: String,uidUsr: String,
                    namaPosko: String, lat: String,
                    long: String, telp: String, desa: String, kec: String,
                    kab: String, prov: String, namapngjwb: String) {
        val ref = dataRef.child("Posko")
        val key = ref.push().key
        val dataPosko = DataPosko(key, idBencana, uidUsr, namaPosko, lat, namapngjwb, long, telp, desa, kec, kab, prov)
        ref.child(key!!).setValue(dataPosko)
            .addOnSuccessListener {
                view.onSuccess("Berhasil membuat posko")
            }
            .addOnFailureListener {
                view.onFailed("Gagal mebuat posko")
            }
    }

    fun getUser(noTelp: String) {
        var dataUser = DataUser()
        val ref = dataRef.child("Users").orderByChild("uid").equalTo(mAuth.currentUser?.uid)
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {
                val list = mutableListOf<DataUser>()
                for (snapshot in p0.children) {
                    dataUser= snapshot.getValue(DataUser::class.java)!!
                    list.add(dataUser)
                }
                view.getDataUser(dataUser)
            }
        })
    }

}