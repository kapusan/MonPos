package id.rackspira.seedisaster.ui.main.home


import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import id.rackspira.seedisaster.R
import id.rackspira.seedisaster.data.network.entity.ListBencana
import kotlinx.android.synthetic.main.fragment_home.*
import android.preference.PreferenceManager
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.*
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider
import org.osmdroid.views.overlay.compass.CompassOverlay
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.view.Window
import android.widget.AdapterView
import android.widget.ArrayAdapter
import id.rackspira.seedisaster.data.network.entity.ListJenisBencana
import kotlinx.android.synthetic.main.popup_map.*
import id.rackspira.seedisaster.ui.detailbencana.DetailbencanaActivity
class HomeFragment : Fragment(), HomeView, View.OnClickListener, AdapterView.OnItemSelectedListener {

    private lateinit var presenter: HomePresenter
    private lateinit var adapter: HomeAdapter
    private var isMap: Boolean = false
    private val list: MutableList<ListBencana> = mutableListOf()
    private val listJnsBencana = arrayOf("Banjir", "Tanah Longsor",
        "Banjir dan Tanah Longsor", "Gelombang Pasang", "Puting Beliung", "Kekeringan",
        "Kebakaran Hutan & Lahan", "Gempa Bumi", "Tsunami", "Gempa Bumi & Tsunami", "Letusan Gunung Api", "Kebakaran")
    private val listJenis: MutableList<ListJenisBencana> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val ctx = context
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx))
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fabHome.setOnClickListener(this)
        buttonRetryHome.setOnClickListener(this)

        presenter = HomePresenter(this)
        presenter.getJenisBencana()
        adapter = HomeAdapter()
        recyclerHome.layoutManager = LinearLayoutManager(context)
        recyclerHome.adapter = adapter

        mapView.setTileSource(TileSourceFactory.MAPNIK)
        mapView.setMultiTouchControls(true)
        mapView.setBuiltInZoomControls(true)

        val aa = ArrayAdapter(context, android.R.layout.simple_spinner_item, listJnsBencana)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerHome.adapter = aa
        spinnerHome.onItemSelectedListener = this
    }


    override fun onNothingSelected(parent: AdapterView<*>?) {}

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (listJenis.size > 0) {
            presenter.getListBencana(listJenis[position].kode!!, "15")
            textviewKosong.visibility = View.GONE
        }
    }

    override fun getListBencana(listBencana: List<ListBencana>) {
        progressbarHome.visibility = View.GONE
        imageView6.visibility = View.GONE
        adapter.addListBencana(listBencana)
        list.addAll(listBencana)
        setMap()

        if (listBencana.isNotEmpty()) {
            textviewKosong.visibility = View.GONE
        } else {
            textviewKosong.visibility = View.VISIBLE
        }

    }

    override fun onError(msg: String?) {
//        buttonRetryHome.visibility = View.VISIBLE
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.fabHome -> {
                if (!isMap) {
                    recyclerHome.visibility = View.GONE
                    mapView.visibility = View.VISIBLE
                    isMap = true
                } else {
                    recyclerHome.visibility = View.VISIBLE
                    mapView.visibility = View.GONE
                    isMap = false
                }
            }
            R.id.buttonRetryHome -> {
                activity?.recreate()
            }
        }
    }

    fun setMap() {
        val mapControler = mapView.controller
        mapControler.setZoom(6.0)
        var startPoint = GeoPoint(-2.28, 117.37)
        mapControler.setCenter(startPoint)

        //compas overlay
        val mCompassOverlay = CompassOverlay(context, InternalCompassOrientationProvider(context), mapView)
        mCompassOverlay.enableCompass()
        mapView.overlays.add(mCompassOverlay)

        addMapMarker()
    }

    override fun addMapMarker() {
        val items = ArrayList<OverlayItem>()

        for (listtempat in list) {
            items.add(
                OverlayItem(
                    listtempat.kejadian,
                    listtempat.keterangan,
                    GeoPoint(listtempat.latitude!!, listtempat.longitude!!)
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
        mapView.overlays.add(mOverlay)
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    fun showDialog(title: String?, body: String?, item: OverlayItem?, index: Int) {
        val dialog = Dialog(context!!)
        val data = list[index]
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
        dialog.setContentView(R.layout.popup_map)
        dialog.setCancelable(true)

        dialog.map_popup_header.text = title
        dialog.map_popup_body.text = body

        dialog.textview_detail.setOnClickListener {
            val intent = Intent(context, DetailbencanaActivity::class.java)
            intent.putExtra("posisi", data)
            context?.startActivity(intent)
        }

        dialog.textview_navigasi.setOnClickListener {
            var gmnIntentUri =
                Uri.parse("geo:" + item?.point?.latitude + "," + item?.point?.longitude + "?q=" + list[index].nkab)
            val mapIntent = Intent(Intent.ACTION_VIEW, gmnIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            context?.startActivity(mapIntent)

        }

        dialog.show()

    }

    override fun getListJenis(listJenisBencana: List<ListJenisBencana>) {
        listJenis.addAll(listJenisBencana)
        presenter.getListBencana(listJenis[0].kode!!, "15")
    }
}
