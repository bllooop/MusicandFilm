package com.example.musicandfilm.ui.movie.details

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.example.musicandfilm.databinding.FragmentDetailsBinding
import com.example.musicandfilm.models.Comments
import com.example.musicandfilm.models.movies.MovieDetails
import com.example.musicandfilm.room.RecentHistory
import com.example.musicandfilm.ui.InsertingRoomViewModel
import com.example.musicandfilm.ui.events.details.CommentAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.ArrayList


class DetailsFragment : Fragment() {
    private val IMAGE_BASE = "https://image.tmdb.org/t/p/w500/"
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private var comment = ""
    private val sdf = SimpleDateFormat("dd/MM/yyyy")
    private lateinit var firebaseAuth : FirebaseAuth
    private lateinit var commentsArrayList: ArrayList<Comments>
    val database = FirebaseDatabase.getInstance("https://musicandfilm-5497b-default-rtdb.europe-west1.firebasedatabase.app")
    val comments = database.getReference("Comments/Movies")
    private lateinit var viewModel: DetailsViewModel
    lateinit  var ratingBar: RatingBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        setHasOptionsMenu(true)
        //return inflater.inflate(R.layout.fragment_main, container, false)
        return root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ratingBar = binding.ratingBar
        val args = this.arguments
        val input = args?.getString("id")
        val id = input!!.toInt()
        val viewModel = ViewModelProvider(this).get(DetailsViewModel::class.java)
        viewModel.getLiveDataObserver().observe(viewLifecycleOwner, Observer {
            bindMovie(it)
        })
        viewModel.getMovie(id)
        binding.send.setOnClickListener {
            addComment(id)
        }
        viewComments(id)
        refreshApp(id)
    }


    private fun addComment(id: Int){
        var user = FirebaseAuth.getInstance().currentUser
        firebaseAuth = FirebaseAuth.getInstance()
        var userid = user!!.uid
        comment = binding.commentText.text.toString().trim()
        val email = firebaseAuth.currentUser!!.email.toString()
        val unixTime = System.currentTimeMillis() / 1000;
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val current = LocalDateTime.now().format(formatter)
        val mComment = Comments(userid,id.toString(),email, ratingBar.rating.toString(), "Movies", comment,current.toString(), unixTime.toString())
        comments.child(unixTime.toString()).setValue(mComment)
        Toast.makeText(context,"Комментарий опубликован", Toast.LENGTH_SHORT).show()
    }

    private fun viewComments(id: Int){
        val rv_comments_list: RecyclerView = binding.rvCommentsList
        firebaseAuth = FirebaseAuth.getInstance()
        val firebaseUser = firebaseAuth.currentUser
        var userid = firebaseUser!!.uid
        commentsArrayList = arrayListOf<com.example.musicandfilm.models.Comments>()
        rv_comments_list.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL,false)
        rv_comments_list.setHasFixedSize(true)
        val adapter = CommentAdapter(commentsArrayList)
        rv_comments_list.adapter = adapter
        comments.orderByChild("id").equalTo(id.toString()).addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (commentsSnapshot in snapshot.children) {
                        val mComment = commentsSnapshot.getValue(com.example.musicandfilm.models.Comments::class.java)
                        commentsArrayList.add(mComment!!)
                    }
                    adapter.notifyDataSetChanged()
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
    fun bindMovie(movies: MovieDetails){
        firebaseAuth = FirebaseAuth.getInstance()
        val firebaseUser = firebaseAuth.currentUser
        val movie_title: TextView = binding.movieTitle
        val movie_runtime: TextView = binding.movieRuntime
        val movie_status: TextView = binding.movieStatus
        val movie_vote_average: TextView = binding.movieVoteAverage
        val movie_poster: ImageView = binding.moviePoster
        val movie_release_date: TextView = binding.movieReleaseDate
        val description: TextView = binding.movieText
        movie_title.text = movies.title
        movie_release_date.text = movies.releaseDate
        movie_runtime.text = movies.runtime.toString() + " мин"
        movie_status.text = "Выпущен"
        movie_vote_average.text = movies.voteAverage.toString()
        description.text = movies.overview
        Glide.with(this).load(IMAGE_BASE + movies.posterPath).into(movie_poster)
        val image_link = IMAGE_BASE + movies.posterPath
        val viewModel = ViewModelProvider(this).get(InsertingRoomViewModel::class.java)
        viewModel.insert(RecentHistory(title = movies.title,date = movies.releaseDate, type_id = movies.id.toString(), image = image_link, type = "movies", userid = firebaseUser!!.email.toString()))
    }

    private fun refreshApp(id: Int){
        val swipe_to_refresh: SwipeRefreshLayout = binding.swipeToRefresh
        swipe_to_refresh.setOnRefreshListener {
            viewComments(id)
            swipe_to_refresh.isRefreshing = false
        }
    }
}