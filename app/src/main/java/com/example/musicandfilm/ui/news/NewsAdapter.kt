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
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.musicandfilm.R
import com.example.musicandfilm.models.news.Items
import com.example.musicandfilm.models.news.Response
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*

class NewsAdapter (  private val news: List<Items>
) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>(){
    class NewsViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        private val IMAGE_BASE = "https://kudago.com/media/images/event/"
        private val sdf = SimpleDateFormat("dd/MM/yyyy")
        private val minus = "-"
        val database = FirebaseDatabase.getInstance("https://musicandfilm-5497b-default-rtdb.europe-west1.firebasedatabase.app")
        var user = FirebaseAuth.getInstance().currentUser
        var userid = user!!.uid
        val favorite: ImageButton = view.findViewById(R.id.fav)
        val bundle = Bundle()
        val favEvent = database.getReference("News")

        fun bindEvent(items: Items, context: Context) {
            val news_title = itemView.findViewById<TextView>(R.id.news_title)
            val news_image = itemView.findViewById<ImageView>(R.id.news_image)
            val news_date = itemView.findViewById<TextView>(R.id.news_date)
            var date = ""
            //val image_url = news..toString().removeRange(0,46)
           // val imageev = image_url.subSequence(0, image_url.indexOf(',')).toString()
         //   news_title.text = news.title.capitalize()
           // news_title.text = news.date.toString()
            news_title.text = items.date.toString()
       //     val newdate = news.date
             //   val netDate = Date(newdate.toLong() * 1000)
             //   date = "До " + sdf.format(netDate)
                news_date.text = "eegeg"
        //    Glide.with(itemView).load(IMAGE_BASE + imageev).into(news_image)
           // bundle.putString("id", event.id)
            favorite.setOnClickListener {
              //  val mEvent = FavoriteEvent(userid,event.id,event.title,imageev,date)
               // favEvent.child(event.id).setValue(mEvent)
                Toast.makeText(context,"Мероприятие добавлено в избранное", Toast.LENGTH_SHORT).show()
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
        holder.bindEvent(news.get(position),context)
    }
}