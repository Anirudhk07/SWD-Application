package com.example.swd_application.Models

class EventCoordinatorModel {
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