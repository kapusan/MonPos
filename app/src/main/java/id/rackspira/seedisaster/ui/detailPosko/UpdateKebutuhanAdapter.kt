package id.rackspira.seedisaster.ui.detailPosko

import android.os.Handler
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import com.google.firebase.database.FirebaseDatabase
import id.rackspira.seedisaster.R
import id.rackspira.seedisaster.data.network.entity.DataPosko
import id.rackspira.seedisaster.data.network.entity.KebPosko
import kotlinx.android.synthetic.main.list_update_kebutuhan.view.*
import java.util.regex.Pattern
import android.widget.TextView



class UpdateKebutuhanAdapter: RecyclerView.Adapter<UpdateKebutuhanAdapter.ViewHolder>(){
    private val list = mutableListOf<KebPosko>()
    private lateinit var list2: DataPosko

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.list_update_kebutuhan, parent, false))

    override fun getItemCount() = list.size

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) = p0.bind(list[p1], list2 )

    internal fun addListPosko(
        posko: List<KebPosko>,
        list2: DataPosko
    ) {
        this.list.clear()
        this.list.addAll(posko)
        this.list2 = list2
        notifyDataSetChanged()
    }


    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        fun bind(position: KebPosko, list2:DataPosko) {
            val namaKeb = capitalize(position.namaKeb!!)

            itemView.textViewNamaBarang.text = namaKeb

            itemView.editTextJumlahKebutuhan.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

                }

                override fun afterTextChanged(s: Editable) {
                    if (s.isEmpty()) {
                    } else {
                        Handler().postDelayed({
                            pushUpdate(position, list2)
                        }, 1000)
                    }
                }
            })


        }

        private fun pushUpdate(position: KebPosko, list2: DataPosko) {
            val jumlahKebutuhan = itemView.editTextJumlahKebutuhan.text.toString()
            val data = KebPosko(position.idKeb.toString(),position.namaKeb.toString(),jumlahKebutuhan, position.satuanKeb.toString())
            val dataRef = FirebaseDatabase.getInstance().reference
            val ref = dataRef.child("Kebutuhan").child(list2.idBencana.toString()).child(list2.idPosko.toString())
                .child("Sandang").child(position.idKeb.toString())
            ref.setValue(data).addOnSuccessListener {
                Log.d("UpdateKEbutuhan", "Sukses")
            }.addOnFailureListener {
                Log.d("UpdateKEbutuhan", "Gagal")
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
