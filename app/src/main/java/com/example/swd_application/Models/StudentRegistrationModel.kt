package com.example.swd_application.Models

class StudentRegistrationModel {
    lateinit var studentBranch: String
    lateinit var studentCollegeEmailId: String
    lateinit var studentFirstName: String
    lateinit var studentGrNo: String
    var studentImageUrl: String? = null
    lateinit var studentLastName: String
    lateinit var studentPassword: String

    // Required for Firebase
    constructor() {

    }

    constructor(
        studentBranch: String,
        studentCollegeEmailId: String,
        studentFirstName: String,
        studentGrNo: String,
        studentImageUrl: String?,
        studentLastName: String,
        studentPassword: String
    ) {
        this.studentBranch = studentBranch
        this.studentCollegeEmailId = studentCollegeEmailId
        this.studentFirstName = studentFirstName
        this.studentGrNo = studentGrNo
        this.studentImageUrl = studentImageUrl
        this.studentLastName = studentLastName
        this.studentPassword = studentPassword
    }
}