package id.rackspira.seedisaster.ui.detailbencana.posko

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
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
import id.rackspira.seedisaster.ui.detailPosko.DetailPoskoActivity
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_info.*
import org.osmdroid.views.overlay.ItemizedIconOverlay
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus
import org.osmdroid.views.overlay.Marker
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
        progressBarFragmentPosko.visibility = View.GONE
        poskoAdapter.addListPosko(dataPosko)
        if (dataPosko.isNotEmpty()) {
            imageviewPoskoKosong.visibility = View.GONE
            textviewBlmAda.visibility = View.GONE
            textviewBlmAda2.visibility = View.GONE
            fabMaps.visibility = View.VISIBLE
        } else {
            imageviewPoskoKosong.visibility = View.VISIBLE
            textviewBlmAda.visibility = View.VISIBLE
            textviewBlmAda2.visibility = View.VISIBLE
            fabMaps.visibility = View.GONE
        }
        for (data in dataPosko) {
            if (!data.lat.equals("null") && !data.long.equals("null")) {
                list.add(data)
            }
        }
        setMap()
    }

    fun setMap() {

        if (listBencana.latitude != null && listBencana.longitude != null) {
            val mapControler = mapViewPosko.controller
            mapControler.setZoom(6.0)
            var startPoint = GeoPoint(listBencana.latitude!!, listBencana.longitude!!)
            mapControler.setCenter(startPoint)

            val startMarker = Marker(mapViewPosko)
            startMarker.position = startPoint
            startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            mapViewPosko.overlays.add(startMarker)

            mapViewPosko.invalidate()

            startMarker.icon = ContextCompat.getDrawable(context!!, R.drawable.ic_lokasi_detail)
            startMarker.title = listBencana.kejadian
            startMarker.snippet = listBencana.keterangan

        }

        //compas overlay
        val mCompassOverlay = CompassOverlay(context, InternalCompassOrientationProvider(context), mapViewPosko)
        mCompassOverlay.enableCompass()
        mapViewPosko.overlays.add(mCompassOverlay)

        addMapMarker()
    }

    fun addMapMarker() {
        val items = ArrayList<OverlayItem>()

        for (listtempat in list) {
            items.add(
                OverlayItem(
                    listtempat.namaPosko,
                    listtempat.telp,
                    GeoPoint(listtempat.lat!!.toDouble(), listtempat.long!!.toDouble())
                )
            )
        }

        val mOverlay =
            ItemizedOverlayWithFocus(context, items, object : ItemizedIconOverlay.OnItemGestureListener<OverlayItem> {
                override fun onItemLongPress(index: Int, item: OverlayItem?): Boolean {
                    return false
                }

                override fun onItemSingleTapUp(index: Int, item: OverlayItem?): Boolean {
                    showDialog(item?.title, item?.snippet, item, index)
                    return true
                }

            })

        mOverlay.setFocusItemsOnTap(true)
        mapViewPosko.overlays.add(mOverlay)
    }

    override fun onPause() {
        super.onPause()
        mapViewPosko.onPause()
    }

    override fun onResume() {
        super.onResume()
        mapViewPosko.onResume()
        presenter.getDataPosko(listBencana.kib!!)
    }

    fun showDialog(title: String?, body: String?, item: OverlayItem?, index: Int) {
        val dialog = Dialog(context!!)
        val data = list[index]
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.popup_map)
        dialog.setCancelable(true)

        dialog.map_popup_header.text = title
        dialog.map_popup_body.text = body

        dialog.textview_detail.setOnClickListener {
            val intent = Intent(context, DetailPoskoActivity::class.java)
            intent.putExtra("posko", data)
            context?.startActivity(intent)
        }

        dialog.textview_navigasi.setOnClickListener {
            val gmnIntentUri =
                Uri.parse("geo:" + item?.point?.latitude + "," + item?.point?.longitude + "?q=" + list[index].kab)
            val mapIntent = Intent(Intent.ACTION_VIEW, gmnIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            context?.startActivity(mapIntent)
        }

        dialog.show()

    }

}

