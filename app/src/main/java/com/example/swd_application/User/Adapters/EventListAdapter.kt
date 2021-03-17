package com.example.swd_application.User.Adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.swd_application.R
import com.example.swd_application.User.Models.EventModelUser
import com.example.swd_application.User.Models.EventProfileUser
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class EventListAdapter(private val onEventClickListener: OnEventClickListener,private val recyclerOptions: FirestoreRecyclerOptions<EventModelUser>) : FirestoreRecyclerAdapter<EventModelUser, EventListAdapter.EventViewHolder>(recyclerOptions) {

    companion object{
        private const val TAG = "Event Adapter"
    }

    interface OnEventClickListener{
        fun onEventClick(position:Int)
    }

    inner class EventViewHolder(@NonNull itemView: View,onEventClickListener: OnEventClickListener): RecyclerView.ViewHolder(itemView),View.OnClickListener{
        val eventName:TextView
        val eventStartDate:TextView
        val eventEndDate:TextView
        val eventImage:ImageView
        val onEventClickListener:OnEventClickListener

        init{
            eventName = itemView.findViewById<TextView>(R.id.tv_recycler_event_list_event_name)
            eventStartDate = itemView.findViewById<TextView>(R.id.tv_recycler_event_list_event_start_date_value)
            eventEndDate = itemView.findViewById<TextView>(R.id.tv_recycler_event_list_event_end_date_value)
            eventImage = itemView.findViewById<ImageView>(R.id.iv_recycler_event_list_event_image)

            this.onEventClickListener = onEventClickListener

            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            // TODO("Not yet implemented")
            onEventClickListener.onEventClick(adapterPosition)
        }

        fun bindEventData(eventProfileModel: EventProfileUser){
            val eventProfile: EventProfileUser = eventProfileModel

            eventName.text = eventProfile.eventName
            eventStartDate.text = eventProfile.eventStartDate
            eventEndDate.text = eventProfile.eventEndDate

            val eventImageUrl:String? = eventProfile.eventImageUrl
            if(eventImageUrl != null) {
                Log.d(TAG, "bindEventData: $eventImageUrl")
                Log.d(TAG, "itemview context is ${itemView.context}")
                Glide.with(itemView.context)
                    .load(eventImageUrl)
                    .into(eventImage)
            }
        }
    }

    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int):EventViewHolder{
        // TODO("Not yet implemented")
        Log.d("Adapter","onCreateViewHolder()")

        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.recycler_event_list_items,parent,false)
        return EventViewHolder(itemView,this.onEventClickListener)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int, model: EventModelUser) {
        // TODO("Not yet implemented")
        Log.d("Adapter","onBindViewHolder() for position $position")

        holder.bindEventData(model.profile)
    }

    override fun getItemCount(): Int {
        return recyclerOptions.snapshots.size
    }
}