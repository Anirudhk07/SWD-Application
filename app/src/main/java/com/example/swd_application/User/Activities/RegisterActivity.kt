package com.example.swd_application.User.Activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.webkit.MimeTypeMap
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.swd_application.Models.StudentRegistrationModel
import com.example.swd_application.R
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.ktx.storage
import com.himanshurawat.hasher.HashType
import com.himanshurawat.hasher.Hasher
import java.util.*

class RegisterActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "RegisterActivity"
        private const val STUDENT_REGISTRATION_COLLECTION = "STUDENT_REGISTRATION"
        private const val STUDENT_GR_NUMBER = "studentGrNo"

        private const val STUDENT_IMAGE_PICK_CODE = 999
        const val STUDENTS_ROOT_EVENT_IMAGE_REF = "Students-Profile-Image"
        const val STUDENT_IMAGE_URL_FIELD = "studentImageUrl"
    }

    private lateinit var etFirstName: EditText
    private lateinit var etLastName: EditText
    private lateinit var etGrNumber: EditText
    private lateinit var etDepartment: EditText

    private lateinit var ivStudentImageProfile: ImageView
    private lateinit var btnChoose: Button
    private var imageUri: Uri? = null
    private var imageUriAsString: String? = null

    private lateinit var etVitEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirmPassword: EditText
    private lateinit var btnRegister: Button
    private lateinit var btnLogin: Button

    private val firestore: FirebaseFirestore = Firebase.firestore
    private val studentRegistrationCollection: CollectionReference = firestore.collection(STUDENT_REGISTRATION_COLLECTION)

    private val storageReference: FirebaseStorage = Firebase.storage
    private val imageStorageRootRef: StorageReference = storageReference.reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        etFirstName = findViewById<EditText>(R.id.et_register_firstname)
        etLastName = findViewById<EditText>(R.id.et_register_lastname)
        etGrNumber = findViewById<EditText>(R.id.et_register_gr_number)
        etDepartment = findViewById<EditText>(R.id.et_register_department)
        ivStudentImageProfile = findViewById<ImageView>(R.id.iv_register_student_profile_image)
        btnChoose = findViewById<Button>(R.id.btn_register_choose_image)
        etVitEmail = findViewById<EditText>(R.id.et_register_vit_email)
        etPassword = findViewById<EditText>(R.id.et_register_password)
        etConfirmPassword = findViewById<EditText>(R.id.et_register_confirm_password)

        btnRegister = findViewById(R.id.btn_register_register)
        btnLogin = findViewById(R.id.btn_register_login)

        btnChoose.setOnClickListener {
            launchGallery()
        }

        btnLogin.setOnClickListener {
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
        }

        btnRegister.setOnClickListener {
            val areAllFieldsFilled = checkWhetherAllFieldsAreFilled()
            // When all the fields are not filled
            if (!areAllFieldsFilled) {
                return@setOnClickListener
            }

            val studentRegistrationData = getStudentRegistrationData()
            checkWhetherStudentIsAlreadyRegistered(studentRegistrationData)
        }
    }

    private fun addStudentRegistrationData(studentRegistrationModel: StudentRegistrationModel) {
        studentRegistrationCollection
            .document(studentRegistrationModel.studentGrNo)
            .set(studentRegistrationModel)
            .addOnSuccessListener {
                Log.d(TAG, "DocumentSnapshot successfully written!")

                Toast.makeText(this@RegisterActivity, "Your account is successfully registered with us.Redirecting you to login page", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
            }.addOnFailureListener { e ->
                Log.w(TAG, "Error writing document")
                Toast.makeText(this@RegisterActivity, e.message, Toast.LENGTH_SHORT).show()
            }
    }

    private fun checkWhetherStudentIsAlreadyRegistered(studentRegistrationModel: StudentRegistrationModel) {
        studentRegistrationCollection
            .whereEqualTo(STUDENT_GR_NUMBER, studentRegistrationModel.studentGrNo)
            .get()
            .addOnCompleteListener { task: Task<QuerySnapshot> ->
                if (task.isSuccessful) {
                    if (task.result != null) {
                        for (document: QueryDocumentSnapshot in task.result!!) {
                            Log.d(TAG, document.getId() + " => " + document.getData());

                            val student = document.toObject<StudentRegistrationModel>(StudentRegistrationModel::class.java)

                            Log.d(TAG, "checkWhetherStudentIsAlreadyRegistered: ${studentRegistrationModel.studentGrNo.equals(student.studentGrNo)}")

                            if (studentRegistrationModel.studentGrNo == student.studentGrNo) {
                                Toast.makeText(this@RegisterActivity, "Your account already exists.Please try to login with the your registered account.", Toast.LENGTH_SHORT).show();
                                return@addOnCompleteListener
                            }
                        }
                    }

                    addStudentRegistrationData(studentRegistrationModel)

                    uploadImageToFirebaseStorage(studentRegistrationModel.studentGrNo)
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                    Toast.makeText(this@RegisterActivity, "There was problem occurred in getting you registered.So Please Try Again.", Toast.LENGTH_SHORT).show();
                }
            }
    }

    private fun getFileExtension(uri: Uri): String {
        val contentResolver = contentResolver
        val mimeType = MimeTypeMap.getSingleton()
        return mimeType.getExtensionFromMimeType(contentResolver.getType(uri)).toString()
    }

    private fun uploadImageToFirebaseStorage(studentGrNo: String) {

        val studentProfileImageRef: StorageReference = imageStorageRootRef.child("${STUDENTS_ROOT_EVENT_IMAGE_REF}/${studentGrNo}.${getFileExtension(imageUri!!)}")

        val path: String = studentProfileImageRef.path
        val imageName: String = studentProfileImageRef.name
        Log.d(TAG, "path is $path and imagename is $imageName")

        val uploadTask: UploadTask = studentProfileImageRef.putFile(imageUri!!)

        uploadTask.addOnSuccessListener {
            Log.d(TAG, "uploadImageToFirebaseStorage: Your image got successfully uploaded")
        }.addOnFailureListener {
            Log.d(TAG, "uploadImageToFirebaseStorage: ${it.message}")

            Toast.makeText(this@RegisterActivity, "Your image did not got successfully uploaded.Please contact the administration team", Toast.LENGTH_SHORT).show()
        }

        val urlTask = uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            studentProfileImageRef.downloadUrl
        }

        urlTask.addOnCompleteListener { task: Task<Uri> ->
            if (task.isSuccessful) {
                val downloadUri: Uri? = task.result
                if (downloadUri != null) {
                    // We are here updating the event
                    updateStudentDataWithImageUrl(studentGrNo,STUDENT_IMAGE_URL_FIELD, downloadUri)
                }
            }
        }
    }

    private fun updateStudentDataWithImageUrl(studentGrNo: String, fieldName: String, downloadUri: Uri) {
        studentRegistrationCollection
            .document(studentGrNo)
            .update(mapOf(fieldName to downloadUri.toString()))
            .addOnSuccessListener {
                Log.d(TAG, "DocumentSnapshot successfully updated! path name is ${downloadUri}")
            }
            .addOnFailureListener { ex ->
                Log.w(TAG, "Error updating document", ex)
            }
    }

    private fun checkWhetherAllFieldsAreFilled(): Boolean {
        if (etFirstName.text.toString().isEmpty() || etFirstName.text.toString().isBlank()) {
            Toast.makeText(this@RegisterActivity, "Your firstname field is empty.Please enter firstname to proceed.", Toast.LENGTH_SHORT).show()
            return false
        }
        if (etLastName.text.toString().isEmpty() || etLastName.text.toString().isBlank()) {
            Toast.makeText(this@RegisterActivity, "Your lastname field is empty.Please enter lastname to proceed.", Toast.LENGTH_SHORT).show()
            return false
        }
        if (etGrNumber.text.toString().isEmpty() || etGrNumber.text.toString().isBlank()) {
            Toast.makeText(this@RegisterActivity, "Your GR-NUMBER field is empty.Please enter GR-NUMBER to proceed.", Toast.LENGTH_SHORT).show()
            return false
        }
        if (etDepartment.text.toString().isEmpty() || etDepartment.text.toString().isBlank()) {
            Toast.makeText(this@RegisterActivity, "Your department field is empty.Please enter department to proceed.", Toast.LENGTH_SHORT).show()
            return false
        }
        if (ivStudentImageProfile.drawable == null) {
            Toast.makeText(this@RegisterActivity, "You have not chosen an image.Please chose an image to proceed.", Toast.LENGTH_SHORT).show()
            return false
        }
        if (etVitEmail.text.toString().isEmpty() || etVitEmail.text.toString().isBlank()) {
            Toast.makeText(this@RegisterActivity, "Your vit email address field is empty.Please enter vit email address to proceed.", Toast.LENGTH_SHORT).show()
            return false
        }
        if (!isEmailValid(etVitEmail.text.toString())) {
            Toast.makeText(this@RegisterActivity, "You have entered an invalid vit email address", Toast.LENGTH_SHORT).show()
            return false
        }
        if (etPassword.text.toString().isEmpty() || etPassword.text.toString().isBlank()) {
            Toast.makeText(this@RegisterActivity, "Your password field is empty.Please enter password to proceed.", Toast.LENGTH_SHORT).show()
            return false
        }
        if (etConfirmPassword.text.toString().isEmpty() || etConfirmPassword.text.toString().isBlank()) {
            Toast.makeText(this@RegisterActivity, "Your confirm password field is empty.Please enter confirm password to proceed.", Toast.LENGTH_SHORT).show()
            return false
        }
        if (etPassword.text.toString() != etConfirmPassword.text.toString()) {
            Toast.makeText(this@RegisterActivity, "Your password and confirm password doesn't matches!!!", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    private fun isEmailValid(vitEmail: CharSequence): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(vitEmail).matches()
    }

    private fun getStudentRegistrationData(): StudentRegistrationModel {
        val encryptedPassword: String = Hasher.hash(etPassword.text.toString(), HashType.SHA_256)

        return StudentRegistrationModel(
            etDepartment.text.toString(),
            etVitEmail.text.toString().toLowerCase(Locale.ROOT),
            capitalize(etFirstName.text.toString()),
            etGrNumber.text.toString(),
            null,
            capitalize(etLastName.text.toString()),
            encryptedPassword
        )
    }

    private fun capitalize(fieldValue: String): String {
        return fieldValue.substring(0, 1).toUpperCase(Locale.ROOT) + fieldValue.substring(1).toLowerCase(Locale.ROOT)
    }

    private fun launchGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, STUDENT_IMAGE_PICK_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == STUDENT_IMAGE_PICK_CODE) {
            imageUri = data?.data

            if (imageUri != null) {
                ivStudentImageProfile.setImageURI(imageUri)
                imageUriAsString = data?.dataString
            }
        }
    }
}

