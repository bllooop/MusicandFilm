package com.example.musicandfilm.ui.news

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musicandfilm.R
import com.example.musicandfilm.models.news.Attachments
import com.example.musicandfilm.models.news.FavoriteNews
import com.example.musicandfilm.models.news.Items
import com.example.musicandfilm.models.news.Response
import com.example.musicandfilm.ui.InsertingRoomViewModel
import com.example.musicandfilm.ui.profile.ProfileViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class NewsAdapter (  private val news: List<Items>
) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>(){
    class NewsViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        private val sdf = SimpleDateFormat("dd/MM/yyyy")
        private val minus = "-"
        val database = FirebaseDatabase.getInstance("https://musicandfilm-5497b-default-rtdb.europe-west1.firebasedatabase.app")
        var user = FirebaseAuth.getInstance().currentUser
        var userid = user!!.uid
        val favorite: ImageButton = view.findViewById(R.id.fav)
        val bundle = Bundle()
        val favNews = database.getReference("News")
        val attachment = ArrayList<Attachments>()

        fun bindNews(items: Items, context: Context) {
            val news_title = itemView.findViewById<TextView>(R.id.news_title)
            val news_image = itemView.findViewById<ImageView>(R.id.news_image)
            val news_date = itemView.findViewById<TextView>(R.id.news_date)
            var date = ""
            var image_link = ""
            if(items.text.isNotEmpty()) { news_title.text = items.text!!.subSequence(0, 25).toString() + "..."}
            val newdate = items.date
            val netDate = Date(newdate!!.toLong() * 1000)
            date = sdf.format(netDate).toString()
            news_date.text = sdf.format(netDate).toString()
           attachment.addAll(items.attachments)
            if (attachment.isNotEmpty()) {
                val attachments: Attachments = attachment.first()
                val type = attachments.type.toString()
                var image_url = items.attachments.toString()
                if (type == "video") {
                    var image_url = attachments.video!!.image.get(3)
                    image_link = image_url.url.toString()
                } else if (type == "photo") {
                    var image_url = attachments.photo!!.sizes.get(3)
                    image_link = image_url.url.toString()
                } else {
                    var image_url = attachments.link!!.photo!!.sizes.get(3)
                    image_link = image_url.url.toString()
                }
                Glide.with(itemView).load(image_link).into(news_image)
            }
            val postid = items.ownerId.toString() + "_" + items.id.toString()
             bundle.putString("id", postid)
            favorite.setOnClickListener {
              val mNews = FavoriteNews(userid,date,postid, items.text!!,image_link )
               favNews.child(postid).setValue(mNews)
                Toast.makeText(context,"Новость добавлена в избранное", Toast.LENGTH_SHORT).show()

            }
            itemView.setOnClickListener {
               itemView.findNavController()
                   .navigate(R.id.action_navigation_news_to_navigation_news_details, bundle)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent, false))
    }
    override fun getItemCount(): Int = news.size
    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        var context: Context =holder.itemView.context
        holder.bindNews(news.get(position),context)
    }
}