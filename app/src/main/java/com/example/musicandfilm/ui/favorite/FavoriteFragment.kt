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
import com.example.musicandfilm.R
import com.example.musicandfilm.databinding.FragmentFavoriteBinding
import com.example.musicandfilm.models.events.FavoriteEvent
import com.example.musicandfilm.models.movies.FavoriteMovie
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
        val rv_movies_list: RecyclerView = binding.rvMoviesList
        val rv_events_list: RecyclerView = binding.rvEventsList
        firebaseAuth = FirebaseAuth.getInstance()
        val firebaseUser = firebaseAuth.currentUser
        var userid = firebaseUser!!.uid
        val empty: TextView=itemView.findViewById(R.id.empty)
        val empty1: TextView=itemView.findViewById(R.id.empty1)
        val empty2: TextView=itemView.findViewById(R.id.empty2)
        movieArrayList= arrayListOf<FavoriteMovie>()
        eventArrayList = arrayListOf<FavoriteEvent>()
        rv_movies_list.layoutManager = LinearLayoutManager(activity,RecyclerView.HORIZONTAL,false)
        rv_movies_list.setHasFixedSize(true)
        rv_events_list.layoutManager = LinearLayoutManager(activity,RecyclerView.HORIZONTAL,false)
        rv_events_list.setHasFixedSize(true)
        val adapter = FavoriteAdapter(movieArrayList)
        rv_movies_list.adapter = adapter
        val adapter_ev = FavoriteEventAdapter(eventArrayList)
        rv_events_list.adapter = adapter_ev
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
                    adapter.notifyDataSetChanged()
                    empty1.isVisible = adapter.getItemCount() == 0
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
}