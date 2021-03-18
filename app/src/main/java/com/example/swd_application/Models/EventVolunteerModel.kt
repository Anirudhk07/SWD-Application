package com.example.swd_application.Models

class EventVolunteerModel {
    lateinit var volunteerBranch: String
    lateinit var volunteerCollegeEmailId: String
    lateinit var volunteerContactNo: String
    lateinit var volunteerFirstName: String
    lateinit var volunteerGrNo: String
    lateinit var volunteerId: String
    lateinit var volunteerLastName: String
    lateinit var volunteerYear: String

    // Required for Firebase
    constructor() {

    }

    constructor(
        volunteerBranch: String,
        volunteerCollegeEmailId: String,
        volunteerContactNo: String,
        volunteerFirstName: String,
        volunteerGrNo: String,
        volunteerId: String,
        volunteerLastName: String,
        volunteerYear: String
    ) : this() {
        this.volunteerBranch = volunteerBranch
        this.volunteerCollegeEmailId = volunteerCollegeEmailId
        this.volunteerContactNo = volunteerContactNo
        this.volunteerFirstName = volunteerFirstName
        this.volunteerGrNo = volunteerGrNo
        this.volunteerId = volunteerId
        this.volunteerLastName = volunteerLastName
        this.volunteerYear = volunteerYear
    }
}