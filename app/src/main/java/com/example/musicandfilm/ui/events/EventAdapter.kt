package com.example.musicandfilm.ui.events

import android.content.Context
import android.media.Image
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
import com.example.musicandfilm.models.Event
import com.example.musicandfilm.models.Images
import java.text.SimpleDateFormat
import java.util.*

class EventAdapter(
    private val events: List<Event>
) : RecyclerView.Adapter<EventAdapter.EventViewHolder>(){
    class EventViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        private val IMAGE_BASE = "https://kudago.com/media/images/event/"
        private val sdf = SimpleDateFormat("dd/MM/yyyy")
        private val minus = "-"
        val favorite: ImageButton = view.findViewById(R.id.fav)
        val bundle = Bundle()
        fun bindEvent(event: Event, context: Context) {
            val event_title = itemView.findViewById<TextView>(R.id.event_title)
            val event_poster = itemView.findViewById<ImageView>(R.id.event_image)
            val event_date = itemView.findViewById<TextView>(R.id.event_date)
            val image_url = event.event_images.toString().removeRange(0,46)
            val imageev = image_url.subSequence(0, image_url.indexOf(','))
            event_title.text = event.title.capitalize()
            val datestr = event.dates.last().toString().removeRange(0,12)
            if (datestr[0].toString() == minus) {
                val date = datestr.removeRange(0,18)
                val newdate = date.substringBefore(")")
                val netDate = Date(newdate.toLong() * 1000)
                event_date.text = "До " + sdf.format(netDate)
            } else {
                val date = datestr.removeRange(0,16)
                val newdate = date.substringBefore(")")
                val netDate = Date(newdate.toLong() * 1000)
                event_date.text = "До " + sdf.format(netDate)
            }
            val netDate = Date("1681160400".toLong() * 1000)
            Glide.with(itemView).load(IMAGE_BASE + imageev).into(event_poster)
            bundle.putString("id", event.id)
            favorite.setOnClickListener {
                Toast.makeText(context,sdf.format(netDate), Toast.LENGTH_SHORT).show()
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