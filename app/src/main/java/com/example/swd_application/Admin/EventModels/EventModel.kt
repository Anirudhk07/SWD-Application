package com.example.swd_application.models.EventModel

import com.example.swd_application.Admin.EventModels.EventNonProfile
import com.example.swd_application.Admin.EventProfile

data class EventModel(
    val NonProfile: EventNonProfile,
    val Profile: EventProfile
)