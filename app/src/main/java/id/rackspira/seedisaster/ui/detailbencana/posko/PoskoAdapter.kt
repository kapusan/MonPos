package id.rackspira.seedisaster.ui.detailbencana.posko

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.rackspira.seedisaster.R
import id.rackspira.seedisaster.data.network.entity.DataPosko
import id.rackspira.seedisaster.ui.detailPosko.DetailPoskoActivity
import kotlinx.android.synthetic.main.list_posko.view.*
import java.util.regex.Pattern

class PoskoAdapter: RecyclerView.Adapter<PoskoAdapter.ViewHolder>() {

    private val list = mutableListOf<DataPosko>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.list_posko, parent, false))

    override fun getItemCount() = list.size

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) = p0.bind(list[p1])

    internal fun addListPosko(
        posko: List<DataPosko>
    ) {
        this.list.clear()
        this.list.addAll(posko)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        fun bind(position: DataPosko) {
            val namaPosko = capitalize(position.namaPosko!!)

            itemView.textviewNamaPosko.text = namaPosko
            itemView.textviewNoTelp.text = position.telp!!
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailPoskoActivity::class.java)
                intent.putExtra("posko", position)
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