package com.example.musicandfilm.ui.events

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musicandfilm.R
import com.example.musicandfilm.databinding.FragmentEventsBinding
import com.example.musicandfilm.models.Event
import java.util.*

class EventsFragment : Fragment() {
    private var _binding: FragmentEventsBinding? = null
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
        _binding = FragmentEventsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        setHasOptionsMenu(true)
        //return inflater.inflate(R.layout.fragment_main, container, false)
        return root
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        val viewModel = ViewModelProvider(this).get(EventViewModel::class.java)
        val rv_events_list: RecyclerView = binding.rvEventsList
        viewModel.getLiveDataObserver().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            rv_events_list.layoutManager = LinearLayoutManager(activity)
            rv_events_list.setHasFixedSize(true)
            //  getMovieData { movies: List<Movie> ->
            arrayList.clear()
            arrayList.addAll(it)
            displayList.clear()
            displayList.addAll(arrayList)
            var adapter = EventAdapter(displayList)
            rv_events_list.adapter = adapter
        })
        viewModel.getEventData()
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu,menu)
        val rv_events_list: RecyclerView = binding.rvEventsList
        val item = menu!!.findItem(R.id.search_action)
        if (item!=null) {
            val searchView = item?.actionView as SearchView
            //   displayList.addAll(it)
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }
                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText!!.isNotEmpty()) {
                        displayList.clear()
                        val search = newText.toLowerCase(Locale.getDefault())
                        arrayList.forEach {
                            if (it.title.toLowerCase(Locale.getDefault()).contains(search)) {
                                displayList.add(it)
                                //  Log.d("MyLog", "test " + it.toString())
                            }
                        }
                        rv_events_list.adapter!!.notifyDataSetChanged()
                    } else {
                        displayList.clear()
                        displayList.addAll(arrayList)
                       // rv_events_list.adapter!!.notifyDataSetChanged()
                    }
                    return true
                }
            })
        }
        return super.onCreateOptionsMenu(menu, inflater)
    }
}