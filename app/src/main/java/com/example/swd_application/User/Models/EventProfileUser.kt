package com.example.swd_application.User.Models

import android.os.Parcel
import android.os.Parcelable


class EventProfileUser : Parcelable {
    var eventConductedYear:Int = -1
    lateinit var eventDescription: String
    lateinit var eventEndDate: String
    var eventFlagship: Boolean = false
    lateinit var eventHeads: List<String>
    var eventImageUrl: String? = null
    var eventLinkUrl: String? = null
    lateinit var eventName: String
    lateinit var eventStartDate: String

    constructor(){

    }

    constructor(parcel: Parcel) : this() {
        eventConductedYear = parcel.readInt()
        eventDescription = parcel.readString().toString()
        eventEndDate = parcel.readString().toString()
        eventFlagship = parcel.readByte() != 0.toByte()
        eventHeads = parcel.createStringArrayList()!!
        eventImageUrl = parcel.readString()
        eventLinkUrl = parcel.readString()
        eventName = parcel.readString().toString()
        eventStartDate = parcel.readString().toString()
    }

    constructor(eventConductedYear:Int,eventDescription: String,eventEndDate: String,eventFlagship: Boolean,eventHeads: List<String>,eventImageUrl: String?,eventLinkUrl: String?,eventName: String,eventStartDate: String):this(){
        this.eventConductedYear = eventConductedYear
        this.eventDescription = eventDescription
        this.eventEndDate = eventEndDate
        this.eventFlagship = eventFlagship
        this.eventHeads = eventHeads
        this.eventImageUrl = eventImageUrl
        this.eventLinkUrl = eventLinkUrl
        this.eventName = eventName
        this.eventStartDate = eventStartDate
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(eventConductedYear)
        parcel.writeString(eventDescription)
        parcel.writeString(eventEndDate)
        parcel.writeByte(if (eventFlagship) 1 else 0)
        parcel.writeStringList(eventHeads)
        parcel.writeString(eventImageUrl)
        parcel.writeString(eventLinkUrl)
        parcel.writeString(eventName)
        parcel.writeString(eventStartDate)
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
