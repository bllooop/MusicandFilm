package com.example.musicandfilm.ui.news.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.example.musicandfilm.databinding.FragmentNewsBinding
import com.example.musicandfilm.databinding.FragmentNewsDetailsBinding
import com.example.musicandfilm.models.Comments
import com.example.musicandfilm.models.events.EventDetail
import com.example.musicandfilm.models.news.Attachments
import com.example.musicandfilm.models.news.Items
import com.example.musicandfilm.room.RecentHistory
import com.example.musicandfilm.ui.InsertingRoomViewModel
import com.example.musicandfilm.ui.events.details.CommentAdapter
import com.example.musicandfilm.ui.events.details.EventDetailsViewModel
import com.example.musicandfilm.ui.profile.ProfileViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.*

class NewsDetailsFragment () : Fragment() {
    private var _binding: FragmentNewsDetailsBinding? = null
    private val binding get() = _binding!!
    private var comment = ""
    private lateinit var firebaseAuth : FirebaseAuth
    private lateinit var commentsArrayList: ArrayList<Comments>
    val database = FirebaseDatabase.getInstance("https://musicandfilm-5497b-default-rtdb.europe-west1.firebasedatabase.app")
    val comments = database.getReference("Comments/News")
    val arrayList = ArrayList<Items>()
    val attachment = ArrayList<Attachments>()
    private val sdf = SimpleDateFormat("dd/MM/yyyy")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewsDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        setHasOptionsMenu(true)
        //return inflater.inflate(R.layout.fragment_main, container, false)
        return root
    }
    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        val args = this.arguments
        val id = args?.getString("id").toString()
        val viewModel = ViewModelProvider(this).get(NewsDetailsViewModel::class.java)
        viewModel.getSingleNewsData(id).observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            arrayList.addAll(it)
            bindNews(arrayList.get(0))
        })
        binding.send.setOnClickListener {
            addComment(id)
        }
        viewComments(id)
        refreshApp(id)
    }



    private fun addComment(id: String){
        var user = FirebaseAuth.getInstance().currentUser
        firebaseAuth = FirebaseAuth.getInstance()
        var userid = user!!.uid
        comment = binding.commentText.text.toString().trim()
        val email = firebaseAuth.currentUser!!.email.toString()
        val mComment = com.example.musicandfilm.models.Comments(userid,id.toString(),email, comment)
        comments.child(id).setValue(mComment)
        Toast.makeText(context,"Комментарий опубликован", Toast.LENGTH_SHORT).show()
    }

    private fun viewComments(id: String){
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
    private fun refreshApp(id: String){
        val swipe_to_refresh: SwipeRefreshLayout = binding.swipeToRefresh
        swipe_to_refresh.setOnRefreshListener {
            viewComments(id)
            swipe_to_refresh.isRefreshing = false
        }
    }

    fun bindNews(items: Items){
        val news_date: TextView = binding.newsDate
        val news_image: ImageView = binding.newsImage
        val news_text: TextView = binding.newsText
        var image_link = ""
        attachment.addAll(items.attachments)
        val attachments: Attachments = attachment.get(0)
        val type  = attachments.type.toString()
        //  var image_url = items.attachments.toString()
        if (type == "video") {
            var image_url = attachments.video!!.image.get(3)
            image_link = image_url.url.toString()
        }
        else if (type =="photo"){
            var image_url = attachments.photo!!.sizes.get(3)
            image_link = image_url.url.toString()
        }
        else {
            var image_url = attachments.link!!.photo!!.sizes.get(3)
            image_link = image_url.url.toString()
        }
        news_text.text = items.text
        val newdate = items.date
        val netDate = Date(newdate!!.toLong() * 1000)
        val date = sdf.format(netDate).toString()
        news_date.text =  sdf.format(netDate).toString()
        Glide.with(this).load(image_link).into(news_image)

        val viewModel = ViewModelProvider(this).get(InsertingRoomViewModel::class.java)
        viewModel.insert(RecentHistory(title = items.text.toString(),date = date, type_id = items.id.toString(), image = image_link, type = "news", userid = "1"))
    }
}