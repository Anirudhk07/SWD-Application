package com.example.swd_application.Models

class EventHeadModel {
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