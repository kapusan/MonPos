package id.rackspira.seedisaster.ui.detailPosko

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.*
import com.squareup.picasso.Picasso
import id.rackspira.seedisaster.R
import id.rackspira.seedisaster.data.network.entity.DataJumlahPengungsi
import id.rackspira.seedisaster.data.network.entity.DataPosko
import id.rackspira.seedisaster.data.network.entity.KebPosko
import kotlinx.android.synthetic.main.activity_detail_posko.*


class DetailPoskoActivity : AppCompatActivity(), DetailPoskoView {

    private lateinit var list: DataPosko
    private lateinit var kebutuhanAdapter: KebutuhanAdapter
    private lateinit var updateKebutuhanAdapter: UpdateKebutuhanAdapter
    private lateinit var presenter: DetailPoskoPresenter
    private lateinit var idJumalhPengungsi: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_posko)
        presenter = DetailPoskoPresenter(this)


        list = intent.getParcelableExtra("posko")

        val myStrings = arrayOf("Sandang", "Pangan", "Kesehatan", "Sanitasi", "Tempat Huni", "Relawan")
        val myStrings2 = arrayOf("Paket", "Buah", "Liter", "Rupiah", "Orang")

        tambahKebutuhan.setOnClickListener {
            val dialog = Dialog(this)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setContentView(R.layout.popup_tambah_kebutuhan)
            val cancelButton = dialog.findViewById(R.id.textViewBatal) as TextView
            val saveButton = dialog.findViewById(R.id.textViewTambah) as TextView
            val namaKebutuhan = dialog.findViewById(R.id.editTextNamaKebutuhan) as EditText
            val jumlahKebutuhan = dialog.findViewById(R.id.editTextJumlah) as EditText
            val spinner = dialog.findViewById(R.id.spinnerKategori) as Spinner
            val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, myStrings)
            spinner.adapter = arrayAdapter

            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                }
            }
            val spinner2 = dialog.findViewById(R.id.spinnerPaket) as Spinner
            val arrayAdapter2 = ArrayAdapter(this, android.R.layout.simple_spinner_item, myStrings2)
            spinner2.adapter = arrayAdapter2

            spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                }
            }
            saveButton.setOnClickListener {
                val dataSpinner1 = spinner.selectedItem.toString()
                val dataSpinner2 = spinner2.selectedItem.toString()
                Log.d("SPINNER1", dataSpinner1)
                Log.d("SPINNER2", dataSpinner2)
                Log.d("anangnama", namaKebutuhan.text.toString())

                if (namaKebutuhan.getText().toString().isEmpty()) run { namaKebutuhan.error = "Nama kebutuhan kosong" }
                else if (jumlahKebutuhan.getText().toString().isEmpty()) run { jumlahKebutuhan.error = "Jumlah kosong" }
                else{
                    presenter.tambahKebutuhan(
                        list.idBencana.toString(), list.idPosko.toString(), namaKebutuhan.text.toString(),
                        jumlahKebutuhan.text.toString(), dataSpinner2, dataSpinner1 )
                    dialog.dismiss()
                }

            }
            cancelButton.setOnClickListener { dialog.dismiss() }
            dialog.show()

            setUp()
        }

        tambahPengungsi.setOnClickListener{
            val dialog = Dialog(this)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setContentView(R.layout.popup_update_jumlah_pengungsi)

            val clickBatal = dialog.findViewById(R.id.textViewBatal) as TextView
            val clickTambah = dialog.findViewById(R.id.textViewTambah) as TextView
            val clickUpdate = dialog.findViewById(R.id.textViewUpdate) as TextView
            val editTextLakilaki = dialog.findViewById(R.id.editTextLakilaki) as EditText
            val editTextPerempuan = dialog.findViewById(R.id.editTextPerempuan) as EditText
            val editTextAnakanak = dialog.findViewById(R.id.editTextAnakanak) as EditText
            val editTextBalita = dialog.findViewById(R.id.editTextBalita) as EditText
            val editTextLansia = dialog.findViewById(R.id.editTextLansia) as EditText

            editTextLakilaki.setText(textViewJumlahL.text.toString())
            editTextPerempuan.setText(textViewJumlahP.text.toString())
            editTextAnakanak.setText(textViewJumlahAnak.text.toString())
            editTextBalita.setText(textViewJumlahBalita.text.toString())
            editTextLansia.setText(textViewJumlahLansi.text.toString())

            if (editTextLakilaki.text.toString() == "0" && editTextPerempuan.text.toString() == "0" && editTextAnakanak.text.toString() == "0" && editTextBalita.text.toString() == "0" && editTextLansia.text.toString() == "0")
            {
                clickTambah.visibility = View.VISIBLE
                clickUpdate.visibility = View.INVISIBLE
            }else{
                clickTambah.visibility = View.INVISIBLE
                clickUpdate.visibility = View.VISIBLE
            }

            clickTambah.setOnClickListener {
                if (editTextLakilaki.text.toString().isEmpty()) run { editTextLakilaki.error = "Jumlah kosong" }
                else if (editTextPerempuan.text.toString().isEmpty()) run { editTextPerempuan.error = "Jumlah Kosong" }
                else if (editTextAnakanak.text.toString().isEmpty()) run { editTextPerempuan.error = "Jumlah kosong" }
                else if (editTextBalita.text.toString().isEmpty()) run { editTextPerempuan.error = "Jumlah kosong" }
                else if (editTextLansia.text.toString().isEmpty()) run { editTextPerempuan.error = "Jumlah kosong" }
                else{
                    presenter.tambahJumlahPengungsi(
                        list.idPosko.toString(), editTextLakilaki.text.toString(), editTextPerempuan.text.toString(), editTextBalita.text.toString(), editTextAnakanak.text.toString(),editTextLansia.text.toString()
                    )
                    presenter.getJumlhPeng(
                        list.idPosko.toString())
                    dialog.dismiss()
                }
            }

            clickUpdate.setOnClickListener {
                if (editTextLakilaki.text.toString().isEmpty()) run { editTextLakilaki.error = "Jumlah kosong" }
                else if (editTextPerempuan.text.toString().isEmpty()) run { editTextPerempuan.error = "Jumlah Kosong" }
                else if (editTextAnakanak.text.toString().isEmpty()) run { editTextPerempuan.error = "Jumlah kosong" }
                else if (editTextBalita.text.toString().isEmpty()) run { editTextPerempuan.error = "Jumlah kosong" }
                else if (editTextLansia.text.toString().isEmpty()) run { editTextPerempuan.error = "Jumlah kosong" }
                else{
                    presenter.updateJumlahPengungsi(
                        idJumalhPengungsi, list.idPosko.toString(), editTextLakilaki.text.toString(), editTextPerempuan.text.toString(), editTextBalita.text.toString(), editTextAnakanak.text.toString(),editTextLansia.text.toString()
                    )
                    presenter.getJumlhPeng(
                        list.idPosko.toString())
                    dialog.dismiss()
                }
            }
            clickBatal.setOnClickListener {
                dialog.dismiss()
            }
            dialog.show()
        }

        imageViewSandang.setOnClickListener {

            presenter.getDataKebutuhan(
                list.idBencana.toString(), list.idPosko.toString(), "Sandang"
            )
            val dialog = Dialog(this)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setContentView(R.layout.popup_kebutuhan_kategori)

            val clickKembali = dialog.findViewById(R.id.textViewKembali) as TextView
            val clickUpdate = dialog.findViewById(R.id.textViewUpdate) as TextView
            val namaKebutuhan = dialog.findViewById(R.id.namaKebutuhan) as TextView
            val listKebutuhan = dialog.findViewById(R.id.recyclerViewKebutuhan) as RecyclerView
            kebutuhanAdapter = KebutuhanAdapter()
            listKebutuhan.layoutManager = LinearLayoutManager(this)
            listKebutuhan.adapter = kebutuhanAdapter
            namaKebutuhan.text = "Sandang"

            Log.d("anangkentot2",list.idBencana)
            Log.d("anangkentot2",list.idBencana)

            clickUpdate.setOnClickListener {
                presenter.getUpdateKebutuhan(
                    list.idBencana.toString(), list.idPosko.toString(), "Sandang"
                )
                dialog.dismiss()
                val dialog = Dialog(this)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.setContentView(R.layout.popup_update_kebutuhan)

                val clickBatal = dialog.findViewById(R.id.textViewBatal) as TextView
                val clickTambah = dialog.findViewById(R.id.textViewTambah) as TextView
                val listUpdateKebutuhan = dialog.findViewById(R.id.recycleUpdateKebutuhan) as RecyclerView
                updateKebutuhanAdapter = UpdateKebutuhanAdapter("Sandang")
                listUpdateKebutuhan.layoutManager = LinearLayoutManager(this)
                listUpdateKebutuhan.adapter = updateKebutuhanAdapter
                namaKebutuhan.text = "Sandang"

                Log.d("anangkentot2",list.idBencana)
                Log.d("anangkentot2",list.idBencana)

                clickTambah.setOnClickListener {
                    dialog.dismiss()

                }
                clickBatal.setOnClickListener { dialog.dismiss() }
                dialog.show()

            }
            clickKembali.setOnClickListener { dialog.dismiss() }
            dialog.show()
        }

        imageViewPangan.setOnClickListener {
            presenter.getDataKebutuhan(
                list.idBencana.toString(), list.idPosko.toString(), "Pangan"
            )
            val dialog = Dialog(this)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setContentView(R.layout.popup_kebutuhan_kategori)

            val clickKembali = dialog.findViewById(R.id.textViewKembali) as TextView
            val clickUpdate = dialog.findViewById(R.id.textViewUpdate) as TextView
            val namaKebutuhan = dialog.findViewById(R.id.namaKebutuhan) as TextView
            val listKebutuhan = dialog.findViewById(R.id.recyclerViewKebutuhan) as RecyclerView
            kebutuhanAdapter = KebutuhanAdapter()
            listKebutuhan.layoutManager = LinearLayoutManager(this)
            listKebutuhan.adapter = kebutuhanAdapter
            namaKebutuhan.text = "Pangan"

            Log.d("anangkentot2",list.idBencana)
            Log.d("anangkentot2",list.idBencana)

            clickUpdate.setOnClickListener {
                presenter.getUpdateKebutuhan(
                    list.idBencana.toString(), list.idPosko.toString(), "Pangan"
                )
                dialog.dismiss()
                val dialog = Dialog(this)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.setContentView(R.layout.popup_update_kebutuhan)

                val clickBatal = dialog.findViewById(R.id.textViewBatal) as TextView
                val clickTambah = dialog.findViewById(R.id.textViewTambah) as TextView
                val listUpdateKebutuhan = dialog.findViewById(R.id.recycleUpdateKebutuhan) as RecyclerView
                updateKebutuhanAdapter = UpdateKebutuhanAdapter("Pangan")
                listUpdateKebutuhan.layoutManager = LinearLayoutManager(this)
                listUpdateKebutuhan.adapter = updateKebutuhanAdapter
                namaKebutuhan.text = "Pangan"

                Log.d("anangkentot2",list.idBencana)
                Log.d("anangkentot2",list.idBencana)

                clickTambah.setOnClickListener {
                    dialog.dismiss()

                }
                clickBatal.setOnClickListener { dialog.dismiss() }
                dialog.show()

            }
            clickKembali.setOnClickListener { dialog.dismiss() }
            dialog.show()

        }

        imageViewKesehatan.setOnClickListener {
            presenter.getDataKebutuhan(
                list.idBencana.toString(), list.idPosko.toString(), "Kesehatan"
            )
            val dialog = Dialog(this)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setContentView(R.layout.popup_kebutuhan_kategori)

            val clickKembali = dialog.findViewById(R.id.textViewKembali) as TextView
            val clickUpdate = dialog.findViewById(R.id.textViewUpdate) as TextView
            val namaKebutuhan = dialog.findViewById(R.id.namaKebutuhan) as TextView
            val listKebutuhan = dialog.findViewById(R.id.recyclerViewKebutuhan) as RecyclerView
            kebutuhanAdapter = KebutuhanAdapter()
            listKebutuhan.layoutManager = LinearLayoutManager(this)
            listKebutuhan.adapter = kebutuhanAdapter
            namaKebutuhan.text = "Kesehatan"

            Log.d("anangkentot2",list.idBencana)
            Log.d("anangkentot2",list.idBencana)

            clickUpdate.setOnClickListener {
                presenter.getUpdateKebutuhan(
                    list.idBencana.toString(), list.idPosko.toString(), "Kesehatan"
                )
                dialog.dismiss()
                val dialog = Dialog(this)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.setContentView(R.layout.popup_update_kebutuhan)

                val clickBatal = dialog.findViewById(R.id.textViewBatal) as TextView
                val clickTambah = dialog.findViewById(R.id.textViewTambah) as TextView
                val listUpdateKebutuhan = dialog.findViewById(R.id.recycleUpdateKebutuhan) as RecyclerView
                updateKebutuhanAdapter = UpdateKebutuhanAdapter("Kesehatan")
                listUpdateKebutuhan.layoutManager = LinearLayoutManager(this)
                listUpdateKebutuhan.adapter = updateKebutuhanAdapter
                namaKebutuhan.text = "Kesehatan"

                Log.d("anangkentot2",list.idBencana)
                Log.d("anangkentot2",list.idBencana)

                clickTambah.setOnClickListener {
                    dialog.dismiss()

                }
                clickBatal.setOnClickListener { dialog.dismiss() }
                dialog.show()

            }
            clickKembali.setOnClickListener { dialog.dismiss() }
            dialog.show()
        }

        imageViewSanitasi.setOnClickListener {
            presenter.getDataKebutuhan(
                list.idBencana.toString(), list.idPosko.toString(), "Sanitasi"
            )
            val dialog = Dialog(this)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setContentView(R.layout.popup_kebutuhan_kategori)

            val clickKembali = dialog.findViewById(R.id.textViewKembali) as TextView
            val clickUpdate = dialog.findViewById(R.id.textViewUpdate) as TextView
            val namaKebutuhan = dialog.findViewById(R.id.namaKebutuhan) as TextView
            val listKebutuhan = dialog.findViewById(R.id.recyclerViewKebutuhan) as RecyclerView
            kebutuhanAdapter = KebutuhanAdapter()
            listKebutuhan.layoutManager = LinearLayoutManager(this)
            listKebutuhan.adapter = kebutuhanAdapter
            namaKebutuhan.text = "Sanitasi"

            Log.d("anangkentot2",list.idBencana)
            Log.d("anangkentot2",list.idBencana)

            clickUpdate.setOnClickListener {
                presenter.getUpdateKebutuhan(
                    list.idBencana.toString(), list.idPosko.toString(), "Sanitasi"
                )
                dialog.dismiss()
                val dialog = Dialog(this)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.setContentView(R.layout.popup_update_kebutuhan)

                val clickBatal = dialog.findViewById(R.id.textViewBatal) as TextView
                val clickTambah = dialog.findViewById(R.id.textViewTambah) as TextView
                val listUpdateKebutuhan = dialog.findViewById(R.id.recycleUpdateKebutuhan) as RecyclerView
                updateKebutuhanAdapter = UpdateKebutuhanAdapter("Sanitasi")
                listUpdateKebutuhan.layoutManager = LinearLayoutManager(this)
                listUpdateKebutuhan.adapter = updateKebutuhanAdapter
                namaKebutuhan.text = "Sanitasi"

                Log.d("anangkentot2",list.idBencana)
                Log.d("anangkentot2",list.idBencana)

                clickTambah.setOnClickListener {
                    dialog.dismiss()

                }
                clickBatal.setOnClickListener { dialog.dismiss() }
                dialog.show()
            }
            clickKembali.setOnClickListener { dialog.dismiss() }
            dialog.show()
        }

        imageViewTempatHuni.setOnClickListener {
            presenter.getDataKebutuhan(
                list.idBencana.toString(), list.idPosko.toString(), "Tempat Huni"
            )
            val dialog = Dialog(this)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setContentView(R.layout.popup_kebutuhan_kategori)

            val clickKembali = dialog.findViewById(R.id.textViewKembali) as TextView
            val clickUpdate = dialog.findViewById(R.id.textViewUpdate) as TextView
            val namaKebutuhan = dialog.findViewById(R.id.namaKebutuhan) as TextView
            val listKebutuhan = dialog.findViewById(R.id.recyclerViewKebutuhan) as RecyclerView
            kebutuhanAdapter = KebutuhanAdapter()
            listKebutuhan.layoutManager = LinearLayoutManager(this)
            listKebutuhan.adapter = kebutuhanAdapter
            namaKebutuhan.text = "Tempat Huni"

            Log.d("anangkentot2",list.idBencana)
            Log.d("anangkentot2",list.idBencana)

            clickUpdate.setOnClickListener {
                presenter.getUpdateKebutuhan(
                    list.idBencana.toString(), list.idPosko.toString(), "Tempat Huni"
                )
                dialog.dismiss()
                val dialog = Dialog(this)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.setContentView(R.layout.popup_update_kebutuhan)

                val clickBatal = dialog.findViewById(R.id.textViewBatal) as TextView
                val clickTambah = dialog.findViewById(R.id.textViewTambah) as TextView
                val listUpdateKebutuhan = dialog.findViewById(R.id.recycleUpdateKebutuhan) as RecyclerView
                updateKebutuhanAdapter = UpdateKebutuhanAdapter("Tempat Huni")
                listUpdateKebutuhan.layoutManager = LinearLayoutManager(this)
                listUpdateKebutuhan.adapter = updateKebutuhanAdapter
                namaKebutuhan.text = "Tempat Huni"

                Log.d("anangkentot2",list.idBencana)
                Log.d("anangkentot2",list.idBencana)

                clickTambah.setOnClickListener {
                    dialog.dismiss()

                }
                clickBatal.setOnClickListener { dialog.dismiss() }
                dialog.show()
            }
            clickKembali.setOnClickListener { dialog.dismiss() }
            dialog.show()
        }

        imageViewRelawan.setOnClickListener {
            presenter.getDataKebutuhan(
                list.idBencana.toString(), list.idPosko.toString(), "Relawan"
            )
            val dialog = Dialog(this)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setContentView(R.layout.popup_kebutuhan_kategori)

            val clickKembali = dialog.findViewById(R.id.textViewKembali) as TextView
            val clickUpdate = dialog.findViewById(R.id.textViewUpdate) as TextView
            val namaKebutuhan = dialog.findViewById(R.id.namaKebutuhan) as TextView
            val listKebutuhan = dialog.findViewById(R.id.recyclerViewKebutuhan) as RecyclerView
            kebutuhanAdapter = KebutuhanAdapter()
            listKebutuhan.layoutManager = LinearLayoutManager(this)
            listKebutuhan.adapter = kebutuhanAdapter
            namaKebutuhan.text = "Relawan"

            Log.d("anangkentot2",list.idBencana)
            Log.d("anangkentot2",list.idBencana)

            clickUpdate.setOnClickListener {
                presenter.getUpdateKebutuhan(
                    list.idBencana.toString(), list.idPosko.toString(), "Relawan"
                )
                dialog.dismiss()
                val dialog = Dialog(this)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.setContentView(R.layout.popup_update_kebutuhan)

                val clickBatal = dialog.findViewById(R.id.textViewBatal) as TextView
                val clickTambah = dialog.findViewById(R.id.textViewTambah) as TextView
                val listUpdateKebutuhan = dialog.findViewById(R.id.recycleUpdateKebutuhan) as RecyclerView
                updateKebutuhanAdapter = UpdateKebutuhanAdapter("Relawan")
                listUpdateKebutuhan.layoutManager = LinearLayoutManager(this)
                listUpdateKebutuhan.adapter = updateKebutuhanAdapter
                namaKebutuhan.text = "Relawan"

                Log.d("anangkentot2",list.idBencana)
                Log.d("anangkentot2",list.idBencana)

                clickTambah.setOnClickListener {
                    dialog.dismiss()

                }
                clickBatal.setOnClickListener { dialog.dismiss() }
                dialog.show()
            }
            clickKembali.setOnClickListener { dialog.dismiss() }
            dialog.show()
        }

        Log.d("idPosko", list.idPosko.toString())
        presenter.getJumlhPeng(
            list.idPosko.toString()
        )

    }


    private fun setUp() {
        namaUser.text = list.namaPenangungJawab
        nomorHpUser.text = list.telp
    }

    override fun onSuccess(msg: String?) {
        Log.d("berhasil", "Berhasil")
    }

    override fun onFailed(ms: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSuccessUpdate(msg: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onFailedUpdate(msg: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getDataKebutuhan(dataKebutuhan: List<KebPosko>) {
        Log.d("anangngentot", dataKebutuhan.size.toString())
        kebutuhanAdapter.addListPosko(dataKebutuhan, list.idBencana)

    }

    override fun getUpdateDataKebutuhan(dataKebutuhan: List<KebPosko>) {
        updateKebutuhanAdapter.addListPosko(dataKebutuhan, list)
    }

    override fun getJumlahPengungsu(dataJumlahPengungsi: DataJumlahPengungsi) {
        Log.d("lakilaki",dataJumlahPengungsi.laki.toString())
        textViewJumlahL.text = dataJumlahPengungsi.laki.toString()
        textViewJumlahP.text = dataJumlahPengungsi.prempuan.toString()
        textViewJumlahBalita.text = dataJumlahPengungsi.balita.toString()
        textViewJumlahAnak.text = dataJumlahPengungsi.anak.toString()
        textViewJumlahLansi.text = dataJumlahPengungsi.lansia.toString()
        idJumalhPengungsi = dataJumlahPengungsi.idJmlPeng.toString()

    }

}
