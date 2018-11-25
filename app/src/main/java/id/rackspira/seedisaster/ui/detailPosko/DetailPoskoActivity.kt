package id.rackspira.seedisaster.ui.detailPosko

import android.app.Dialog
import android.opengl.Visibility
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import com.squareup.picasso.Picasso
import id.rackspira.seedisaster.R
import id.rackspira.seedisaster.data.network.entity.DataJumlahPengungsi
import id.rackspira.seedisaster.data.network.entity.DataPosko
import id.rackspira.seedisaster.data.network.entity.KebPosko
import id.rackspira.seedisaster.ui.detailbencana.posko.PoskoAdapter
import kotlinx.android.synthetic.main.activity_detail_posko.*
import kotlinx.android.synthetic.main.fragment_posko.*
import kotlinx.android.synthetic.main.popup_kebutuhan_kategori.*


class DetailPoskoActivity : AppCompatActivity(), DetailPoskoView {


    private lateinit var list: DataPosko
    private lateinit var kebutuhanAdapter: KebutuhanAdapter
    private lateinit var presenter: DetailPoskoPresenter
    private lateinit var idbencana: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_posko)
        presenter = DetailPoskoPresenter(this)

        list = intent.getParcelableExtra("posko")

        val myStrings = arrayOf("Sandang", "Pangan", "Kesehatan", "Sanitasi", "Tempat Huni", "Relawan")
        val myStrings2 = arrayOf("Paket", "Buah", "Liter", "Rupiah", "Orang")

        tambahKebutuhan.setOnClickListener {
            val dialog = Dialog(this)
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
                Log.d("anangnama", namaKebutuhan.toString())
                presenter.tambahKebutuhan(
                    list.idBencana.toString(), list.idPosko.toString(), namaKebutuhan.text.toString(),
                    jumlahKebutuhan.text.toString(), dataSpinner2, dataSpinner1
                )
                dialog.dismiss()
            }
            cancelButton.setOnClickListener { dialog.dismiss() }
            dialog.show()

            setUp()
        }

        imageViewSandang.setOnClickListener {

            presenter.getDataKebutuhan(
                list.idBencana.toString(), list.idPosko.toString(), "Sandang"
            )
            val dialog = Dialog(this)
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

            }
            clickKembali.setOnClickListener { dialog.dismiss() }
            dialog.show()
        }

        imageViewPangan.setOnClickListener {
            presenter.getDataKebutuhan(
                list.idBencana.toString(), list.idPosko.toString(), "Pangan"
            )

            val dialog = Dialog(this)
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

            }
            clickKembali.setOnClickListener { dialog.dismiss() }
            dialog.show()
        }

        imageViewKesehatan.setOnClickListener {
            presenter.getDataKebutuhan(
                list.idBencana.toString(), list.idPosko.toString(), "Kesehatan"
            )

            val dialog = Dialog(this)
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

            }
            clickKembali.setOnClickListener { dialog.dismiss() }
            dialog.show()
        }

        imageViewSanitasi.setOnClickListener {
            presenter.getDataKebutuhan(
                list.idBencana.toString(), list.idPosko.toString(), "Sanitasi"
            )
            val dialog = Dialog(this)
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

            }
            clickKembali.setOnClickListener { dialog.dismiss() }
            dialog.show()
        }

        imageViewTempatHuni.setOnClickListener {
            presenter.getDataKebutuhan(
                list.idBencana.toString(), list.idPosko.toString(), "Tempat Huni"
            )
            val dialog = Dialog(this)
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

            }
            clickKembali.setOnClickListener { dialog.dismiss() }
            dialog.show()
        }

        imageViewRelawan.setOnClickListener {
            presenter.getDataKebutuhan(
                list.idBencana.toString(), list.idPosko.toString(), "Relawan"
            )
            val dialog = Dialog(this)
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

            }
            clickKembali.setOnClickListener { dialog.dismiss() }
            dialog.show()
        }



    }



    private fun setUp() {
        namaUser.text = list.namaPenangungJawab
        nomorHpUser.text = list.telp
        Picasso.get().load(list.urlPhoto).into(imagePosko)
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

    override fun getJumlahPengungsu(dataJumlahPengungsi: DataJumlahPengungsi) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
