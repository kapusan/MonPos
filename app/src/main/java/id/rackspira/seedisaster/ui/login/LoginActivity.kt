package id.rackspira.seedisaster.ui.login

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import id.rackspira.seedisaster.R
import id.rackspira.seedisaster.ui.login.verifikasikode.VerifikasiActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        buttonLogin.setOnClickListener {
            val intent = Intent(this, VerifikasiActivity::class.java)
            intent.putExtra("no", edittextTelp.text.toString())
            startActivity(intent)
        }
    }
}
