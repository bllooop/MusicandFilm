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
import com.example.musicandfilm.models.events.Dates
import com.example.musicandfilm.models.events.Event
import com.example.musicandfilm.models.events.FavoriteEvent
import com.example.musicandfilm.models.events.Images
import com.example.musicandfilm.models.movies.FavoriteMovie
import com.example.musicandfilm.models.news.Attachments
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

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
        val images = ArrayList<Images>()
        val dates = ArrayList<Dates>()

        fun bindEvent(event: Event, context: Context) {
            val event_title = itemView.findViewById<TextView>(R.id.event_title)
            val event_poster = itemView.findViewById<ImageView>(R.id.event_image)
            val event_date = itemView.findViewById<TextView>(R.id.event_date)
            var date = ""
            images.addAll(event.images)
            dates.addAll(event.dates)
            val images: Images = images.get(0)
            val dates: Dates = dates.last()
            val image_url = images.image
            event_title.text = event.title.capitalize()
                val netDate = Date(dates.end.toLong() * 1000)
                date = "До " + sdf.format(netDate)
                event_date.text = date
            Glide.with(itemView).load(image_url).into(event_poster)
            val unixTime = System.currentTimeMillis() / 1000
            bundle.putString("id", event.id)
            favorite.setOnClickListener {
                val mEvent = FavoriteEvent(userid,event.id,event.title,image_url,date, unixTime.toString())
                favEvent.child(unixTime.toString()).setValue(mEvent)
                Toast.makeText(context,"Мероприятие добавлено в избранное", Toast.LENGTH_SHORT).show()
            }
            itemView.setOnClickListener {
                itemView.findNavController()
                    .navigate(R.id.action_navigation_event_to_navigation_event_details, bundle)
            }
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