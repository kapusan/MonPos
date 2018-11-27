package id.rackspira.seedisaster.ui.isiprofil

import android.app.DatePickerDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.View
import id.rackspira.seedisaster.R
import kotlinx.android.synthetic.main.activity_isi_profil.*
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.FirebaseStorage
import id.rackspira.seedisaster.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_detail_posko.*
import java.util.*
import javax.xml.datatype.DatatypeConstants.MONTHS

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
        if (telp.isNotEmpty()) {
            edtNoTelefon.setText(telp)
        }

        edtTglLahir.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)


            val dpd = DatePickerDialog(this ,DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                // Display Selected date in textbox
                edtTglLahir.setText("" + dayOfMonth + "-" + (monthOfYear+1) +"-"+ year)
            }, year, month, day)
            dpd.show()
        }

        btnSimpan.setOnClickListener {
            if (edtNama.text.toString().isEmpty()) run { errorInfo.visibility = View.VISIBLE }
            else if (edtJenisKel.text.toString().isEmpty()) run { errorInfo.visibility = View.VISIBLE  }
            else if (edtTglLahir.text.toString().isEmpty()) run { errorInfo.visibility = View.VISIBLE  }
            else if (edtNoTelefon.text.toString().isEmpty()) run { errorInfo.visibility = View.VISIBLE  }
            else{
                presenter.createProfil(edtNama.text.toString(), edtJenisKel.text.toString(), edtNoTelefon.text.toString(),edtTglLahir.text.toString())
            }
        }

    }

    override fun onSuccess(msg: String?) {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onFailed(ms: String?) {}
}
