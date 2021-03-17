package com.example.swd_application.Admin.EventModels

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

