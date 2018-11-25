package id.rackspira.seedisaster.ui.main.petabencana

import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import id.rackspira.seedisaster.R
import kotlinx.android.synthetic.main.activity_detail_peta.*
import org.osmdroid.bonuspack.kml.KmlDocument
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.FolderOverlay
import org.osmdroid.views.overlay.compass.CompassOverlay
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider
import java.io.InputStream

class PetaBencanaFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_peta_bencana, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mapViewPeta.setTileSource(TileSourceFactory.MAPNIK)
        mapViewPeta.setMultiTouchControls(true)
        mapViewPeta.setBuiltInZoomControls(true)
        val mapControler = mapViewPeta.controller
        mapControler.setZoom(6.0)

        //compas overlay
        val mCompassOverlay = CompassOverlay(context, InternalCompassOrientationProvider(context), mapViewPeta)
        mCompassOverlay.enableCompass()
        mapViewPeta.overlays.add(mCompassOverlay)

        LoadKml(
            mapViewPeta,
            resources.openRawResource(R.raw.bencana_gunungapi)
        ).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
    }

    class LoadKml(val mapView: MapView, val openRawResource: InputStream) : AsyncTask<Void, Void, Void>() {

        lateinit var kmlDocument: KmlDocument

        override fun doInBackground(vararg params: Void?): Void? {
            kmlDocument = KmlDocument()
            kmlDocument.parseKMLStream(openRawResource, null)
            val kmlOverlay = kmlDocument.mKmlRoot.buildOverlay(mapView, null, null, kmlDocument) as FolderOverlay
            mapView.overlays.add(kmlOverlay)
            return null
        }

        override fun onPostExecute(result: Void?) {
            mapView.invalidate()
            val bb = kmlDocument.mKmlRoot.boundingBox
            if (bb != null)
                mapView.controller.setCenter(bb.centerWithDateLine)
            super.onPostExecute(result)
        }
    }


}
