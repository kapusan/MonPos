package id.rackspira.seedisaster.ui.detailglobal

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import id.rackspira.seedisaster.R
import id.rackspira.seedisaster.data.network.entity.Fields
import kotlinx.android.synthetic.main.activity_detail_global.*

class DetailGlobalActivity : AppCompatActivity(), DetailGlobalView {

    private lateinit var presenter: DetailGlobalPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_global)
        presenter = DetailGlobalPresenter(this)

        val id = intent.getStringExtra("id")
        presenter.getDetailGlobal(id)

        back.setOnClickListener {
            finish()
        }
    }

    override fun getDetail(field: Fields) {
        textviewDetailBencana.text = field.description
        textviewJudul.text = field.name
    }

    override fun onError(msg: String) {
    }
}
