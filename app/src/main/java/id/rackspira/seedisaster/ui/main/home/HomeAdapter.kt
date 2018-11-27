package id.rackspira.seedisaster.ui.main.home

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import id.rackspira.seedisaster.R
import id.rackspira.seedisaster.data.network.entity.ListBencana
import id.rackspira.seedisaster.ui.detailbencana.DetailbencanaActivity
import kotlinx.android.synthetic.main.list_bencana.view.*
import java.util.regex.Pattern

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    private val list = mutableListOf<ListBencana>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.list_bencana, parent, false)
    )

    override fun getItemCount() = list.size

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) = p0.bind(list[p1])

    internal fun addListBencana(bencana: List<ListBencana>) {
        this.list.clear()
        this.list.addAll(bencana)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(position: ListBencana) {
            val kejadian = capitalize(position.kejadian!!)
            val nprop = capitalize(position.nprop!!)
            val nkab = capitalize(position.nkab!!)

            itemView.textviewJudul.text = kejadian
            itemView.textViewLokasi.text = nprop + ", " + nkab
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailbencanaActivity::class.java)
                intent.putExtra("posisi", position)
                itemView.context.startActivity(intent)
            }

            if (itemView.textviewJudul.text == "Banjir"){
                itemView.imageViewBencana.setImageResource(R.drawable.ic_bencana_banjir)
            }else if (itemView.textviewJudul.text == "Tanah Longsor"){
                itemView.imageViewBencana.setImageResource(R.drawable.ic_bencana_tanahlongsor)
            }else if (itemView.textviewJudul.text == "Banjir dan Tanah Longsor"){
                itemView.imageViewBencana.setImageResource(R.drawable.ic_bencana_tanahlongsor)
            }else if (itemView.textviewJudul.text == "Gelombang Pasang \\/ Abrasi"){
                itemView.imageViewBencana.setImageResource(R.drawable.ic_bencana_gelombangpasang)
            }else if (itemView.textviewJudul.text == "Puting Beliung"){
                itemView.imageViewBencana.setImageResource(R.drawable.ic_bencana_putingbeliung)
            }else if (itemView.textviewJudul.text == "Kekeringan"){
                itemView.imageViewBencana.setImageResource(R.drawable.ic_bencana_kekeringan)
            }else if (itemView.textviewJudul.text == "Kebakaran Hutan Dan Lahan"){
                itemView.imageViewBencana.setImageResource(R.drawable.ic_bencana_kebakarankutan)
            }else if (itemView.textviewJudul.text == "Gempa Bumi"){
                itemView.imageViewBencana.setImageResource(R.drawable.ic_bencana_gempa)
            }else if (itemView.textviewJudul.text == "Tsunami"){
                itemView.imageViewBencana.setImageResource(R.drawable.ic_bencana_tsunami)
            }else if (itemView.textviewJudul.text == "Gempa Bumi Dan Tsunami"){
                itemView.imageViewBencana.setImageResource(R.drawable.ic_bencana_gempa)
            }else if (itemView.textviewJudul.text == "Letusan Gunung Api"){
                itemView.imageViewBencana.setImageResource(R.drawable.ic_bencana_letusangunungmerapi)
            }else if (itemView.textviewJudul.text == "Kebakaran"){
                itemView.imageViewBencana.setImageResource(R.drawable.ic_bencana_kebakaran)
            }else if (itemView.textviewJudul.text == "Kecelakaan Transportasi"){
                itemView.imageViewBencana.setImageResource(R.drawable.ic_bencana_kecelakaantransportasi)
            }else if (itemView.textviewJudul.text == "Kecelakaan Industri"){
                itemView.imageViewBencana.setImageResource(R.drawable.ic_bencana_kecelakaanindustri)
            }else if (itemView.textviewJudul.text == "Wabah Penyakit"){
                itemView.imageViewBencana.setImageResource(R.drawable.ic_bencana_wabahpentakit)
            }else if (itemView.textviewJudul.text == "Epidermi"){
                itemView.imageViewBencana.setImageResource(R.drawable.ic_bencana_epidermi)
            }else if (itemView.textviewJudul.text == "Serangan Hewan Liar"){
                itemView.imageViewBencana.setImageResource(R.drawable.ic_bencana_seranganhewanbuas)
            }else if (itemView.textviewJudul.text == "Hama Tanaman"){
                itemView.imageViewBencana.setImageResource(R.drawable.ic_bencana_hamatanaman)
            }else if (itemView.textviewJudul.text == "Kemaparan"){
                itemView.imageViewBencana.setImageResource(R.drawable.ic_bencana_kelaparan)
            }else if (itemView.textviewJudul.text == "Klb"){
                itemView.imageViewBencana.setImageResource(R.drawable.ic_bencana_epidermi)
            }else if (itemView.textviewJudul.text == "Konflik \\/ Kerusuhan Sosial"){
                itemView.imageViewBencana.setImageResource(R.drawable.ic_bencana_konflik)
            }else if (itemView.textviewJudul.text == "Bentrok Antar Kelompok"){
                itemView.imageViewBencana.setImageResource(R.drawable.ic_bencana_bentrokantarkelompok)
            }else if (itemView.textviewJudul.text == "Aksi teror \\/ Sabotase"){
                itemView.imageViewBencana.setImageResource(R.drawable.ic_bencana_aksiterorsabotase)
            }else{
                itemView.imageViewBencana.setImageResource(R.drawable.ic_bencana_kelaparan)
            }

        }

        private fun capitalize(capString: String): String {
            val capBuffer = StringBuffer()
            val capMatcher = Pattern.compile("([a-z])([a-z]*)", Pattern.CASE_INSENSITIVE).matcher(capString)
            while (capMatcher.find()) {
                capMatcher.appendReplacement(
                    capBuffer,
                    capMatcher.group(1).toUpperCase() + capMatcher.group(2).toLowerCase()
                )
            }
            return capMatcher.appendTail(capBuffer).toString()
        }
    }
}