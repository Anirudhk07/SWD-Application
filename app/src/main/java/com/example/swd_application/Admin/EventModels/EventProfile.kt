package com.example.swd_application.Admin

import android.os.Parcel
import android.os.Parcelable

data class EventProfile(
    val EventConductedYear:Int,
    val EventDescription: String,
    val EventEndDate: String?,
    val EventFlagship: Boolean,
    val EventHeads: List<String>,
    val EventImageUrl: String?,
    val EventLinkUrl: String,
    val EventName: String,
    val EventStartDate: String?
)

