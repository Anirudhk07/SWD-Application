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


        // TODO: Temparory Arrangement
        val btn_skip = findViewById<Button>(R.id.btn_admin_skip)

        btn_skip.setOnClickListener {
            val intent = Intent(this, EventAdminActivity::class.java)
            startActivity(intent)
        }

    }
}