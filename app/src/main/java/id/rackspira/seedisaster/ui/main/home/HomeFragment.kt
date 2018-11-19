package id.rackspira.seedisaster.ui.main.home


import android.content.Intent
import android.net.Uri
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
import android.support.v4.content.ContextCompat
import android.util.Log
import android.widget.Toast
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider
import org.osmdroid.views.overlay.compass.CompassOverlay
import org.osmdroid.views.overlay.gridlines.LatLonGridlineOverlay2
import org.osmdroid.views.overlay.OverlayItem
import org.osmdroid.views.overlay.ItemizedIconOverlay
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus
import org.osmdroid.views.overlay.Marker




class HomeFragment : Fragment(), HomeView, View.OnClickListener {

    private lateinit var presenter: HomePresenter
    private lateinit var adapter: HomeAdapter
    private var isMap: Boolean = false
    private val list: MutableList<ListBencana> = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val ctx = context
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx))
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fabHome.setOnClickListener(this)

        presenter = HomePresenter(this)
        presenter.getListBencana("ALL", "10")

        adapter = HomeAdapter()
        recyclerHome.layoutManager = LinearLayoutManager(context)
        recyclerHome.adapter = adapter
    }

    override fun getListBencana(listBencana: List<ListBencana>) {
        adapter.addListBencana(listBencana)
        list.addAll(listBencana)
        setMap()
    }

    override fun onError(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
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
        }
    }

    override fun setMap() {
        mapView.setTileSource(TileSourceFactory.MAPNIK)
        mapView.setMultiTouchControls(true)
        mapView.setBuiltInZoomControls(true)

        val mapControler = mapView.controller
        mapControler.setZoom(5.0)
        var startPoint = GeoPoint(-2.28, 117.37)
        mapControler.setCenter(startPoint)

        //myLocation Overlay
        val mLocationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(context), mapView)
        mLocationOverlay.enableMyLocation()
        mapView.overlays.add(mLocationOverlay)

        //compas overlay
        val mCompassOverlay = CompassOverlay(context, InternalCompassOrientationProvider(context), mapView)
        mCompassOverlay.enableCompass()
        mapView.overlays.add(mCompassOverlay)

        Log.d("SIZE", list.size.toString())

        addMapMarker()
    }

    override fun addMapMarker() {
        val items = ArrayList<OverlayItem>()

        for (listtempat in list){
            items.add(OverlayItem(listtempat.kejadian, listtempat.keterangan, GeoPoint(listtempat.latitude, listtempat.longitude)))
        }

        val mOverlay = ItemizedOverlayWithFocus(context, items, object: ItemizedIconOverlay.OnItemGestureListener<OverlayItem>{
            override fun onItemLongPress(index: Int, item: OverlayItem?): Boolean {
                return false
            }

            override fun onItemSingleTapUp(index: Int, item: OverlayItem?): Boolean {
                var gmnIntentUri = Uri.parse("geo:"+item?.point?.latitude+","+item?.point?.longitude+"?q=" + list[index].nkab)
                val mapIntent = Intent(Intent.ACTION_VIEW, gmnIntentUri)
                mapIntent.setPackage("com.google.android.apps.maps")
                startActivity(mapIntent)
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
}
