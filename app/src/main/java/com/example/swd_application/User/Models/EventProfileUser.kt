package com.example.swd_application.User.Models

import android.os.Parcel
import android.os.Parcelable


class EventProfileUser : Parcelable {
    lateinit var eventConductedYear: String
    lateinit var eventDescription: String
    lateinit var eventEndDate: String
    var eventFlagship: Boolean = false
    lateinit var eventHeads: List<String>
    var eventImageUrl: String? = null
    var eventLinkUrl: String? = null
    lateinit var eventName: String
    var eventNumberOfSeatsFilled: Int = 0
    lateinit var eventStartDate: String
    var eventTotalNumberOfSeats: Int = 0

    // Required for Firebase
    constructor() {

    }

    constructor(parcel: Parcel) : this() {
        eventConductedYear = parcel.readString().toString()
        eventDescription = parcel.readString().toString()
        eventEndDate = parcel.readString().toString()
        eventFlagship = parcel.readByte() != 0.toByte()
        eventHeads = parcel.createStringArrayList()!!
        eventImageUrl = parcel.readString()
        eventLinkUrl = parcel.readString()
        eventName = parcel.readString().toString()
        eventNumberOfSeatsFilled = parcel.readInt()
        eventStartDate = parcel.readString().toString()
        eventTotalNumberOfSeats = parcel.readInt()
    }

    constructor(
        eventConductedYear: String,
        eventDescription: String,
        eventEndDate: String,
        eventFlagship: Boolean,
        eventHeads: List<String>,
        eventImageUrl: String?,
        eventLinkUrl: String?,
        eventName: String,
        eventNumberOfSeatsFilled: Int,
        eventStartDate: String,
        eventTotalNumberOfSeats: Int
    ) : this() {
        this.eventConductedYear = eventConductedYear
        this.eventDescription = eventDescription
        this.eventEndDate = eventEndDate
        this.eventFlagship = eventFlagship
        this.eventHeads = eventHeads
        this.eventImageUrl = eventImageUrl
        this.eventLinkUrl = eventLinkUrl
        this.eventName = eventName
        this.eventNumberOfSeatsFilled = eventNumberOfSeatsFilled
        this.eventStartDate = eventStartDate
        this.eventTotalNumberOfSeats = eventTotalNumberOfSeats
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(eventConductedYear)
        parcel.writeString(eventDescription)
        parcel.writeString(eventEndDate)
        parcel.writeByte(if (eventFlagship) 1 else 0)
        parcel.writeStringList(eventHeads)
        parcel.writeString(eventImageUrl)
        parcel.writeString(eventLinkUrl)
        parcel.writeString(eventName)
        parcel.writeInt(eventNumberOfSeatsFilled)
        parcel.writeString(eventStartDate)
        parcel.writeInt(eventTotalNumberOfSeats)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<EventProfileUser> {
        override fun createFromParcel(parcel: Parcel): EventProfileUser {
            return EventProfileUser(parcel)
        }

        override fun newArray(size: Int): Array<EventProfileUser?> {
            return arrayOfNulls(size)
        }
    }
}
