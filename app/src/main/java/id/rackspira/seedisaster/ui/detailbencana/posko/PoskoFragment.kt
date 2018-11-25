package id.rackspira.seedisaster.ui.detailbencana.posko

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window

import id.rackspira.seedisaster.R
import id.rackspira.seedisaster.data.network.entity.DataPosko
import id.rackspira.seedisaster.data.network.entity.ListBencana
import id.rackspira.seedisaster.ui.buatposko.BuatPoskoActivity
import kotlinx.android.synthetic.main.fragment_posko.*
import kotlinx.android.synthetic.main.popup_map.*
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.ItemizedIconOverlay
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus
import org.osmdroid.views.overlay.Marker
import id.rackspira.seedisaster.ui.detailPosko.DetailPoskoActivity
import org.osmdroid.views.overlay.OverlayItem
import org.osmdroid.views.overlay.compass.CompassOverlay
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider

class PoskoFragment : Fragment(), PoskoView {

    private lateinit var poskoAdapter: PoskoAdapter
    private lateinit var presenter: PoskoPresenter
    private val list: MutableList<DataPosko> = mutableListOf()
    private var isMapOpen: Boolean = false
    private lateinit var listBencana: ListBencana

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_posko, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapViewPosko.setTileSource(TileSourceFactory.MAPNIK)
        mapViewPosko.setMultiTouchControls(true)
        mapViewPosko.setBuiltInZoomControls(true)
        listBencana = arguments!!.getParcelable("posisi") as ListBencana
        fabTambahPosko.setOnClickListener {
            val intent = Intent(context, BuatPoskoActivity::class.java)
            intent.putExtra("data", listBencana)
            context!!.startActivity(intent)
        }

        fabMaps.setOnClickListener {
            if (isMapOpen) {
                recyclerviewPosko.visibility = View.VISIBLE
                mapViewPosko.visibility = View.GONE
                isMapOpen = false
            } else {
                recyclerviewPosko.visibility = View.GONE
                mapViewPosko.visibility = View.VISIBLE
                isMapOpen = true
            }
        }

        presenter = PoskoPresenter(this)
        presenter.getDataPosko(listBencana.kib!!)
        poskoAdapter = PoskoAdapter()
        recyclerviewPosko.layoutManager = LinearLayoutManager(context)
        recyclerviewPosko.adapter = poskoAdapter
    }

    override fun onSuccess(msg: String?) {}

    override fun onFailed(ms: String?) {}

    override fun onSuccessUpdate(msg: String?) {}

    override fun onFailedUpdate(msg: String?) {}

    override fun getDataPosko(dataPosko: List<DataPosko>) {
        poskoAdapter.addListPosko(dataPosko)
        if (dataPosko.isNotEmpty()) {
            imageviewPoskoKosong.visibility = View.GONE
            textviewBlmAda.visibility = View.GONE
            textviewBlmAda2.visibility = View.GONE
        } else {
            imageviewPoskoKosong.visibility = View.VISIBLE
            textviewBlmAda.visibility = View.VISIBLE
            textviewBlmAda2.visibility = View.VISIBLE
        }
    }
}
