package com.example.swd_application.Authentication.Client

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.swd_application.Models.StudentRegistrationModel
import com.example.swd_application.R
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class ProfileActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "ProfileActivity"
        private const val STUDENT_REGISTRATION_COLLECTION = "STUDENT_REGISTRATION"
        private const val STUDENT_GR_NUMBER = "studentGrNo"
        private const val APP_SHARED_PREFERENCES = "APP-PREFERENCES"
    }

    private lateinit var ivStudentProfileImage: ImageView
    private lateinit var tvFirstName: TextView
    private lateinit var tvLastName: TextView
    private lateinit var tvGrNo: TextView
    private lateinit var tvVitEmail: TextView
    private lateinit var tvBranch: TextView

    private val firestore: FirebaseFirestore = Firebase.firestore
    private val studentRegistrationCollection: CollectionReference = firestore.collection(STUDENT_REGISTRATION_COLLECTION)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        ivStudentProfileImage = findViewById(R.id.iv_profile_student_image)
        tvFirstName = findViewById(R.id.tv_profile_first_name)
        tvLastName = findViewById(R.id.tv_profile_last_name)
        tvGrNo = findViewById(R.id.tv_profile_gr_number)
        tvVitEmail = findViewById(R.id.tv_profile_vit_email)
        tvBranch = findViewById(R.id.tv_profile_department)

        val preferences = getSharedPreferences(APP_SHARED_PREFERENCES, MODE_PRIVATE)
        val studentGrNo = preferences.getString(STUDENT_GR_NUMBER, "null")
        Log.d(TAG, "onCreate: $studentGrNo")

        if (studentGrNo != null) {
            fetchAndSetStudentData(studentGrNo)
        }
    }

    private fun fetchAndSetStudentData(studentGrNo: String) {
        studentRegistrationCollection
            .document(studentGrNo)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val document: DocumentSnapshot? = task.result
                    if (document != null && document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.data)

                        val studentRegistrationModel = document.toObject(StudentRegistrationModel::class.java)
                        if(studentRegistrationModel != null){
                            setStudentProfileData(studentRegistrationModel)
                        }
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.exception)
                    Toast.makeText(this@ProfileActivity, "There was some issue while login.So please try to login again.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun setStudentProfileData(studentRegistrationModel: StudentRegistrationModel) {
        if(studentRegistrationModel.studentImageUrl != null){
            Glide.with(this)
                .load(studentRegistrationModel.studentImageUrl)
                .into(ivStudentProfileImage)
        }

        tvFirstName.text = studentRegistrationModel.studentFirstName
        tvLastName.text = studentRegistrationModel.studentLastName
        tvGrNo.text = studentRegistrationModel.studentGrNo
        tvVitEmail.text = studentRegistrationModel.studentCollegeEmailId
        tvBranch.text = studentRegistrationModel.studentBranch
    }
}