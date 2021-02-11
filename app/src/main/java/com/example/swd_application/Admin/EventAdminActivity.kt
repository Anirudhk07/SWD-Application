package com.example.swd_application.Admin

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.swd_application.R
import java.util.*


class EventAdminActivity : AppCompatActivity() {
    private lateinit var imageView: ImageView
    private lateinit var imageButton: Button
    private lateinit var sendButton: Button
    private var imageData: ByteArray? = null

    companion object {
        private const val IMAGE_PICK_CODE = 999
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_admin)

        val et_admin_event_name = findViewById<TextView>(R.id.et_admin_event_name)
        val et_admin_event_discription = findViewById<TextView>(R.id.et_admin_event_discription)

        val btnChoose = findViewById<Button>(R.id.btnChoose);
        val btnUpload = findViewById<Button>(R.id.btnUpload);
        val imageView = findViewById<ImageView>(R.id.imgView);

        val btn_date_picker_start = findViewById<Button>(R.id.btn_date_picker_start)
        val tv_start_event = findViewById<TextView>(R.id.tv_start_event)

        val btn_date_picker_end = findViewById<Button>(R.id.btn_date_picker_end)
        val tv_end_event = findViewById<TextView>(R.id.tv_end_event)

        val et_event_name_head_1 = findViewById<TextView>(R.id.et_event_name_head_1)
        val et_event_name_head_2 = findViewById<TextView>(R.id.et_event_name_head_2)

        val et_event_link = findViewById<TextView>(R.id.et_event_link)

        val btn_add_event = findViewById<Button>(R.id.btn_add_event)


        btnChoose.setOnClickListener {
            launchGallery()
        }
        btnUpload.setOnClickListener {
//            uploadImage()
        }

        //Calender
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        btn_date_picker_start.setOnClickListener {
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{view, year, month, dayOfMonth ->
                tv_start_event.text = ""+ dayOfMonth + "/" + (month+1) + "/" + year
            },year,month,day)
            dpd.show()
        }

        btn_date_picker_end.setOnClickListener {
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{view, year, month, dayOfMonth ->
                tv_end_event.text = ""+ dayOfMonth + "/" + (month+1) + "/" + year
            },year,month,day)
            dpd.show()
        }

        btn_add_event.setOnClickListener {
            //TODO: Anubhav Pabby Pls Continue from here

        }
    }

    private fun launchGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }
}