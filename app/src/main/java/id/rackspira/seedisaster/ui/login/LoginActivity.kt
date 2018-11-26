package id.rackspira.seedisaster.ui.login

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import id.rackspira.seedisaster.R
import id.rackspira.seedisaster.ui.login.verifikasikode.VerifikasiActivity
import id.rackspira.seedisaster.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (mAuth.currentUser != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        buttonLogin.setOnClickListener {
            val intent = Intent(this, VerifikasiActivity::class.java)
            intent.putExtra("no", "+62" +edittextTelp.text.toString())
            startActivity(intent)
            finish()
        }
    }
}
