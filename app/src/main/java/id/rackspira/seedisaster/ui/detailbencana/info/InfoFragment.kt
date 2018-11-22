package id.rackspira.seedisaster.ui.detailbencana.info


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import id.rackspira.seedisaster.R
import id.rackspira.seedisaster.data.network.entity.ListBencana
import kotlinx.android.synthetic.main.fragment_info.*
import id.rackspira.seedisaster.ui.detailPosko.DetailPoskoActivity
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.compass.CompassOverlay
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider
import org.osmdroid.views.overlay.Marker



class InfoFragment : Fragment() {

    private var list: ListBencana? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_info, container, false)

    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageView3.setOnClickListener {
            context?.startActivity(Intent(context, DetailPoskoActivity::class.java))
        }

        list = arguments!!.getParcelable("posisi")

        textViewLokasiInfo.text = list?.nkab + ", " + list?.nprop
        tvHilangJumlah.text = list?.hilang
        tvMeninggalJumlah.text = list?.meninggal
        tvTerlukaJumlah.text = list?.terluka
        tvMenderitaJumlah.text = list?.menderita
        textViewJumlahBerat.text = list?.rumahRusakBerat
        textViewJumlahRingan.text = list?.rumahRusakRingan
        textViewJumlahSedang.text = list?.rumahRusakSedang
        textViewKeterangan.text = list?.keterangan

        maps.setTileSource(TileSourceFactory.MAPNIK)
        maps.setMultiTouchControls(true)
        maps.setBuiltInZoomControls(true)

        val lat = list?.latitude
        val long = list?.longitude

        val mapControler = maps.controller
        mapControler.setZoom(15.0)
        var startPoint = GeoPoint(lat!!, long!!)
        mapControler.setCenter(startPoint)

        //compas overlay
        val mCompassOverlay = CompassOverlay(context, InternalCompassOrientationProvider(context), maps)
        mCompassOverlay.enableCompass()
        maps.overlays.add(mCompassOverlay)

        val startMarker = Marker(maps)
        startMarker.position = startPoint
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        maps.overlays.add(startMarker)

        maps.invalidate()

        startMarker.icon = ContextCompat.getDrawable(context!!, R.drawable.ic_lokasi_detail)
        startMarker.title = list?.kejadian

    }
}
