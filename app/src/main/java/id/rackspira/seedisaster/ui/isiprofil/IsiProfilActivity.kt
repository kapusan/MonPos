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

    private var filepath: Uri? = null
    private val PICK_IMAGE_REQUEST: Int = 71
    private var urlPP: String? = null
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

        btnPilihGambar.setOnClickListener {
            chooseImage()
        }

        btnUpload.setOnClickListener {
            uploadImage()
        }

        btnSimpan.setOnClickListener {
            presenter.createProfil(edtNama.text.toString(), edtJenisKel.text.toString(), telp,edtTglLahir.text.toString(), urlPP!!)
        }

    }

    fun chooseImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            filepath = data.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filepath)
                imageViewPrev.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    fun uploadImage() {
        if (filepath != null) {
            val ref = storageReference?.child("images/" + UUID.randomUUID().toString())
            ref?.putFile(filepath!!)?.addOnCompleteListener {
                ref.downloadUrl.addOnSuccessListener {url ->
                    urlPP = url.toString()
                }
                Toast.makeText(this, "Upload berhasil", Toast.LENGTH_SHORT).show()
            }?.addOnFailureListener {
                Toast.makeText(this, "Upload gagal " + it.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onSuccess(msg: String?) {
        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun onFailed(ms: String?) {}
}
