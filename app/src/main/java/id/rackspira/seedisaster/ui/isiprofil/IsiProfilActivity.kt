package id.rackspira.seedisaster.ui.isiprofil

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import id.rackspira.seedisaster.R
import kotlinx.android.synthetic.main.activity_isi_profil.*
import java.io.IOException
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.FirebaseStorage
import id.rackspira.seedisaster.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*


class IsiProfilActivity : AppCompatActivity(), IsiProfilView {

    private lateinit var presenter: IsiProfilPresenter

    private var storage: FirebaseStorage? = null
    var storageReference: StorageReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_isi_profil)
        storage = FirebaseStorage.getInstance()
        storageReference = storage?.reference
        presenter = IsiProfilPresenter(this)

        val telp = intent.getStringExtra("no")
//        if (telp.isNotEmpty()) {
//            edtNoTelefon.setText(telp)
//        }

        btnSimpan.setOnClickListener {
            presenter.createProfil(edtNama.text.toString(), edtJenisKel.text.toString(), edtNoTelefon.text.toString(),edtTglLahir.text.toString())
        }

    }

    override fun onSuccess(msg: String?) {
        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun onFailed(ms: String?) {}
}
