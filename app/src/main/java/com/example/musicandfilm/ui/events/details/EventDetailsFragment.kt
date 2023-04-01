package com.example.musicandfilm.ui.events.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musicandfilm.databinding.FragmentEventDetailsBinding
import com.example.musicandfilm.models.events.EventDetail
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.*

class EventDetailsFragment : Fragment() {
    private var _binding: FragmentEventDetailsBinding? = null
    private val binding get() = _binding!!
    private val sdf = SimpleDateFormat("dd/MM/yyyy")
    private val IMAGE_BASE = "https://kudago.com/media/images/event/"
    private var comment = ""
    private lateinit var firebaseAuth : FirebaseAuth
    private lateinit var commentsArrayList:ArrayList<com.example.musicandfilm.models.Comments>
    val database = FirebaseDatabase.getInstance("https://musicandfilm-5497b-default-rtdb.europe-west1.firebasedatabase.app")
    val comments = database.getReference("Comments/Events")

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
        binding.send.setOnClickListener {
            addComment(id)
        }
        viewComments(id)
    }

    private fun addComment(id: Int){
        var user = FirebaseAuth.getInstance().currentUser
        firebaseAuth = FirebaseAuth.getInstance()
        var userid = user!!.uid
        comment = binding.commentText.text.toString().trim()
        val email = firebaseAuth.currentUser!!.email.toString()
        val mComment = com.example.musicandfilm.models.Comments(userid,id.toString(),email, comment)
        comments.child(id.toString()).setValue(mComment)
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