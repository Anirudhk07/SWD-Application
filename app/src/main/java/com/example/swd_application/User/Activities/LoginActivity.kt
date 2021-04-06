package com.example.swd_application.User.Activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.swd_application.Models.StudentRegistrationModel
import com.example.swd_application.R
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
    }

    private val firestore: FirebaseFirestore = Firebase.firestore

    private lateinit var grNumber: EditText
    private lateinit var vitEmail: EditText
    private lateinit var password: EditText

    private lateinit var btnRegister: Button
    private lateinit var btnLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_new)

        grNumber = findViewById<EditText>(R.id.et_login_gr_number)
        vitEmail = findViewById<EditText>(R.id.et_login_vit_email)
        password = findViewById<EditText>(R.id.et_login_password)

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

            val grNumber = grNumber.text.toString().trim()
            val email = vitEmail.text.toString().trim().toLowerCase(Locale.ROOT)
            val password = Hasher.hash(password.text.toString().trim(), HashType.SHA_256)

            checkWhetherStudentIsAlreadyRegistered(grNumber, email, password)
        }
    }

    private fun checkWhetherStudentIsAlreadyRegistered(studentGrNo: String, email: String, password: String) {
        firestore.collection(STUDENT_REGISTRATION_COLLECTION)
            .document(studentGrNo)
            .get()
            .addOnCompleteListener { task ->
                if(task.isSuccessful){
                    val document: DocumentSnapshot? = task.result
                    if(document != null && document.exists()){
                        Log.d(TAG, "DocumentSnapshot data: " + document.data)

                        val studentRegistrationModel = document.toObject(StudentRegistrationModel::class.java)
                        if(studentRegistrationModel != null){
                            if (email != studentRegistrationModel.studentCollegeEmailId) {
                                Toast.makeText(this@LoginActivity, "You have entered the wrong email.Please enter the correct email.", Toast.LENGTH_SHORT).show()
                                return@addOnCompleteListener
                            }

                            if (password != studentRegistrationModel.studentPassword) {
                                Toast.makeText(this@LoginActivity, "You have entered the wrong password.Please enter the correct password.", Toast.LENGTH_SHORT).show()
                                return@addOnCompleteListener
                            }

                            Toast.makeText(this@LoginActivity,"Getting you logged in inside the app.",Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this@LoginActivity,EventBasicDetailsActivity::class.java))
                        }
                    } else {
                        Log.d(TAG, "No such document");
                        Toast.makeText(this@LoginActivity, "Your student account doesn't exist.Please get yourself registered on the app.", Toast.LENGTH_SHORT).show();
                    }
                } else{
                    Log.d(TAG, "get failed with ", task.getException());
                    Toast.makeText(this@LoginActivity, "There was some issue while login.So please try to login again.", Toast.LENGTH_SHORT).show();
                }
            }
    }

    private fun checkWhetherAllFieldsAreFilled(): Boolean {
        if (grNumber.text.toString().isEmpty() || grNumber.text.toString().isBlank()) {
            Toast.makeText(this@LoginActivity, "Your gr number field is empty.Please enter gr number to proceed.", Toast.LENGTH_SHORT).show()
            return false
        }
        if (vitEmail.text.toString().isEmpty() || vitEmail.text.toString().isBlank()) {
            Toast.makeText(this@LoginActivity, "Your vit email address field is empty.Please enter vit email address to proceed.", Toast.LENGTH_SHORT).show()
            return false
        }
        if (!isEmailValid(vitEmail.text.toString())) {
            Toast.makeText(this@LoginActivity, "You have entered an invalid vit email address", Toast.LENGTH_SHORT).show()
            return false
        }
        if (password.text.toString().isEmpty() || password.text.toString().isBlank()) {
            Toast.makeText(this@LoginActivity, "Your password field is empty.Please enter password to proceed.", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    private fun isEmailValid(vitEmail: CharSequence): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(vitEmail).matches()
    }
}