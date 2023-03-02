package com.example.musicandfilm.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musicandfilm.R
import com.example.musicandfilm.databinding.FragmentFavoriteBinding
import com.example.musicandfilm.models.FavoriteMovie
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FavoriteFragment : Fragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private lateinit var mFavoriteViewModel: FavoriteViewModel
    private lateinit var movieArrayList:ArrayList<FavoriteMovie>
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
        firebaseAuth = FirebaseAuth.getInstance()
        val firebaseUser = firebaseAuth.currentUser
        var userid = firebaseUser!!.uid
        val empty: TextView=itemView.findViewById(R.id.empty)
        movieArrayList= arrayListOf<FavoriteMovie>()
        rv_movies_list.layoutManager = LinearLayoutManager(activity)
        rv_movies_list.setHasFixedSize(true)
        val adapter = FavoriteAdapter(movieArrayList)
        rv_movies_list.adapter = adapter
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
    }
}