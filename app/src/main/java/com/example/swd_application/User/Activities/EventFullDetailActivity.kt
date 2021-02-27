package com.example.swd_application.User.Activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.webkit.URLUtil
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.example.swd_application.R
import com.example.swd_application.User.Models.EventProfileUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class EventFullDetailActivity : AppCompatActivity() {

    companion object{
        private const val TAG = "EventFullDetail"
        const val FORM_URL = "Form-Url"
    }

    private lateinit var tvEventName:TextView
    private lateinit var tvEventStartDate:TextView
    private lateinit var tvEventEndDate:TextView
    private lateinit var ivEventImage:ImageView
    private lateinit var tvEventDescription:TextView
    private lateinit var tvEventHeadOne:TextView
    private lateinit var tvEventHeadTwo:TextView
    private lateinit var tvEventFormLink:TextView

    private val firestore: FirebaseFirestore = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_full_detail)

        tvEventName = findViewById<TextView>(R.id.tv_activity_event_full_detail_list_event_name)
        tvEventStartDate = findViewById<TextView>(R.id.tv_activity_event_full_detail_list_event_start_date_value)
        tvEventEndDate = findViewById<TextView>(R.id.tv_activity_event_full_detail_list_event_end_date_value)
        ivEventImage = findViewById<ImageView>(R.id.iv_activity_event_full_detail_list_event_image)
        tvEventDescription = findViewById<TextView>(R.id.tv_activity_event_full_detail_list_event_description_value)
        tvEventHeadOne = findViewById<TextView>(R.id.tv_activity_event_full_detail_list_event_head_name_1)
        tvEventHeadTwo = findViewById<TextView>(R.id.tv_activity_event_full_detail_list_event_head_name_2)
        tvEventFormLink = findViewById<TextView>(R.id.tv_activity_event_full_detail_list_event_form_link)

        val eventProfileData: EventProfileUser? = intent.getParcelableExtra<EventProfileUser>(EventBasicDetailsActivity.EVENT_PROFILE_MODEL)
        if(eventProfileData != null){
            setEventData(eventProfileData)
        }

        tvEventFormLink.setOnClickListener {

            val intent = Intent(this, EventFormWebViewActivity::class.java)
            val eventFormUrl: String? = eventProfileData?.eventLinkUrl
            if (URLUtil.isValidUrl(eventFormUrl)) {
                intent.putExtra("url", eventFormUrl)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Invalid URL!", Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    private fun setEventData(eventProfileNew: EventProfileUser){
        tvEventName.text = eventProfileNew.eventName
        tvEventStartDate.text = eventProfileNew.eventStartDate
        tvEventEndDate.text = eventProfileNew.eventEndDate
        if(eventProfileNew.eventImageUrl != null) {
            Log.d(TAG,"${eventProfileNew.eventImageUrl}")
            setImageView(eventProfileNew.eventImageUrl!!.toUri())
        }
        tvEventDescription.text = eventProfileNew.eventDescription
        tvEventHeadOne.text = eventProfileNew.eventHeads.get(0)
        tvEventHeadTwo.text = eventProfileNew.eventHeads.get(1)
        tvEventFormLink.text = eventProfileNew.eventLinkUrl
    }

    private fun setImageView(imageUri: Uri){
        Glide.with(this)
            .load(imageUri)
            .into(ivEventImage)
    }

}