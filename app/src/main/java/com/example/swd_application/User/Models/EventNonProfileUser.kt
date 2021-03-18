package com.example.swd_application.User.Models

class EventNonProfileUser {
    lateinit var eventCoordinatorsDetails: List<EventCoordinatorDetailsUser>
    lateinit var eventHeadsDetails: List<EventHeadDetailsUser>
    lateinit var eventVolunteersDetails: List<EventVolunteerDetailsUser>

    constructor() {

    }

    constructor(
        eventCoordinatorsDetails: List<EventCoordinatorDetailsUser>,
        eventHeadsDetails: List<EventHeadDetailsUser>,
        eventVolunteersDetails: List<EventVolunteerDetailsUser>
    ) : this() {
        this.eventCoordinatorsDetails = eventCoordinatorsDetails
        this.eventHeadsDetails = eventHeadsDetails
        this.eventVolunteersDetails = eventVolunteersDetails
    }
}

class EventCoordinatorDetailsUser {
    lateinit var coordinatorBranch: String
    lateinit var coordinatorCollegeEmailId: String
    lateinit var coordinatorContactNo: String
    lateinit var coordinatorFirstName: String
    lateinit var coordinatorGrNo: String
    lateinit var coordinatorId: String
    lateinit var coordinatorLastName: String
    lateinit var coordinatorYear: String

    // Required for Firebase
    constructor() {

    }

    constructor(
        coordinatorBranch: String,
        coordinatorCollegeEmailId: String,
        coordinatorContactNo: String,
        coordinatorFirstName: String,
        coordinatorGrNo: String,
        coordinatorId: String,
        coordinatorLastName: String,
        coordinatorYear: String
    ) : this() {
        this.coordinatorBranch = coordinatorBranch
        this.coordinatorCollegeEmailId = coordinatorCollegeEmailId
        this.coordinatorContactNo = coordinatorContactNo
        this.coordinatorFirstName = coordinatorFirstName
        this.coordinatorGrNo = coordinatorGrNo
        this.coordinatorId = coordinatorId
        this.coordinatorLastName = coordinatorLastName
        this.coordinatorYear = coordinatorYear
    }
}

class EventHeadDetailsUser {
    lateinit var headBranch: String
    lateinit var headCollegeEmailId: String
    lateinit var headContactNo: String
    lateinit var headFirstName: String
    lateinit var headGrNo: String
    lateinit var headId: String
    lateinit var headLastName: String
    lateinit var headYear: String

    // Required for Firebase
    constructor() {

    }

    constructor(
        headBranch: String,
        headCollegeEmailId: String,
        headContactNo: String,
        headFirstName: String,
        headGrNo: String,
        headId: String,
        headLastName: String,
        headYear: String
    ) : this() {
        this.headBranch = headBranch
        this.headCollegeEmailId = headCollegeEmailId
        this.headContactNo = headContactNo
        this.headFirstName = headFirstName
        this.headGrNo = headGrNo
        this.headId = headId
        this.headLastName = headLastName
        this.headYear = headYear
    }
}

class EventVolunteerDetailsUser {
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
