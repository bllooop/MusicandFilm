package com.example.musicandfilm.ui.favorite

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
import com.bumptech.glide.Glide
import com.example.musicandfilm.R
import com.example.musicandfilm.models.events.FavoriteEvent
import com.example.musicandfilm.models.news.FavoriteNews
import com.google.firebase.database.FirebaseDatabase

class FavoriteNewsAdapter(private val news : List<FavoriteNews>):
    RecyclerView.Adapter<FavoriteNewsAdapter.FavoriteViewHolder>() {
    class FavoriteViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val bundle = Bundle()
        val database = FirebaseDatabase.getInstance("https://musicandfilm-5497b-default-rtdb.europe-west1.firebasedatabase.app")
        val favNews = database.getReference("News")
        val favorite: ImageButton = view.findViewById(R.id.fav)
        fun bindEvent(favoriteNews: FavoriteNews, context: Context) {
            val news_title = itemView.findViewById<TextView>(R.id.news_title)
            val news_date = itemView.findViewById<TextView>(R.id.news_date)
            val news_image = itemView.findViewById<ImageView>(R.id.news_image)
            news_title.text = favoriteNews.text.subSequence(0, 20).toString() + "..."
            news_date.text = favoriteNews.date
            Glide.with(itemView).load(favoriteNews.image).into(news_image)
            bundle.putString("id", favoriteNews.id)
            itemView.setOnClickListener {
                itemView.findNavController()
                    .navigate(R.id.action_navigation_favorite_to_navigation_news_details, bundle)
            }
            favorite.setOnClickListener {
                favNews.child(favoriteNews.unix).removeValue()
                Toast.makeText(context,"Новость удалена из избранного", Toast.LENGTH_SHORT).show()
            }
        }
    }
    override fun onBindViewHolder(holder: FavoriteNewsAdapter.FavoriteViewHolder, position: Int) {
        var context: Context = holder.itemView.context
        holder.bindEvent(news.get(position),context)
    }
    override fun getItemCount(): Int {
        return news.size
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteNewsAdapter.FavoriteViewHolder {
        return FavoriteNewsAdapter.FavoriteViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.news_item_fav, parent, false)
        )
    }
}