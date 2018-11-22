package id.rackspira.seedisaster.ui.main.home

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.rackspira.seedisaster.R
import id.rackspira.seedisaster.data.network.entity.ListBencana
import id.rackspira.seedisaster.ui.detailbencana.DetailbencanaActivity
import kotlinx.android.synthetic.main.list_bencana.view.*
import java.util.regex.Pattern

class HomeAdapter: RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    private val list = mutableListOf<ListBencana>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(LayoutInflater.from(parent.context)
        .inflate(R.layout.list_bencana, parent, false))

    override fun getItemCount() = list.size

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) = p0.bind(list[p1])

    internal fun addListBencana(bencana: List<ListBencana>) {
        this.list.addAll(bencana)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        fun bind(position: ListBencana) {
            val kejadian = capitalize(position.kejadian!!)
            val nprop = capitalize(position.nprop!!)
            val nkab = capitalize(position.nkab!!)

            itemView.textviewJudul.text = kejadian
            itemView.textViewLokasi.text = nprop +", "+nkab
            itemView.setOnClickListener {
//                itemView.context.startActivity(Intent(itemView.context, DetailbencanaActivity::class.java))
                val intent = Intent(itemView.context, DetailbencanaActivity::class.java)
                intent.putExtra("posisi", position)
                itemView.context.startActivity(intent)
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