package com.example.musicandfilm.ui.events

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musicandfilm.R
import com.example.musicandfilm.models.events.Event
import com.example.musicandfilm.models.events.FavoriteEvent
import com.example.musicandfilm.models.movies.FavoriteMovie
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*

class EventAdapter(
    private val events: List<Event>
) : RecyclerView.Adapter<EventAdapter.EventViewHolder>(){
    class EventViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        private val IMAGE_BASE = "https://kudago.com/media/images/event/"
        private val sdf = SimpleDateFormat("dd/MM/yyyy")
        private val minus = "-"
        val database = FirebaseDatabase.getInstance("https://musicandfilm-5497b-default-rtdb.europe-west1.firebasedatabase.app")
        var user = FirebaseAuth.getInstance().currentUser
        var userid = user!!.uid
        val favorite: ImageButton = view.findViewById(R.id.fav)
        val bundle = Bundle()
        val favEvent = database.getReference("Events")

        fun bindEvent(event: Event, context: Context) {
            val event_title = itemView.findViewById<TextView>(R.id.event_title)
            val event_poster = itemView.findViewById<ImageView>(R.id.event_image)
            val event_date = itemView.findViewById<TextView>(R.id.event_date)
            var date = ""
            val image_url = event.event_images.toString().removeRange(0,46)
            val imageev = image_url.subSequence(0, image_url.indexOf(',')).toString()
            event_title.text = event.title.capitalize()
            val datestr = event.dates.last().toString().removeRange(0,12)
            if (datestr[0].toString() == minus) {
                date = datestr.removeRange(0,18)
                val newdate = date.substringBefore(")")
                val netDate = Date(newdate.toLong() * 1000)
                date = "До " + sdf.format(netDate)
                event_date.text = date
            } else {
                date = datestr.removeRange(0,16)
                val newdate = date.substringBefore(")")
                val netDate = Date(newdate.toLong() * 1000)
                date = "До " + sdf.format(netDate)
                event_date.text = date
            }
            Glide.with(itemView).load(IMAGE_BASE + imageev).into(event_poster)
            bundle.putString("id", event.id)
            favorite.setOnClickListener {
                val mEvent = FavoriteEvent(userid,event.id,event.title,imageev,date)
                favEvent.child(event.id).setValue(mEvent)
                Toast.makeText(context,"Мероприятие добавлено в избранное", Toast.LENGTH_SHORT).show()
            }
            itemView.setOnClickListener {
                itemView.findNavController()
                    .navigate(R.id.action_navigation_event_to_navigation_event_details, bundle)
            }
           // event_date.text = datestr
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        return EventViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.event_item, parent, false))
    }
    override fun getItemCount(): Int = events.size
    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        var context: Context =holder.itemView.context
        holder.bindEvent(events.get(position),context)
    }
}