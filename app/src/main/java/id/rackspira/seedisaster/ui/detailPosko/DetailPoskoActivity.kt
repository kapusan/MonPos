package id.rackspira.seedisaster.ui.detailPosko

import android.annotation.SuppressLint
import android.app.Dialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import id.rackspira.seedisaster.R
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_detail_posko.*




class DetailPoskoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_posko)

        ciProfil.setOnClickListener {
            val dialog = Dialog(this)
            dialog.setTitle("Choose an item")
            dialog.setContentView(R.layout.popup_tambah_kebutuhan);
//            val cancelButton = dialog.findViewById(R.id.textViewBatal) as TextView
//            val saveButton = dialog.findViewById(R.id.textViewTambah) as TextView
//            val namaKebutuhan = dialog.findViewById(R.id.editTextNamaKebutuhan) as EditText

//            saveButton.setOnClickListener {
//                Toast.makeText(this@DetailPoskoActivity, "Data saved", Toast.LENGTH_SHORT).show()
//                dialog.dismiss()
//            }
//
//            cancelButton.setOnClickListener { dialog.dismiss() }

            dialog.show()
        }





    }




}
