package id.rackspira.seedisaster.ui.buatposko

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.widget.Toast
import id.rackspira.seedisaster.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import id.rackspira.seedisaster.data.network.entity.ListBencana
import kotlinx.android.synthetic.main.activity_buat_posko.*


class BuatPoskoActivity : AppCompatActivity(), BuatPoskoView {

    private lateinit var presenter: BuatPoskoPresenter
    private lateinit var mAuth: FirebaseAuth
    private var latitude: Double? = null
    private var longitude: Double? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buat_posko)
        presenter = BuatPoskoPresenter(this)
        mAuth = FirebaseAuth.getInstance()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        val dataPosko = intent.getParcelableExtra<ListBencana>("data")
        val manager = getSystemService(LOCATION_SERVICE) as LocationManager
        val permissions = arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION)
        ActivityCompat.requestPermissions(this, permissions, 0)

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    latitude = location?.latitude
                    longitude = location?.longitude
                }
        }

        buttonSimpan.setOnClickListener {
            if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                Toast.makeText(this, "KENTOT KAMU YA", Toast.LENGTH_SHORT).show()
            } else if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                fusedLocationClient.lastLocation
                    .addOnSuccessListener { location: Location? ->
                        latitude = location?.latitude
                        longitude = location?.longitude
                        presenter.tambahPosko(
                            dataPosko.kib.toString(), "kosong", edittextNamaPosko.text.toString(),
                            latitude.toString(), longitude.toString(), "2786878", "887686"
                        )
                    }
            } else {
                ActivityCompat.requestPermissions(this, permissions, 0)
            }
        }

    }

    override fun onSuccess(msg: String?) {

    }

    override fun onFailed(ms: String?) {}
}
