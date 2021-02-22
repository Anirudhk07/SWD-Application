package com.example.swd_application.Admin.EventModels

data class EventNonProfile(
    val EventCoordinatorsDetails:List<EventCoordinatorDetails>,
    val EventHeadsDetails:List<EventHeadDetails>,
    val EventVolunteersDetails:List<EventVolunteerDetails>
)

data class EventCoordinatorDetails(
    val CoordinatorBranch: String,
    val CoordinatorCollegeEmailId: String,
    val CoordinatorContactNo: String,
    val CoordinatorFirstName: String,
    val CoordinatorGrNo: String,
    val CoordinatorId: String,
    val CoordinatorLastName: String,
    val CoordinatorYear: String
)

data class EventHeadDetails(
    val HeadBranch: String,
    val HeadCollegeEmailId: String,
    val HeadContactNo: String,
    val HeadFirstName: String,
    val HeadGrNo: String,
    val HeadId: String,
    val HeadLastName: String,
    val HeadYear: String
)

data class EventVolunteerDetails(
    val VolunteerBranch: String,
    val VolunteerCollegeEmailId: String,
    val VolunteerContactNo: String,
    val VolunteerFirstName: String,
    val VolunteerGrNo: String,
    val VolunteerId: String,
    val VolunteerLastName: String,
    val VolunteerYear: String
)
