package id.rackspira.seedisaster.ui.login.verifikasikode

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import id.rackspira.seedisaster.R
import id.rackspira.seedisaster.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_verifikasi.*
import java.util.concurrent.TimeUnit

class VerifikasiActivity : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null
    private var mResendToken: PhoneAuthProvider.ForceResendingToken? = null
    private var mVerificationId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verifikasi)
        mAuth = FirebaseAuth.getInstance()
        val mCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {

                signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {

                if (e is FirebaseAuthInvalidCredentialsException) {
                } else if (e is FirebaseTooManyRequestsException) {
                }

            }

            override fun onCodeSent(verificationId: String?,
                                    token: PhoneAuthProvider.ForceResendingToken) {

                mVerificationId = verificationId
                mResendToken = token
            }
        }

        val telp = intent.getStringExtra("no")

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            telp,
            60,
            TimeUnit.SECONDS,
            this,
            mCallbacks)

        buttonOke.setOnClickListener {
            verifyPhoneNumberWithCode(mVerificationId!!, edittextKode.text.toString())
        }

    }

    fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        mAuth?.signInWithCredential(credential)?.addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                val user = task.result?.user
                startActivity(Intent(this, MainActivity::class.java))
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
            30,
            TimeUnit.SECONDS,
            this,
            callback,
            token
        )
    }

}
