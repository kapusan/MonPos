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
import com.google.firebase.auth.FirebaseAuth
import id.rackspira.seedisaster.R
import id.rackspira.seedisaster.data.network.entity.DataJumlahPengungsi
import id.rackspira.seedisaster.data.network.entity.DataPosko
import id.rackspira.seedisaster.data.network.entity.KebPosko
import kotlinx.android.synthetic.main.activity_detail_posko.*


class DetailPoskoActivity : AppCompatActivity(), DetailPoskoView {

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
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
        setUp()

        val myStrings = arrayOf("Sandang", "Pangan", "Kesehatan", "Sanitasi", "Tempat Huni", "Relawan")
        val myStrings2 = arrayOf("Paket", "Buah", "Liter", "Rupiah", "Orang")

        System.out.print(mAuth.uid.toString())
        if (mAuth.uid.toString() != list.uidUsr.toString())
        {
            tambahPengungsi.visibility = View.GONE
            tambahKebutuhan.visibility = View.GONE
        }else{
            tambahPengungsi.visibility = View.VISIBLE
            tambahKebutuhan.visibility = View.VISIBLE
        }

        tambahKebutuhan.setOnClickListener {
            val dialogTKebutuhan = Dialog(this)
            dialogTKebutuhan.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogTKebutuhan.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialogTKebutuhan.setContentView(R.layout.popup_tambah_kebutuhan)
            val cancelButton = dialogTKebutuhan.findViewById(R.id.textViewBatal) as TextView
            val saveButton = dialogTKebutuhan.findViewById(R.id.textViewTambah) as TextView
            val namaKebutuhan = dialogTKebutuhan.findViewById(R.id.editTextNamaKebutuhan) as EditText
            val jumlahKebutuhan = dialogTKebutuhan.findViewById(R.id.editTextJumlah) as EditText
            val spinner = dialogTKebutuhan.findViewById(R.id.spinnerKategori) as Spinner
            val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, myStrings)
            spinner.adapter = arrayAdapter

            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                }
            }
            val spinner2 = dialogTKebutuhan.findViewById(R.id.spinnerPaket) as Spinner
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

                when {
                    namaKebutuhan.text.toString().isEmpty() -> run { namaKebutuhan.error = "Nama kebutuhan kosong" }
                    jumlahKebutuhan.text.toString().isEmpty() -> run { jumlahKebutuhan.error = "Jumlah kosong" }
                    else -> {
                        presenter.tambahKebutuhan(
                            list.idBencana.toString(), list.idPosko.toString(), namaKebutuhan.text.toString(),
                            jumlahKebutuhan.text.toString(), dataSpinner2, dataSpinner1 )
                        dialogTKebutuhan.dismiss()
                    }
                }

            }
            cancelButton.setOnClickListener { dialogTKebutuhan.dismiss() }
            dialogTKebutuhan.show()
        }

        backDetailPosko.setOnClickListener {
            finish()
        }

        tambahPengungsi.setOnClickListener{
            val dialogPengungsi = Dialog(this)
            dialogPengungsi.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogPengungsi.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialogPengungsi.setContentView(R.layout.popup_update_jumlah_pengungsi)

            val clickBatal = dialogPengungsi.findViewById(R.id.textViewBatal) as TextView
            val clickTambah = dialogPengungsi.findViewById(R.id.textViewTambah) as TextView
            val clickUpdate = dialogPengungsi.findViewById(R.id.textViewUpdate) as TextView
            val editTextLakilaki = dialogPengungsi.findViewById(R.id.editTextLakilaki) as EditText
            val editTextPerempuan = dialogPengungsi.findViewById(R.id.editTextPerempuan) as EditText
            val editTextAnakanak = dialogPengungsi.findViewById(R.id.editTextAnakanak) as EditText
            val editTextBalita = dialogPengungsi.findViewById(R.id.editTextBalita) as EditText
            val editTextLansia = dialogPengungsi.findViewById(R.id.editTextLansia) as EditText

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
                when {
                    editTextLakilaki.text.toString().isEmpty() -> run { editTextLakilaki.error = "Jumlah kosong" }
                    editTextPerempuan.text.toString().isEmpty() -> run { editTextPerempuan.error = "Jumlah Kosong" }
                    editTextAnakanak.text.toString().isEmpty() -> run { editTextPerempuan.error = "Jumlah kosong" }
                    editTextBalita.text.toString().isEmpty() -> run { editTextPerempuan.error = "Jumlah kosong" }
                    editTextLansia.text.toString().isEmpty() -> run { editTextPerempuan.error = "Jumlah kosong" }
                    else -> {
                        presenter.tambahJumlahPengungsi(
                            list.idPosko.toString(), editTextLakilaki.text.toString(), editTextPerempuan.text.toString(), editTextBalita.text.toString(), editTextAnakanak.text.toString(),editTextLansia.text.toString()
                        )
                        presenter.getJumlhPeng(
                            list.idPosko.toString())
                        dialogPengungsi.dismiss()
                    }
                }
            }

            clickUpdate.setOnClickListener {
                when {
                    editTextLakilaki.text.toString().isEmpty() -> run { editTextLakilaki.error = "Jumlah kosong" }
                    editTextPerempuan.text.toString().isEmpty() -> run { editTextPerempuan.error = "Jumlah Kosong" }
                    editTextAnakanak.text.toString().isEmpty() -> run { editTextPerempuan.error = "Jumlah kosong" }
                    editTextBalita.text.toString().isEmpty() -> run { editTextPerempuan.error = "Jumlah kosong" }
                    editTextLansia.text.toString().isEmpty() -> run { editTextPerempuan.error = "Jumlah kosong" }
                    else -> {
                        presenter.updateJumlahPengungsi(
                            idJumalhPengungsi, list.idPosko.toString(), editTextLakilaki.text.toString(), editTextPerempuan.text.toString(), editTextBalita.text.toString(), editTextAnakanak.text.toString(),editTextLansia.text.toString()
                        )
                        presenter.getJumlhPeng(
                            list.idPosko.toString())
                        dialogPengungsi.dismiss()
                    }
                }
            }
            clickBatal.setOnClickListener {
                dialogPengungsi.dismiss()
            }
            dialogPengungsi.show()
        }

        imageViewSandang.setOnClickListener {

            presenter.getDataKebutuhan(
                list.idBencana.toString(), list.idPosko.toString(), "Sandang"
            )
            val dialogSandang = Dialog(this)
            dialogSandang.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogSandang.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialogSandang.setContentView(R.layout.popup_kebutuhan_kategori)

            val clickKembali = dialogSandang.findViewById(R.id.textViewKembali) as TextView
            val clickUpdate = dialogSandang.findViewById(R.id.textViewUpdate) as TextView
            val namaKebutuhan = dialogSandang.findViewById(R.id.namaKebutuhan) as TextView
            val listKebutuhan = dialogSandang.findViewById(R.id.recyclerViewKebutuhan) as RecyclerView
            kebutuhanAdapter = KebutuhanAdapter()
            listKebutuhan.layoutManager = LinearLayoutManager(this)
            listKebutuhan.adapter = kebutuhanAdapter
            namaKebutuhan.text = "Sandang"

            Log.d("anangkentot2",list.idBencana)
            Log.d("anangkentot2",list.idBencana)

            if (mAuth.uid.toString() != list.uidUsr.toString()) {
                clickUpdate.visibility = View.GONE
            }else{
                clickUpdate.visibility = View.VISIBLE
            }

            clickUpdate.setOnClickListener {
                presenter.getUpdateKebutuhan(
                    list.idBencana.toString(), list.idPosko.toString(), "Sandang"
                )
                dialogSandang.dismiss()
                val dialogUpdateSanitasi = Dialog(this)
                dialogUpdateSanitasi.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialogUpdateSanitasi.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialogUpdateSanitasi.setContentView(R.layout.popup_update_kebutuhan)

                val clickBatal = dialogUpdateSanitasi.findViewById(R.id.textViewBatal) as TextView
                val clickTambah = dialogUpdateSanitasi.findViewById(R.id.textViewTambah) as TextView
                val listUpdateKebutuhan = dialogUpdateSanitasi.findViewById(R.id.recycleUpdateKebutuhan) as RecyclerView
                updateKebutuhanAdapter = UpdateKebutuhanAdapter("Sandang")
                listUpdateKebutuhan.layoutManager = LinearLayoutManager(this)
                listUpdateKebutuhan.adapter = updateKebutuhanAdapter
                namaKebutuhan.text = "Sandang"

                Log.d("anangkentot2",list.idBencana)
                Log.d("anangkentot2",list.idBencana)

                clickTambah.setOnClickListener {
                    dialogUpdateSanitasi.dismiss()

                }
                clickBatal.setOnClickListener { dialogUpdateSanitasi.dismiss() }
                dialogUpdateSanitasi.show()

            }
            clickKembali.setOnClickListener { dialogSandang.dismiss() }
            dialogSandang.show()
        }

        imageViewPangan.setOnClickListener {
            presenter.getDataKebutuhan(
                list.idBencana.toString(), list.idPosko.toString(), "Pangan"
            )
            val dialogPangan = Dialog(this)
            dialogPangan.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogPangan.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialogPangan.setContentView(R.layout.popup_kebutuhan_kategori)

            val clickKembali = dialogPangan.findViewById(R.id.textViewKembali) as TextView
            val clickUpdate = dialogPangan.findViewById(R.id.textViewUpdate) as TextView
            val namaKebutuhan = dialogPangan.findViewById(R.id.namaKebutuhan) as TextView
            val listKebutuhan = dialogPangan.findViewById(R.id.recyclerViewKebutuhan) as RecyclerView
            kebutuhanAdapter = KebutuhanAdapter()
            listKebutuhan.layoutManager = LinearLayoutManager(this)
            listKebutuhan.adapter = kebutuhanAdapter
            namaKebutuhan.text = "Pangan"

            Log.d("anangkentot2",list.idBencana)
            Log.d("anangkentot2",list.idBencana)

            if (mAuth.uid.toString() != list.uidUsr.toString()) {
                clickUpdate.visibility = View.GONE
            }else{
                clickUpdate.visibility = View.VISIBLE
            }

            clickUpdate.setOnClickListener {
                presenter.getUpdateKebutuhan(
                    list.idBencana.toString(), list.idPosko.toString(), "Pangan"
                )
                dialogPangan.dismiss()
                val dialogUpdateangan = Dialog(this)
                dialogUpdateangan.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialogUpdateangan.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialogUpdateangan.setContentView(R.layout.popup_update_kebutuhan)

                val clickBatal = dialogUpdateangan.findViewById(R.id.textViewBatal) as TextView
                val clickTambah = dialogUpdateangan.findViewById(R.id.textViewTambah) as TextView
                val listUpdateKebutuhan = dialogUpdateangan.findViewById(R.id.recycleUpdateKebutuhan) as RecyclerView
                updateKebutuhanAdapter = UpdateKebutuhanAdapter("Pangan")
                listUpdateKebutuhan.layoutManager = LinearLayoutManager(this)
                listUpdateKebutuhan.adapter = updateKebutuhanAdapter
                namaKebutuhan.text = "Pangan"

                Log.d("anangkentot2",list.idBencana)
                Log.d("anangkentot2",list.idBencana)

                clickTambah.setOnClickListener {
                    dialogUpdateangan.dismiss()

                }
                clickBatal.setOnClickListener { dialogUpdateangan.dismiss() }
                dialogUpdateangan.show()

            }
            clickKembali.setOnClickListener { dialogPangan.dismiss() }
            dialogPangan.show()

        }

        imageViewKesehatan.setOnClickListener {
            presenter.getDataKebutuhan(
                list.idBencana.toString(), list.idPosko.toString(), "Kesehatan"
            )
            val dialogKesehatan = Dialog(this)
            dialogKesehatan.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogKesehatan.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialogKesehatan.setContentView(R.layout.popup_kebutuhan_kategori)

            val clickKembali = dialogKesehatan.findViewById(R.id.textViewKembali) as TextView
            val clickUpdate = dialogKesehatan.findViewById(R.id.textViewUpdate) as TextView
            val namaKebutuhan = dialogKesehatan.findViewById(R.id.namaKebutuhan) as TextView
            val listKebutuhan = dialogKesehatan.findViewById(R.id.recyclerViewKebutuhan) as RecyclerView
            kebutuhanAdapter = KebutuhanAdapter()
            listKebutuhan.layoutManager = LinearLayoutManager(this)
            listKebutuhan.adapter = kebutuhanAdapter
            namaKebutuhan.text = "Kesehatan"

            Log.d("anangkentot2",list.idBencana)
            Log.d("anangkentot2",list.idBencana)

            if (mAuth.uid.toString() != list.uidUsr.toString()) {
                clickUpdate.visibility = View.GONE
            }else{
                clickUpdate.visibility = View.VISIBLE
            }

            clickUpdate.setOnClickListener {
                presenter.getUpdateKebutuhan(
                    list.idBencana.toString(), list.idPosko.toString(), "Kesehatan"
                )
                dialogKesehatan.dismiss()
                val dialogUpdateKesehatan = Dialog(this)
                dialogUpdateKesehatan.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialogUpdateKesehatan.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialogUpdateKesehatan.setContentView(R.layout.popup_update_kebutuhan)

                val clickBatal = dialogUpdateKesehatan.findViewById(R.id.textViewBatal) as TextView
                val clickTambah = dialogUpdateKesehatan.findViewById(R.id.textViewTambah) as TextView
                val listUpdateKebutuhan = dialogUpdateKesehatan.findViewById(R.id.recycleUpdateKebutuhan) as RecyclerView
                updateKebutuhanAdapter = UpdateKebutuhanAdapter("Kesehatan")
                listUpdateKebutuhan.layoutManager = LinearLayoutManager(this)
                listUpdateKebutuhan.adapter = updateKebutuhanAdapter
                namaKebutuhan.text = "Kesehatan"

                Log.d("anangkentot2",list.idBencana)
                Log.d("anangkentot2",list.idBencana)

                clickTambah.setOnClickListener {
                    dialogUpdateKesehatan.dismiss()

                }
                clickBatal.setOnClickListener { dialogUpdateKesehatan.dismiss() }
                dialogUpdateKesehatan.show()

            }
            clickKembali.setOnClickListener { dialogKesehatan.dismiss() }
            dialogKesehatan.show()
        }

        imageViewSanitasi.setOnClickListener {
            presenter.getDataKebutuhan(
                list.idBencana.toString(), list.idPosko.toString(), "Sanitasi"
            )
            val dialogSanitasi = Dialog(this)
            dialogSanitasi.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogSanitasi.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialogSanitasi.setContentView(R.layout.popup_kebutuhan_kategori)

            val clickKembali = dialogSanitasi.findViewById(R.id.textViewKembali) as TextView
            val clickUpdate = dialogSanitasi.findViewById(R.id.textViewUpdate) as TextView
            val namaKebutuhan = dialogSanitasi.findViewById(R.id.namaKebutuhan) as TextView
            val listKebutuhan = dialogSanitasi.findViewById(R.id.recyclerViewKebutuhan) as RecyclerView
            kebutuhanAdapter = KebutuhanAdapter()
            listKebutuhan.layoutManager = LinearLayoutManager(this)
            listKebutuhan.adapter = kebutuhanAdapter
            namaKebutuhan.text = "Sanitasi"

            Log.d("anangkentot2",list.idBencana)
            Log.d("anangkentot2",list.idBencana)

            if (mAuth.uid.toString() != list.uidUsr.toString()) {
                clickUpdate.visibility = View.GONE
            }else{
                clickUpdate.visibility = View.VISIBLE
            }

            clickUpdate.setOnClickListener {
                presenter.getUpdateKebutuhan(
                    list.idBencana.toString(), list.idPosko.toString(), "Sanitasi"
                )
                dialogSanitasi.dismiss()
                val dialogUpdateSanitasi = Dialog(this)
                dialogUpdateSanitasi.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialogUpdateSanitasi.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialogUpdateSanitasi.setContentView(R.layout.popup_update_kebutuhan)

                val clickBatal = dialogUpdateSanitasi.findViewById(R.id.textViewBatal) as TextView
                val clickTambah = dialogUpdateSanitasi.findViewById(R.id.textViewTambah) as TextView
                val listUpdateKebutuhan = dialogUpdateSanitasi.findViewById(R.id.recycleUpdateKebutuhan) as RecyclerView
                updateKebutuhanAdapter = UpdateKebutuhanAdapter("Sanitasi")
                listUpdateKebutuhan.layoutManager = LinearLayoutManager(this)
                listUpdateKebutuhan.adapter = updateKebutuhanAdapter
                namaKebutuhan.text = "Sanitasi"

                Log.d("anangkentot2",list.idBencana)
                Log.d("anangkentot2",list.idBencana)

                clickTambah.setOnClickListener {
                    dialogUpdateSanitasi.dismiss()

                }
                clickBatal.setOnClickListener { dialogUpdateSanitasi.dismiss() }
                dialogUpdateSanitasi.show()
            }
            clickKembali.setOnClickListener { dialogSanitasi.dismiss() }
            dialogSanitasi.show()
        }

        imageViewTempatHuni.setOnClickListener {
            presenter.getDataKebutuhan(
                list.idBencana.toString(), list.idPosko.toString(), "Tempat Huni"
            )
            val dialogTematHuni = Dialog(this)
            dialogTematHuni.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogTematHuni.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialogTematHuni.setContentView(R.layout.popup_kebutuhan_kategori)

            val clickKembali = dialogTematHuni.findViewById(R.id.textViewKembali) as TextView
            val clickUpdate = dialogTematHuni.findViewById(R.id.textViewUpdate) as TextView
            val namaKebutuhan = dialogTematHuni.findViewById(R.id.namaKebutuhan) as TextView
            val listKebutuhan = dialogTematHuni.findViewById(R.id.recyclerViewKebutuhan) as RecyclerView
            kebutuhanAdapter = KebutuhanAdapter()
            listKebutuhan.layoutManager = LinearLayoutManager(this)
            listKebutuhan.adapter = kebutuhanAdapter
            namaKebutuhan.text = "Tempat Huni"

            Log.d("anangkentot2",list.idBencana)
            Log.d("anangkentot2",list.idBencana)

            if (mAuth.uid.toString() != list.uidUsr.toString()) {
                clickUpdate.visibility = View.GONE
            }else{
                clickUpdate.visibility = View.VISIBLE
            }

            clickUpdate.setOnClickListener {
                presenter.getUpdateKebutuhan(
                    list.idBencana.toString(), list.idPosko.toString(), "Tempat Huni"
                )
                dialogTematHuni.dismiss()
                val dialogUpdateTempatHuni = Dialog(this)
                dialogUpdateTempatHuni.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialogUpdateTempatHuni.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialogUpdateTempatHuni.setContentView(R.layout.popup_update_kebutuhan)

                val clickBatal = dialogUpdateTempatHuni.findViewById(R.id.textViewBatal) as TextView
                val clickTambah = dialogUpdateTempatHuni.findViewById(R.id.textViewTambah) as TextView
                val listUpdateKebutuhan = dialogUpdateTempatHuni.findViewById(R.id.recycleUpdateKebutuhan) as RecyclerView
                updateKebutuhanAdapter = UpdateKebutuhanAdapter("Tempat Huni")
                listUpdateKebutuhan.layoutManager = LinearLayoutManager(this)
                listUpdateKebutuhan.adapter = updateKebutuhanAdapter
                namaKebutuhan.text = "Tempat Huni"

                Log.d("anangkentot2",list.idBencana)
                Log.d("anangkentot2",list.idBencana)

                clickTambah.setOnClickListener {
                    dialogUpdateTempatHuni.dismiss()

                }
                clickBatal.setOnClickListener { dialogUpdateTempatHuni.dismiss() }
                dialogUpdateTempatHuni.show()
            }
            clickKembali.setOnClickListener { dialogTematHuni.dismiss() }
            dialogTematHuni.show()
        }

        imageViewRelawan.setOnClickListener {
            presenter.getDataKebutuhan(
                list.idBencana.toString(), list.idPosko.toString(), "Relawan"
            )
            val dialogRelawan = Dialog(this)
            dialogRelawan.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogRelawan.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialogRelawan.setContentView(R.layout.popup_kebutuhan_kategori)

            val clickKembali = dialogRelawan.findViewById(R.id.textViewKembali) as TextView
            val clickUpdate = dialogRelawan.findViewById(R.id.textViewUpdate) as TextView
            val namaKebutuhan = dialogRelawan.findViewById(R.id.namaKebutuhan) as TextView
            val listKebutuhan = dialogRelawan.findViewById(R.id.recyclerViewKebutuhan) as RecyclerView
            kebutuhanAdapter = KebutuhanAdapter()
            listKebutuhan.layoutManager = LinearLayoutManager(this)
            listKebutuhan.adapter = kebutuhanAdapter
            namaKebutuhan.text = "Relawan"

            Log.d("anangkentot2",list.idBencana)
            Log.d("anangkentot2",list.idBencana)

            if (mAuth.uid.toString() != list.uidUsr.toString()) {
                clickUpdate.visibility = View.GONE
            }else{
                clickUpdate.visibility = View.VISIBLE
            }

            clickUpdate.setOnClickListener {
                presenter.getUpdateKebutuhan(
                    list.idBencana.toString(), list.idPosko.toString(), "Relawan"
                )
                dialogRelawan.dismiss()
                val dialogUpdateRelawan = Dialog(this)
                dialogUpdateRelawan.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialogUpdateRelawan.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialogUpdateRelawan.setContentView(R.layout.popup_update_kebutuhan)

                val clickBatal = dialogUpdateRelawan.findViewById(R.id.textViewBatal) as TextView
                val clickTambah = dialogUpdateRelawan.findViewById(R.id.textViewTambah) as TextView
                val listUpdateKebutuhan = dialogUpdateRelawan.findViewById(R.id.recycleUpdateKebutuhan) as RecyclerView
                updateKebutuhanAdapter = UpdateKebutuhanAdapter("Relawan")
                listUpdateKebutuhan.layoutManager = LinearLayoutManager(this)
                listUpdateKebutuhan.adapter = updateKebutuhanAdapter
                namaKebutuhan.text = "Relawan"

                Log.d("anangkentot2",list.idBencana)
                Log.d("anangkentot2",list.idBencana)

                clickTambah.setOnClickListener {
                    dialogUpdateRelawan.dismiss()

                }
                clickBatal.setOnClickListener { dialogUpdateRelawan.dismiss() }
                dialogUpdateRelawan.show()
            }
            clickKembali.setOnClickListener { dialogRelawan.dismiss() }
            dialogRelawan.show()
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
    }

    override fun onFailedUpdate(msg: String?) {
    }

    override fun getDataKebutuhan(dataKebutuhan: List<KebPosko>) {
        Log.d("anangngentot", dataKebutuhan.size.toString())
        kebutuhanAdapter.addListPosko(dataKebutuhan)

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
