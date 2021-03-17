package com.example.swd_application.User.Models

class EventModelUser {

    lateinit var profile: EventProfileUser
    lateinit var nonProfile: EventNonProfileUser

    // Required for Firebase
    constructor() {

    }

    constructor(nonProfile: EventNonProfileUser, profile: EventProfileUser) : this() {
        this.nonProfile = nonProfile
        this.profile = profile
    }

}