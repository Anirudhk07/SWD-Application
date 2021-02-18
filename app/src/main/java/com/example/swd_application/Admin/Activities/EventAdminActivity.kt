package com.example.swd_application.Admin

import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.swd_application.Admin.EventModels.*
import com.example.swd_application.Authentication.Admin.AdminHomeActivity
import com.example.swd_application.R
import com.example.swd_application.models.EventModel.EventModel
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.ktx.storage
import java.util.*


class EventAdminActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "Event-Admin"
        private const val IMAGE_PICK_CODE = 999

        const val COLLECTION_PATH = "EVENTS"
        const val ROOT_EVENT_IMAGE_REF = "Events-Images"
        const val IMAGE_URL_FIELD = "eventImageUrl"
    }

    private lateinit var et_admin_event_name: TextView
    private lateinit var et_admin_event_description: TextView
    private lateinit var btnChoose: Button

    private lateinit var et_event_name_head_1: TextView
    private lateinit var et_event_name_head_2: TextView
    private lateinit var et_event_link: TextView

    private lateinit var btn_date_picker_start: Button
    private lateinit var tv_start_event: TextView
    private lateinit var btn_date_picker_end: Button
    private lateinit var tv_end_event: TextView

    private lateinit var imageView: ImageView
    private var imageUri: Uri? = null
    private var imageUriAsString: String? = null

    private lateinit var radioGroup: RadioGroup
    private lateinit var radioButtonYes: RadioButton
    private lateinit var radioButtonNo: RadioButton

    private lateinit var btn_add_event: Button

    private var isEventFlagship = false
    private var startDate: String? = null
    private var endDate: String? = null

    private val eventsDb: FirebaseFirestore = Firebase.firestore
    private val storageReference: FirebaseStorage = Firebase.storage
    private val imageStorageRootRef: StorageReference = storageReference.reference
    private val rootEventImageRef: StorageReference = imageStorageRootRef.child(ROOT_EVENT_IMAGE_REF)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_admin)

        et_admin_event_name = findViewById<TextView>(R.id.et_admin_event_name)
        et_admin_event_description = findViewById<TextView>(R.id.et_admin_event_description)

        btnChoose = findViewById<Button>(R.id.btnChoose)
        imageView = findViewById<ImageView>(R.id.iv_admin_event_image)

        btn_date_picker_start = findViewById<Button>(R.id.btn_date_picker_start)
        tv_start_event = findViewById<TextView>(R.id.tv_start_event)
        btn_date_picker_end = findViewById<Button>(R.id.btn_date_picker_end)
        tv_end_event = findViewById<TextView>(R.id.tv_end_event)

        et_event_name_head_1 = findViewById<TextView>(R.id.et_event_name_head_1)
        et_event_name_head_2 = findViewById<TextView>(R.id.et_event_name_head_2)

        et_event_link = findViewById<TextView>(R.id.et_event_link)

        btn_add_event = findViewById<Button>(R.id.btn_add_event)

        radioGroup = findViewById<RadioGroup>(R.id.rg_admin_event_flagship)
        radioButtonYes = findViewById<RadioButton>(R.id.rb_admin_event_yes)
        radioButtonNo = findViewById<RadioButton>(R.id.rb_admin_event_no)

        //Calender
        val calender: Calendar = Calendar.getInstance()
        val day: Int = calender.get(Calendar.DAY_OF_MONTH)
        val month: Int = calender.get(Calendar.MONTH)
        val year: Int = calender.get(Calendar.YEAR)

        btn_date_picker_start.setOnClickListener {
            val dpd = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    startDate = "${dayOfMonth}-${month + 1}-${year}"
                    tv_start_event.text = startDate
                },
                year,
                month,
                day
            )
            dpd.show()
        }

        btn_date_picker_end.setOnClickListener {
            val dpd = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    endDate = "${dayOfMonth}-${month + 1}-${year}"
                    tv_end_event.text = endDate
                },
                year,
                month,
                day
            )
            dpd.show()
        }

        radioButtonYes.setOnClickListener {
            isEventFlagship = true
        }

        radioButtonNo.setOnClickListener {
            isEventFlagship = false
        }

        btnChoose.setOnClickListener {
            launchGallery()
        }

        btn_add_event.setOnClickListener {

            val eventModel: EventModel = createEventModel()
            Log.d(TAG, "Show Data $eventModel")

            if(eventModel.Profile.EventName.isEmpty() || eventModel.Profile.EventName.isBlank()){
                Toast.makeText(this@EventAdminActivity,"You have not provided an event name.So provide event name to proceed",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else if(eventModel.Profile.EventDescription.isEmpty() || eventModel.Profile.EventDescription.isBlank()){
                Toast.makeText(this@EventAdminActivity,"You have not provided an event description.So provide an event description to proceed",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else if (imageUri == null) {
                Toast.makeText(this@EventAdminActivity,"You have not provided an event image.So provide an event image to proceed",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else if(eventModel.Profile.EventStartDate == null){
                Toast.makeText(this@EventAdminActivity,"You have not provided an event start date.So provide an event start date to proceed",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else if(eventModel.Profile.EventEndDate == null){
                Toast.makeText(this@EventAdminActivity,"You have not provided an event end date.So provide an event end date to proceed",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else if(eventModel.Profile.EventLinkUrl.isBlank() || eventModel.Profile.EventLinkUrl.isEmpty()) {
                Toast.makeText(this@EventAdminActivity,"You have not provided an event form link.So provide an event form link to proceed",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // This function will check firestore and if event exists it does not allow user to post event
            // but if event does not exist it add the events to firestore
            checkWhetherThisEventExistsInFireStore(eventModel)
        }
    }

    private fun launchGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            imageUri = data?.data

            if (imageUri != null) {
                imageView.setImageURI(imageUri)
                imageUriAsString = data?.dataString
            }
        }
    }

    // It is used to create eventModel datatype from data provided by user
    private fun createEventModel(): EventModel {

        val eventNonProfile: EventNonProfile = EventNonProfile(
            EventCoordinatorsDetails = listOf<EventCoordinatorDetails>(),
            EventHeadsDetails = listOf<EventHeadDetails>(),
            EventVolunteersDetails = listOf<EventVolunteerDetails>()
        )

        val calender: Calendar = Calendar.getInstance()
        val year: Int = calender.get(Calendar.YEAR)
        val eventProfile: EventProfile = EventProfile(
            EventConductedYear = year,
            EventDescription = et_admin_event_description.text.toString(),
            EventEndDate = endDate,
            EventFlagship = isEventFlagship,
            EventHeads = listOf<String>(
                et_event_name_head_1.text.toString(),
                et_event_name_head_2.text.toString()
            ),
            EventImageUrl = null,
            EventLinkUrl = et_event_link.text.toString(),
            EventName = et_admin_event_name.text.toString().trim().toUpperCase(Locale.ROOT),
            EventStartDate = startDate
        )

        val eventModel = EventModel(eventNonProfile, eventProfile)
        return eventModel
    }

    private fun getFileExtension(uri: Uri): String {
        val contentResolver = contentResolver
        val mimeType = MimeTypeMap.getSingleton()
        return mimeType.getExtensionFromMimeType(contentResolver.getType(uri)).toString()
    }
    
    private fun uploadImageToFirebaseStorage(eventId:String) {

        val eventImageRef: StorageReference = imageStorageRootRef.child("${ROOT_EVENT_IMAGE_REF}/${eventId}.${getFileExtension(imageUri!!)}")

        val path: String = eventImageRef.path
        val imageName: String = eventImageRef.name
        Log.d(TAG, "path is $path and imagename is $imageName")

        val uploadTask: UploadTask = eventImageRef.putFile(imageUri!!)

        uploadTask.addOnSuccessListener {
            Log.d(TAG, "uploadImageToFirebaseStorage: Your image got successfully uploaded")
        }.addOnFailureListener {
            Log.d(TAG, "uploadImageToFirebaseStorage: ${it.message}")
            Toast.makeText(
                this@EventAdminActivity,
                "Your image did not got successfully uploaded.Please contact the administration team",
                Toast.LENGTH_SHORT
            ).show()
        }

        val urlTask = uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            eventImageRef.downloadUrl
        }

        urlTask.addOnCompleteListener { task: Task<Uri> ->
            if (task.isSuccessful) {
                val downloadUri: Uri? = task.result
                if (downloadUri != null) {
                    // We are here updating the event
                    updateEventDataWithImageUrl(eventId, IMAGE_URL_FIELD,downloadUri)
                }
            }
        }
    }

    private fun updateEventDataWithImageUrl(eventId: String,fieldName:String,downloadUri:Uri){
        eventsDb.collection(COLLECTION_PATH).document(eventId)
            .update(fieldName, downloadUri.toString())
            .addOnSuccessListener {
                Log.d(
                    TAG,
                    "DocumentSnapshot successfully updated! path name is ${downloadUri} and "
                )
            }
            .addOnFailureListener { ex -> Log.w(TAG, "Error updating document", ex) }
    }

    // This functions adds the event data to firestore
    private fun addEventDataToFirestore(eventModel: EventModel) {

        val eventId =
            "${eventModel.Profile.EventName.toUpperCase(Locale.ROOT)}-${eventModel.Profile.EventConductedYear}"
        eventsDb.collection(COLLECTION_PATH)
            .document(eventId)
            .set(eventModel)
            .addOnCompleteListener {
                Toast.makeText(
                    this@EventAdminActivity,
                    "The Event Data Got Uploaded",
                    Toast.LENGTH_LONG
                ).show()

                val intent = Intent(this@EventAdminActivity, AdminHomeActivity::class.java)
                startActivity(intent)
                finish()
            }.addOnFailureListener {
                Toast.makeText(
                    this@EventAdminActivity,
                    "The Event Data did not got uploaded.Try Again !!",
                    Toast.LENGTH_LONG
                ).show()
            }
    }

    // This will check whether event already exists in firestore or not
    // If events exist it will not allow user to create an event
    // If event does not exist first it creates an event and send it to firebase without image and image url
    // Then it uploads the image and the update the event data with image Url
    private fun checkWhetherThisEventExistsInFireStore(eventModel: EventModel) {

        val eventId = "${eventModel.Profile.EventName.toUpperCase(Locale.ROOT)}-${eventModel.Profile.EventConductedYear}"

        eventsDb.collection(COLLECTION_PATH)
            .get()
            .addOnSuccessListener { documents: QuerySnapshot ->
                for (document in documents) {
                    Log.d(TAG, "DocumentSnapshot id: ${document.id}")
                    if (document.id == eventId) {
                        Toast.makeText(
                            this@EventAdminActivity,
                            "This event already exists in database",
                            Toast.LENGTH_LONG
                        ).show()
                        return@addOnSuccessListener
                    }
                }

                Log.d(TAG, "No such document exists.So we insert this event document")

                // This function assume that image-Uri is null and uploads the event data without image
                addEventDataToFirestore(eventModel)

                // This function uploads the image and also update the event document with the image URL
                uploadImageToFirebaseStorage(eventId)
            }
    }

}
