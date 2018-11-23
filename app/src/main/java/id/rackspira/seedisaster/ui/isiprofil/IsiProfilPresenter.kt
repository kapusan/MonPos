package id.rackspira.seedisaster.ui.isiprofil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import id.rackspira.seedisaster.data.network.entity.DataUser

class IsiProfilPresenter(private val view: IsiProfilView) {

    private val dataRef = FirebaseDatabase.getInstance().reference
    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    fun createProfil(nama: String, jk: String, noTel: String, tglLahir: String) {
        val uid = mAuth.currentUser?.uid
        val ref = dataRef.child("Users").child(noTel)
        val dataUser = DataUser(uid, nama, jk, noTel, tglLahir)
        ref.setValue(dataUser).addOnSuccessListener {
            view.onSuccess("Berhasil buat akun")
        }.addOnFailureListener {
            view.onFailed("Terjadi kesalahan")
        }
    }

}