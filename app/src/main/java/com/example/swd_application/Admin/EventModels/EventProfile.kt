package com.example.swd_application.Admin.EventModels

data class EventProfile(
    val EventConductedYear: String,
    val EventDescription: String,
    val EventEndDate: String?,
    val EventFlagship: Boolean,
    val EventHeads: List<String>,
    val EventImageUrl: String?,
    val EventLinkUrl: String,
    val EventName: String,
    val EventTotalNumberOfSeats: Int,
    val EventNumberOfSeatsFilled: Int,
    val EventStartDate: String?
)

