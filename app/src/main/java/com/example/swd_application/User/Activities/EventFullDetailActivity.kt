package com.example.swd_application.User.Activities

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.example.swd_application.R
import com.example.swd_application.User.Models.EventProfileUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class EventFullDetailActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "EventFullDetail"
        const val FORM_URL = "Form-Url"
    }

    private lateinit var tvEventName: TextView
    private lateinit var tvEventStartDate: TextView
    private lateinit var tvEventEndDate: TextView
    private lateinit var ivEventImage: ImageView
    private lateinit var tvEventDescription: TextView
    private lateinit var tvEventHeadOne: TextView
    private lateinit var tvEventHeadTwo: TextView
    private lateinit var tvTotalNumberOfSeats: TextView
    private lateinit var tvNumberOfSeatsLeft: TextView

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
        tvTotalNumberOfSeats = findViewById<TextView>(R.id.tv_activity_event_full_detail_list_total_seats)
        tvNumberOfSeatsLeft = findViewById<TextView>(R.id.tv_activity_event_full_detail_list_event_seats_left)

        val eventProfileData: EventProfileUser? =
            intent.getParcelableExtra<EventProfileUser>(EventBasicDetailsActivity.EVENT_PROFILE_MODEL)
        if (eventProfileData != null) {
            setEventData(eventProfileData)
        }
    }

    private fun setEventData(eventProfileUser: EventProfileUser) {
        tvEventName.text = eventProfileUser.eventName
        tvEventStartDate.text = eventProfileUser.eventStartDate
        tvEventEndDate.text = eventProfileUser.eventEndDate
        if (eventProfileUser.eventImageUrl != null) {
            Log.d(TAG, "${eventProfileUser.eventImageUrl}")
            setImageView(eventProfileUser.eventImageUrl!!)
        }
        tvEventDescription.text = eventProfileUser.eventDescription
        tvEventHeadOne.text = eventProfileUser.eventHeads.get(0)
        tvEventHeadTwo.text = eventProfileUser.eventHeads.get(1)
        tvTotalNumberOfSeats.text = getString(R.string.total_seats_in_event,eventProfileUser.eventTotalNumberOfSeats.toString())
        val numberOfSeatsLeft: Int = eventProfileUser.eventTotalNumberOfSeats - eventProfileUser.eventNumberOfSeatsFilled
        tvNumberOfSeatsLeft.text = getString(R.string.seats_left_in_event,numberOfSeatsLeft.toString())
    }

    private fun setImageView(imageUrl: String) {
        Glide.with(this)
            .load(imageUrl)
            .into(ivEventImage)
    }
}