package id.rackspira.seedisaster.ui.detailPosko

import android.annotation.SuppressLint
import android.app.Dialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import id.rackspira.seedisaster.R
import kotlinx.android.synthetic.main.activity_detail_posko.*


class DetailPoskoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_posko)

        val myStrings = arrayOf("Sandang", "Pangan", "Kesehatan", "Sanitasi", "Tempat Huni", "Relawan")

        ciProfil.setOnClickListener {
            val dialog = Dialog(this)
            dialog.setContentView(R.layout.popup_tambah_kebutuhan);
            val cancelButton = dialog.findViewById(R.id.textViewBatal) as TextView
            val saveButton = dialog.findViewById(R.id.textViewTambah) as TextView
            val namaKebutuhan = dialog.findViewById(R.id.editTextNamaKebutuhan) as EditText
            val spinner = dialog.findViewById(R.id.spinnerKategori) as Spinner
            val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, myStrings)
            spinner.adapter = arrayAdapter

            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // Code to perform some action when nothing is selected
                }
            }

            saveButton.setOnClickListener {
            }
            cancelButton.setOnClickListener { dialog.dismiss() }
            dialog.show()
        }
    }
}
