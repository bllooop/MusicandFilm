package com.example.musicandfilm.ui.events.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.musicandfilm.databinding.FragmentEventDetailsBinding
import com.example.musicandfilm.models.events.EventDetail
import java.text.SimpleDateFormat
import java.util.*

class EventDetailsFragment : Fragment() {
    private var _binding: FragmentEventDetailsBinding? = null
    private val binding get() = _binding!!
    private val sdf = SimpleDateFormat("dd/MM/yyyy")
    private val IMAGE_BASE = "https://kudago.com/media/images/event/"

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEventDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        setHasOptionsMenu(true)
        //return inflater.inflate(R.layout.fragment_main, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = this.arguments
        val input = args?.getString("id")
        val id = input!!.toInt()
        val viewModel = ViewModelProvider(this).get(EventDetailsViewModel::class.java)
        viewModel.getSingleEventDetail(id).observe(viewLifecycleOwner, Observer {
           bindEvent(it)
        })

    }
    fun bindEvent(events: EventDetail){
        val event_title: TextView = binding.eventTitle
        val event_age: TextView = binding.eventAge
        val event_location: TextView = binding.location
        val event_date: TextView = binding.eventDate
        val event_poster: ImageView = binding.eventImage
        val event_description: TextView = binding.bodyText
        val datestr = events.dates.last().toString().removeRange(0,12)
        if (datestr[0].toString() == "-") {
            val date = datestr.removeRange(0,18)
            val newdate = date.substringBefore(")")
            val netDate = Date(newdate.toLong() * 1000)
            event_date.text = sdf.format(netDate)
        } else {
            val date = datestr.removeRange(0,16)
            val newdate = date.substringBefore(")")
            val netDate = Date(newdate.toLong() * 1000)
            event_date.text = sdf.format(netDate)
        }
        val image_url = events.images.toString().removeRange(0,46)
        val imageev = image_url.subSequence(0, image_url.indexOf(','))
        event_title.text = events.title
        event_age.text = events.ageRestriction
        val eventurl = events.location.toString().removeRange(0,16)
        event_location.text =  eventurl.subSequence(0, eventurl.indexOf(','))
        event_description.text = events.bodyText.replace("\\<.*?\\>".toRegex(), "")
        Glide.with(this).load(IMAGE_BASE + imageev).into(event_poster)
    }

}