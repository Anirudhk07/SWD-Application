package com.example.swd_application.Authentication.Client

import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.swd_application.R
import com.google.android.gms.auth.api.signin.GoogleSignIn


class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val iv_profile_image = findViewById<ImageView>(R.id.iv_profile_image)
        val tv_profile_first_name = findViewById<TextView>(R.id.tv_profile_first_name)
        val tv_profile_email = findViewById<TextView>(R.id.tv_profile_email)


        val acct = GoogleSignIn.getLastSignedInAccount(this)
        if (acct != null) {
            val personName = acct.displayName
            val personEmail = acct.email
            val personPhoto: Uri? = acct.photoUrl

            tv_profile_first_name.text = personName
            tv_profile_email.text = personEmail
            tv_profile_first_name.text = personName

            Glide.with(this).load(personPhoto).into(iv_profile_image);
        }
    }
}