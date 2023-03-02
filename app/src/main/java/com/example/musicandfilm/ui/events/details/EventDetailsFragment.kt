package com.example.musicandfilm.ui.events.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.musicandfilm.databinding.FragmentEventDetailsBinding
import com.example.musicandfilm.databinding.FragmentEventsBinding
import com.example.musicandfilm.models.Event
import com.example.musicandfilm.ui.movie.details.DetailsViewModel
import java.util.ArrayList

class EventDetailsFragment : Fragment() {
    private var _binding: FragmentEventDetailsBinding? = null
    private val binding get() = _binding!!
    val arrayList = ArrayList<Event>()
    val displayList = ArrayList<Event>()

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
        val event_title: TextView = binding.eventTitle
        event_title.text = id.toString()


    }
}