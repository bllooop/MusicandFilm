package com.example.musicandfilm.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.musicandfilm.R
import com.example.musicandfilm.databinding.FragmentFavoriteBinding
import com.example.musicandfilm.models.events.FavoriteEvent
import com.example.musicandfilm.models.movies.FavoriteMovie
import com.example.musicandfilm.models.news.FavoriteNews
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FavoriteFragment : Fragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private lateinit var mFavoriteViewModel: FavoriteViewModel
    private lateinit var movieArrayList:ArrayList<FavoriteMovie>
    private lateinit var eventArrayList:ArrayList<FavoriteEvent>
    private lateinit var newsArrayList:ArrayList<FavoriteNews>

    private lateinit var firebaseAuth : FirebaseAuth

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        val root: View = binding.root
        setHasOptionsMenu(true)
        //return inflater.inflate(R.layout.fragment_main, container, false)
        return root
    }
    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        putIntoFavRv()
        refreshApp()

    }
    private fun putIntoFavRv(){
        val rv_movies_list: RecyclerView = binding.rvMoviesList
        val rv_events_list: RecyclerView = binding.rvEventsList
        val rv_news_list: RecyclerView = binding.rvNewsList
        firebaseAuth = FirebaseAuth.getInstance()
        val firebaseUser = firebaseAuth.currentUser
        var userid = firebaseUser!!.uid
        val empty: TextView=binding.empty
        val empty1: TextView=binding.empty1
        val empty2: TextView=binding.empty2
        movieArrayList= arrayListOf<FavoriteMovie>()
        eventArrayList = arrayListOf<FavoriteEvent>()
        newsArrayList = arrayListOf<FavoriteNews>()
        rv_movies_list.layoutManager = LinearLayoutManager(activity,RecyclerView.HORIZONTAL,false)
        rv_movies_list.setHasFixedSize(true)
        rv_events_list.layoutManager = LinearLayoutManager(activity,RecyclerView.HORIZONTAL,false)
        rv_events_list.setHasFixedSize(true)
        rv_news_list.layoutManager = LinearLayoutManager(activity,RecyclerView.HORIZONTAL,false)
        rv_news_list.setHasFixedSize(true)
        val adapter = FavoriteAdapter(movieArrayList)
        rv_movies_list.adapter = adapter
        val adapter_ev = FavoriteEventAdapter(eventArrayList)
        rv_events_list.adapter = adapter_ev
        val adapter_ns = FavoriteNewsAdapter(newsArrayList)
        rv_news_list.adapter = adapter_ns
        val database = FirebaseDatabase.getInstance("https://musicandfilm-5497b-default-rtdb.europe-west1.firebasedatabase.app")
        val favMovie = database.getReference("Movies")
        favMovie.orderByChild("userid").equalTo(userid).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (moviesSnapshot in snapshot.children) {
                        val mMovie = moviesSnapshot.getValue(FavoriteMovie::class.java)
                        movieArrayList.add(mMovie!!)
                    }
                    adapter.notifyDataSetChanged()
                    empty.isVisible = adapter.getItemCount() == 0
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
        val favEvent = database.getReference("Events")
        favEvent.orderByChild("userid").equalTo(userid).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (moviesSnapshot in snapshot.children) {
                        val mEvent = moviesSnapshot.getValue(FavoriteEvent::class.java)
                        eventArrayList.add(mEvent!!)
                    }
                    adapter_ev.notifyDataSetChanged()
                    empty1.isVisible = adapter.getItemCount() == 0
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
        val favNews = database.getReference("News")
        favNews.orderByChild("userid").equalTo(userid).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (newsSnapshot in snapshot.children) {
                        val mNews = newsSnapshot.getValue(FavoriteNews::class.java)
                        newsArrayList.add(mNews!!)
                    }
                    adapter_ns.notifyDataSetChanged()
                    empty2.isVisible = adapter.getItemCount() == 0
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
    private fun refreshApp(){
        val swipe_to_refresh: SwipeRefreshLayout = binding.swipeToRefresh
        swipe_to_refresh.setOnRefreshListener {
            putIntoFavRv()
            swipe_to_refresh.isRefreshing = false
        }
    }
}