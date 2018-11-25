package id.rackspira.seedisaster.ui.detailPosko

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.rackspira.seedisaster.R
import id.rackspira.seedisaster.data.network.entity.KebPosko
import kotlinx.android.synthetic.main.list_kebutuhan.view.*
import java.util.regex.Pattern

class KebutuhanAdapter: RecyclerView.Adapter<KebutuhanAdapter.ViewHolder>(){
    private val list = mutableListOf<KebPosko>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.list_kebutuhan, parent, false))

    override fun getItemCount() = list.size

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) = p0.bind(list[p1], idbencana = String())

    internal fun addListPosko(
        posko: List<KebPosko>,
        idbencana: String?
    ) {
//        this.list.clear()
        this.list.addAll(posko)
        notifyDataSetChanged()
    }


    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        fun bind(position: KebPosko, idbencana: String?) {
            val namaKeb = capitalize(position.namaKeb!!)

            itemView.textViewKebNama.text = namaKeb
            itemView.textViewKebJumlah.text = position.jumlahKeb +" "+position.satuanKeb
//            itemView.textviewNamaPosko.text = position.namaPosko!!
//            itemView.textviewNoTelp.text = position.telp!!
//            itemView.setOnClickListener {
//                val intent = Intent(itemView.context, DetailPoskoActivity::class.java)
//                intent.putExtra("posko", position)
//                intent.putExtra("idbencana", kib)
//                itemView.context.startActivity(intent)
//            }
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
