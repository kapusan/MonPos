package id.rackspira.seedisaster.ui.buatposko

import android.Manifest
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import id.rackspira.seedisaster.R
import android.location.LocationManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log


class BuatPoskoActivity : AppCompatActivity(), BuatPoskoView {

    private lateinit var presenter: BuatPoskoPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buat_posko)
        presenter = BuatPoskoPresenter(this)
//        presenter.tambahPosko("1" ,"123456", "Tri wikentot", "Posko Kentot",
//            "321.0", "2133124", "129381298", "lajang","kentot")

        val permissions = arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION)
        ActivityCompat.requestPermissions(this, permissions, 0)

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val lm = getSystemService(LOCATION_SERVICE) as LocationManager
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            val location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            val longitude = location.longitude
            val latitude = location.latitude
            Log.d("KENTOT", longitude.toString())
            Log.d("KENTOTT", latitude.toString())
        }
    }

    override fun onSuccess(msg: String?) {

    }

    override fun onFailed(ms: String?) {}
}
