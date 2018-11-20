package id.rackspira.seedisaster.ui.detailbencana.posko

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.rackspira.seedisaster.R
import id.rackspira.seedisaster.data.network.entity.DataPosko
import kotlinx.android.synthetic.main.list_posko.view.*

class PoskoAdapter: RecyclerView.Adapter<PoskoAdapter.ViewHolder>() {

    private val list = mutableListOf<DataPosko>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.list_posko, parent, false))

    override fun getItemCount() = list.size

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) = p0.bind(list[p1])

    internal fun addListPosko(posko: List<DataPosko>) {
        this.list.clear()
        this.list.addAll(posko)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        fun bind(position: DataPosko) {
            itemView.textviewNamaPosko.text = position.namaPosko
            itemView.textviewNoTelp.text = position.telp
        }
    }

}