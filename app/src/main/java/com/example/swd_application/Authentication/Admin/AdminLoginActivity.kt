package com.example.swd_application.Authentication.Admin

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.swd_application.Admin.Activities.EventAdminActivity
import com.example.swd_application.R
import com.google.firebase.auth.FirebaseAuth

class AdminLoginActivity : AppCompatActivity() {
    private var email: EditText? = null
    private var password: EditText? = null
    private var login: Button? = null
    private var mAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_login)
        email = findViewById(R.id.tv_admin_login_email_id)
        password = findViewById(R.id.tv_admin_login_password)
        login = findViewById(R.id.btn_admin_login)
        mAuth = FirebaseAuth.getInstance()
        login?.setOnClickListener {
            val txt_email = email?.text.toString()
            val txt_password = password?.text.toString()
            if (TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)) {
                Toast.makeText(this@AdminLoginActivity, "Empty Credentials!", Toast.LENGTH_SHORT).show()
            } else {
                loginUser(txt_email, txt_password)
            }
        }
        val skip: Button = findViewById(R.id.btn_skip)
        skip.setOnClickListener {
            val intent = Intent(this@AdminLoginActivity, AdminHomeActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loginUser(email: String, password: String) {
        mAuth!!.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val intent = Intent(this, AdminHomeActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
                finish()
            }
        }.addOnFailureListener { e -> Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show() }
    }
}