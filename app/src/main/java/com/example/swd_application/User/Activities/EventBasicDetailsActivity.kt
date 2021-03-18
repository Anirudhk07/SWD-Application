package com.example.swd_application.User.Activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.swd_application.Models.EventModel
import com.example.swd_application.User.Adapters.EventListAdapter
import com.example.swd_application.R
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class EventBasicDetailsActivity : AppCompatActivity(), EventListAdapter.OnEventClickListener {

    companion object {
        private const val TAG = "EventBasicDetails"
        const val EVENT_PROFILE_MODEL = "EVENT_PROFILE"
        const val EVENTS_COLLECTION_PATH = "EVENTS"
    }

    private lateinit var fireStoreDb: FirebaseFirestore
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter:EventListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_basic_details)

        fireStoreDb = Firebase.firestore
        recyclerView = findViewById<RecyclerView>(R.id.rv_event_list_items)

        val query:Query = fireStoreDb.collection(EVENTS_COLLECTION_PATH)

        Log.d(TAG,"This is ${query}")

        val options: FirestoreRecyclerOptions<EventModel> = FirestoreRecyclerOptions.Builder<EventModel>()
            .setQuery(query, EventModel::class.java)
            .build()

        Log.d(TAG,"These are some events ${options.snapshots}")

         adapter = EventListAdapter(this,options)
         recyclerView.layoutManager = LinearLayoutManager(this)
         recyclerView.adapter = adapter
    }

    override fun onEventClick(position: Int) {
        Log.d(TAG,"This is ${adapter.getItem(position)}")
        Toast.makeText(this,"This is ${position}",Toast.LENGTH_SHORT).show()

        val eventData = adapter.getItem(position)
        val intent = Intent(this,EventFullDetailActivity::class.java)
        intent.putExtra(EVENT_PROFILE_MODEL,eventData)
        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

}