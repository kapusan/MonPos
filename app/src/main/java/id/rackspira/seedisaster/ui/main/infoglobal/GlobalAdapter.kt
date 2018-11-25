package id.rackspira.seedisaster.ui.main.infoglobal

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.rackspira.seedisaster.R
import id.rackspira.seedisaster.data.network.entity.ListNewsBencana
import id.rackspira.seedisaster.ui.detailglobal.DetailGlobalActivity
import kotlinx.android.synthetic.main.list_global.view.*

class GlobalAdapter(): RecyclerView.Adapter<GlobalAdapter.ViewHolder>() {

    private val list = mutableListOf<ListNewsBencana>()

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int) = ViewHolder(
        LayoutInflater.from(p0.context).inflate(R.layout.list_global, p0, false)
    )

    override fun getItemCount() = list.size

    override fun onBindViewHolder(p0: GlobalAdapter.ViewHolder, p1: Int) = p0.onBind(list[p1])

    internal fun addListGlobal(global: List<ListNewsBencana>) {
        this.list.addAll(global)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        fun onBind(position: ListNewsBencana) {
            val judul = position.fields?.name
            itemView.textviewTitleGlobal.text = judul

            itemView.textviewTitleGlobal.setOnClickListener {
                val intent = Intent(itemView.context, DetailGlobalActivity::class.java)
                intent.putExtra("id", position.id)
                itemView.context.startActivity(intent)
            }

        }
    }

}
