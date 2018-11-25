package id.rackspira.seedisaster.ui.detailPosko

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import id.rackspira.seedisaster.data.network.entity.DataJumlahPengungsi
import id.rackspira.seedisaster.data.network.entity.KebPosko

class DetailPoskoPresenter(private val view: DetailPoskoView) {

    private val dataRef = FirebaseDatabase.getInstance().reference

    fun tambahKebutuhan(
        idBencana: String, idPosko: String, namaKeb: String, jumlahKeb: String,
        satuanKeb: String, kategori: String
    ) {
        val kebutuhan: KebPosko
        val ref = dataRef.child("Kebutuhan")
        val id = ref.push().key
        kebutuhan = KebPosko(id, namaKeb, jumlahKeb, satuanKeb)
        ref.child(idBencana).child(idPosko).child(kategori).child(id!!).setValue(kebutuhan)
            .addOnSuccessListener {
                Log.d("anangkentot", "Tambah sukses")
                view.onSuccess("Berhasil menambah kebutuhan")
            }
            .addOnFailureListener {
                Log.d("anangkentot", "Tambah Gagal")
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

    fun tambahJumlahPengungsi(
        idPosko: String,
        laki: String,
        perempuan: String,
        balita: String,
        anak: String,
        lansia: String
    ) {
        val dataJml: DataJumlahPengungsi
        val ref = dataRef.child("JumlahPeng")
        val id = ref.push().key
        dataJml = DataJumlahPengungsi(id, idPosko, laki, perempuan, balita, anak, lansia)
        ref.child(id!!).setValue(dataJml).addOnSuccessListener {
            view.onSuccess("Berhasil menambah pengungsi")
        }.addOnFailureListener {
            view.onFailed("Gagal tambaah pengungsi")
        }
    }

    fun updateJumlahPengungsi(
        id: String,
        idPosko: String,
        laki: String,
        perempuan: String,
        balita: String,
        anak: String,
        lansia: String
    ) {
        val dataJml: DataJumlahPengungsi
        val ref = dataRef.child("JumlahPeng")
        dataJml = DataJumlahPengungsi(id, idPosko, laki, perempuan, balita, anak, lansia)
        ref.child(id).setValue(dataJml).addOnSuccessListener {
            view.onSuccess("Berhasil update")
        }.addOnFailureListener {
            view.onFailed("Gagal update")
        }
    }

    fun getJumlhPeng(idPosko: String) {
        val ref = dataRef.child("JumlahPeng").orderByChild("idPosko").equalTo(idPosko)
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(p0: DataSnapshot) {
                val list = mutableListOf<DataJumlahPengungsi>()
                for (snapshot in p0.children) {
                    val dataPosko = snapshot.getValue(DataJumlahPengungsi::class.java)!!
                    view.getJumlahPengungsu(dataPosko)
                }
            }
        })
    }

}