package com.example.swd_application.User.Activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.swd_application.Authentication.Client.ProfileActivity
import com.example.swd_application.Models.StudentRegistrationModel
import com.example.swd_application.R
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.himanshurawat.hasher.HashType
import com.himanshurawat.hasher.Hasher
import java.util.*

class LoginActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "LoginActivity"
        private const val STUDENT_REGISTRATION_COLLECTION = "STUDENT_REGISTRATION"
        private const val STUDENT_GR_NUMBER = "studentGrNo"
        private const val APP_SHARED_PREFERENCES = "APP-PREFERENCES"
    }

    private val firestore: FirebaseFirestore = Firebase.firestore
    private val studentRegistrationCollection: CollectionReference = firestore.collection(STUDENT_REGISTRATION_COLLECTION)

    private lateinit var etGrNumber: EditText
    private lateinit var etVitEmail: EditText
    private lateinit var etPassword: EditText

    private lateinit var btnRegister: Button
    private lateinit var btnLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_new)

        etGrNumber = findViewById(R.id.et_login_gr_number)
        etVitEmail = findViewById(R.id.et_login_vit_email)
        etPassword = findViewById(R.id.et_login_password)

        btnRegister = findViewById(R.id.btn_login_register)
        btnLogin = findViewById(R.id.btn_login_action)

        btnRegister.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener {
            val areAllFieldsFilled = checkWhetherAllFieldsAreFilled()
            // When all the fields are not filled
            if (!areAllFieldsFilled) {
                return@setOnClickListener
            }

            val grNumber = etGrNumber.text.toString().trim()
            val email = etVitEmail.text.toString().trim().toLowerCase(Locale.ROOT)
            val password = Hasher.hash(etPassword.text.toString().trim(), HashType.SHA_256)

            checkWhetherStudentIsAlreadyRegistered(grNumber, email, password)

            // Just for demonstration purpose
            // val preferences = getSharedPreferences (APP_SHARED_PREFERENCES, MODE_PRIVATE);
            // val id = preferences.getString (STUDENT_GR_NUMBER, "null");
            // Log.d(TAG, "onCreate: " + id);
        }
    }

    private fun checkWhetherStudentIsAlreadyRegistered(studentGrNo: String, email: String, password: String) {
        studentRegistrationCollection
            .document(studentGrNo)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val document: DocumentSnapshot? = task.result
                    if (document != null && document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.data)

                        val studentRegistrationModel = document.toObject(StudentRegistrationModel::class.java)
                        if (studentRegistrationModel != null) {
                            if (email != studentRegistrationModel.studentCollegeEmailId) {
                                Toast.makeText(this@LoginActivity, "You have entered the wrong email.Please enter the correct email.", Toast.LENGTH_SHORT).show()
                                return@addOnCompleteListener
                            }

                            if (password != studentRegistrationModel.studentPassword) {
                                Toast.makeText(this@LoginActivity, "You have entered the wrong password.Please enter the correct password.", Toast.LENGTH_SHORT).show()
                                return@addOnCompleteListener
                            }

                            val preferences = getSharedPreferences(APP_SHARED_PREFERENCES, MODE_PRIVATE)
                            preferences.edit().putString(STUDENT_GR_NUMBER, studentRegistrationModel.studentGrNo).apply()

                            Toast.makeText(this@LoginActivity, "Getting you logged in inside the app.", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this@LoginActivity, ProfileActivity::class.java))
                        }
                    } else {
                        Log.d(TAG, "No such document")
                        Toast.makeText(this@LoginActivity, "Your student account doesn't exist.Please get yourself registered on the app.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.exception)
                    Toast.makeText(this@LoginActivity, "There was some issue while login.So please try to login again.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun checkWhetherAllFieldsAreFilled(): Boolean {
        if (etGrNumber.text.toString().isEmpty() || etGrNumber.text.toString().isBlank()) {
            Toast.makeText(this@LoginActivity, "Your gr number field is empty.Please enter gr number to proceed.", Toast.LENGTH_SHORT).show()
            return false
        }
        if (etVitEmail.text.toString().isEmpty() || etVitEmail.text.toString().isBlank()) {
            Toast.makeText(this@LoginActivity, "Your vit email address field is empty.Please enter vit email address to proceed.", Toast.LENGTH_SHORT).show()
            return false
        }
        if (!isEmailValid(etVitEmail.text.toString())) {
            Toast.makeText(this@LoginActivity, "You have entered an invalid vit email address", Toast.LENGTH_SHORT).show()
            return false
        }
        if (etPassword.text.toString().isEmpty() || etPassword.text.toString().isBlank()) {
            Toast.makeText(this@LoginActivity, "Your password field is empty.Please enter password to proceed.", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    private fun isEmailValid(vitEmail: CharSequence): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(vitEmail).matches()
    }
}