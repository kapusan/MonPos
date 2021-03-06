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
import com.google.android.gms.location.*
import id.rackspira.seedisaster.R
import com.google.firebase.auth.FirebaseAuth
import id.rackspira.seedisaster.data.network.entity.DataUser
import id.rackspira.seedisaster.data.network.entity.ListBencana
import kotlinx.android.synthetic.main.activity_buat_posko.*


class BuatPoskoActivity : AppCompatActivity(), BuatPoskoView {

    private lateinit var presenter: BuatPoskoPresenter
    private lateinit var mAuth: FirebaseAuth
    private var latitude: Double? = null
    private var longitude: Double? = null
    private var telefon: String? = null
    private var namaPenangungjawab: String? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private val locationRequest = LocationRequest()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buat_posko)
        presenter = BuatPoskoPresenter(this)
        mAuth = FirebaseAuth.getInstance()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                for (location in locationResult.locations) {
                    latitude = location.latitude
                    longitude = location.longitude
                }
            }
        }

        presenter.getUser()

        val dataPosko = intent.getParcelableExtra<ListBencana>("data")
        Log.d("idbencana", dataPosko.kib)
        val manager = getSystemService(LOCATION_SERVICE) as LocationManager
        val permissions = arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION)
        ActivityCompat.requestPermissions(this, permissions, 0)

//        if (ContextCompat.checkSelfPermission(
//                this,
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) == PackageManager.PERMISSION_GRANTED
//        ) {
//            fusedLocationClient.lastLocation
//                .addOnSuccessListener { location: Location? ->
//                    latitude = location?.latitude
//                    longitude = location?.longitude
//                }
//        }

        backBuatPosko.setOnClickListener {
            finish()
        }

        buttonSimpan.setOnClickListener {
            if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                Toast.makeText(this, "Mohon Hidupkan Lokasi Anda", Toast.LENGTH_LONG).show()
            } else if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                if (edittextNamaPosko.text!!.isEmpty() || edittextDesa.text!!.isEmpty() || edittextKec.text!!.isEmpty() || textviewKab.text!!.isEmpty() || editTextProv.text!!.isEmpty()) {
                    Toast.makeText(this, "Inputan belum lengkap", Toast.LENGTH_LONG).show()
                } else {
                    presenter.tambahPosko(
                        dataPosko.kib.toString(),
                        mAuth.currentUser!!.uid,
                        edittextNamaPosko.text.toString(),
                        latitude.toString(),
                        longitude.toString(),
                        telefon.toString(),
                        edittextDesa.text.toString(),
                        edittextKec.text.toString(),
                        textviewKab.text.toString(),
                        editTextProv.text.toString(),
                        namaPenangungjawab.toString()
                    )
                    finish()
                }

            }
        }
    }

    override fun onSuccess(msg: String?) {

    }

    override fun onFailed(ms: String?) {}

    override fun getDataUser(dataUser: DataUser) {
        Log.d("telefonAnda", dataUser.noTp.toString())
        telefon = dataUser.noTp.toString()
        namaPenangungjawab = dataUser.nama.toString()

    }

    override fun onResume() {
        super.onResume()
        startLocationUpdates()
    }

    private fun startLocationUpdates() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                null /* Looper */
            )

        }
    }

}
