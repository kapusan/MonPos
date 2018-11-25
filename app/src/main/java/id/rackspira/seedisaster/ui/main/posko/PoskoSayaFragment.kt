package id.rackspira.seedisaster.ui.main.posko


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth

import id.rackspira.seedisaster.R
import id.rackspira.seedisaster.data.network.entity.DataPosko
import id.rackspira.seedisaster.ui.detailbencana.posko.PoskoAdapter
import id.rackspira.seedisaster.ui.detailbencana.posko.PoskoPresenter
import id.rackspira.seedisaster.ui.detailbencana.posko.PoskoView
import kotlinx.android.synthetic.main.fragment_posko_saya.*

class PoskoSayaFragment : Fragment(), PoskoView {

    private lateinit var adapter: PoskoAdapter
    private lateinit var presenter: PoskoPresenter
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_posko_saya, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = PoskoAdapter()
        presenter = PoskoPresenter(this)
        firebaseAuth = FirebaseAuth.getInstance()
        presenter.getDataPoskoByUid(firebaseAuth.currentUser!!.uid)

        recyclerPoskoSaya.layoutManager = LinearLayoutManager(context)
        recyclerPoskoSaya.setHasFixedSize(true)
        recyclerPoskoSaya.adapter = adapter
    }

    override fun onSuccess(msg: String?) {

    }

    override fun onFailed(ms: String?) {}

    override fun onSuccessUpdate(msg: String?) {}

    override fun onFailedUpdate(msg: String?) {}

    override fun getDataPosko(dataPosko: List<DataPosko>) {
        adapter.addListPosko(dataPosko)
    }
}
