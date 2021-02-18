package com.example.swd_application.Authentication.Admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.swd_application.Admin.EventAdminActivity
import com.example.swd_application.R

class AdminHomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_home)

        val skip: Button = findViewById<Button>(R.id.btn_skip)
        skip.setOnClickListener {
            val intent = Intent(this@AdminHomeActivity, EventAdminActivity::class.java)
            startActivity(intent)
        }
    }
}