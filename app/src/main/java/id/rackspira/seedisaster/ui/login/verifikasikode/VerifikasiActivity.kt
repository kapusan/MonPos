package id.rackspira.seedisaster.ui.login.verifikasikode

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import id.rackspira.seedisaster.R
import id.rackspira.seedisaster.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_verifikasi.*
import java.util.concurrent.TimeUnit
import com.google.firebase.auth.FirebaseAuth
import id.rackspira.seedisaster.ui.isiprofil.IsiProfilActivity
import kotlinx.android.synthetic.main.activity_buat_posko.*


class VerifikasiActivity : AppCompatActivity() {

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private var mResendToken: PhoneAuthProvider.ForceResendingToken? = null
    private var mVerificationId: String? = null
    private var telp: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verifikasi)

        telp = intent.getStringExtra("no")

        val mCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {

                signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {

                if (e is FirebaseAuthInvalidCredentialsException) {
                } else if (e is FirebaseTooManyRequestsException) {
                }

            }

            override fun onCodeSent(
                verificationId: String?,
                token: PhoneAuthProvider.ForceResendingToken
            ) {

                mVerificationId = verificationId
                mResendToken = token
            }
        }

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            telp!!,
            60,
            TimeUnit.SECONDS,
            this,
            mCallbacks
        )

        buttonLogin.setOnClickListener {
            if (edittextKode.text.toString().isEmpty()) run {edittextKode.error = "Data Kosong" }
            else {
                progressbarVerifikasi.visibility = View.VISIBLE
                verifyPhoneNumberWithCode(mVerificationId!!, edittextKode.text.toString())
            }
        }
    }

    fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        mAuth.signInWithCredential(credential).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                val intent = Intent(this, IsiProfilActivity::class.java)
                intent.putExtra("no", telp)
                startActivity(intent)
            } else {
                if (task.exception is FirebaseAuthInvalidCredentialsException) {

                }
            }
        }
    }

    private fun verifyPhoneNumberWithCode(verificationId: String, code: String) {
        val credential = PhoneAuthProvider.getCredential(verificationId, code)
        signInWithPhoneAuthCredential(credential)
    }

    private fun resendVerificationCode(
        phoneNumber: String,
        token: PhoneAuthProvider.ForceResendingToken,
        callback: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    ) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            phoneNumber,
            60,
            TimeUnit.SECONDS,
            this,
            callback,
            token
        )
    }

}
