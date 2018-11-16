package id.rackspira.seedisaster.ui.main.home

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.rackspira.seedisaster.R
import id.rackspira.seedisaster.data.network.entity.ListBencana
import kotlinx.android.synthetic.main.list_bencana.view.*

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
            itemView.textviewJudul.text = position.kejadian + " " + position.nprop + " " + position.nkab
        }
    }
}