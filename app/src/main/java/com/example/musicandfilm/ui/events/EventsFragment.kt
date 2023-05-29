package com.example.musicandfilm.ui.events

import android.os.Bundle
import android.view.*
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.Toast
import androidx.core.view.isNotEmpty
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.musicandfilm.R
import com.example.musicandfilm.databinding.FragmentEventsBinding
import com.example.musicandfilm.models.events.Event
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.time.LocalDateTime
import java.util.*

class EventsFragment : Fragment() {
    private var _binding: FragmentEventsBinding? = null
    private val binding get() = _binding!!
    val arrayList = ArrayList<Event>()
    val displayList = ArrayList<Event>()
    lateinit var rv_events_list: RecyclerView
    private var progressBar: ProgressBar? = null

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
        rv_events_list = binding.rvEventsList
        progressBar = binding.progressBar
        progressBar!!.visibility = View.VISIBLE
        putEventsInRv()
        refreshApp()
    }

    private fun putEventsInRv(){
        progressBar!!.visibility = View.VISIBLE
        val viewModel = ViewModelProvider(this).get(EventViewModel::class.java)
        val unixTime = System.currentTimeMillis() / 1000;
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
        viewModel.getAllEvents(unixTime.toString())
        if (rv_events_list.isNotEmpty()) {  progressBar!!.visibility = View.INVISIBLE }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu,menu)
        val item = menu!!.findItem(R.id.search_action)
        val searchView = item?.actionView as SearchView
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
                            progressBar!!.visibility = View.INVISIBLE
                        }
                    }
                    rv_events_list.adapter!!.notifyDataSetChanged()
                } else {
                    displayList.clear()
                    displayList.addAll(arrayList)
                    if (rv_events_list.isNotEmpty()) {
                        rv_events_list.adapter!!.notifyDataSetChanged()
                    }
                }
                return true
            }
        })
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            R.id.sort_action ->{
                displayList.sortBy { it.title }
                rv_events_list.adapter!!.notifyDataSetChanged()
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun refreshApp(){
        val swipe_to_refresh: SwipeRefreshLayout = binding.swipeToRefresh
        swipe_to_refresh.setOnRefreshListener {
            putEventsInRv()
            swipe_to_refresh.isRefreshing = false
        }
    }
}