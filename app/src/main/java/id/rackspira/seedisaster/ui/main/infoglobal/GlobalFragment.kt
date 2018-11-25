package id.rackspira.seedisaster.ui.main.infoglobal


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import id.rackspira.seedisaster.R
import id.rackspira.seedisaster.data.network.entity.ListNewsBencana
import kotlinx.android.synthetic.main.fragment_global.*

class GlobalFragment : Fragment(), InfoGlobalView {

    private val list = mutableListOf<ListNewsBencana>()
    private lateinit var adapter: GlobalAdapter
    private lateinit var presenter: InfoGlobalPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_global, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = InfoGlobalPresenter(this)
        adapter = GlobalAdapter()
        presenter.getListGlobal("15")

        recycleGlobal.layoutManager = LinearLayoutManager(context)
        recycleGlobal.adapter = adapter
    }

    override fun getListBencana(listBencana: List<ListNewsBencana>) {
        adapter.addListGlobal(listBencana)
    }

    override fun onError(msg: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
