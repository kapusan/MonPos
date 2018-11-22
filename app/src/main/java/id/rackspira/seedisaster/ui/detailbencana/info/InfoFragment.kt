package id.rackspira.seedisaster.ui.detailbencana.info


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson

import id.rackspira.seedisaster.R
import id.rackspira.seedisaster.data.network.entity.ListBencana
import kotlinx.android.synthetic.main.fragment_info.*
import kotlinx.android.synthetic.main.fragment_posko.*
import id.rackspira.seedisaster.ui.detailPosko.DetailPoskoActivity
import kotlinx.android.synthetic.main.list_bencana.*

class InfoFragment : Fragment() {

    private lateinit var list: ListBencana

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_info, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageView3.setOnClickListener {
            context?.startActivity(Intent(context, DetailPoskoActivity::class.java))
        }

            list = arguments!!.getParcelable("posisi")

            textViewLokasiInfo.text = list.nkab + ", " + list.nprop
            tvHilangJumlah.text = list.hilang
            tvMeninggalJumlah.text = list.meninggal
            tvTerlukaJumlah.text = list.terluka
            tvMenderitaJumlah.text = list.menderita
            textViewJumlahBerat.text = list.rumahRusakBerat
            textViewJumlahRingan.text = list.rumahRusakRingan
            textViewJumlahSedang.text = list.rumahRusakSedang
            textViewKeterangan.text = list.keterangan

    }
}
