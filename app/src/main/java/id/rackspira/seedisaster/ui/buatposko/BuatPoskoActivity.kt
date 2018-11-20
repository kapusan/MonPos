package id.rackspira.seedisaster.ui.buatposko

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import id.rackspira.seedisaster.R

class BuatPoskoActivity : AppCompatActivity(), BuatPoskoView {

    private lateinit var presenter: BuatPoskoPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buat_posko)
        presenter = BuatPoskoPresenter(this)
        presenter.tambahPosko("123456", "Tri wikentot", "Posko Kentot",
            "321.0", "2133124", "129381298", "lajang","kentot")
    }

    override fun onSuccess(msg: String?) {

    }

    override fun onFailed(ms: String?) {}
}
