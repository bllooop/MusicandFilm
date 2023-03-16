package com.example.musicandfilm.ui.favorite

import android.app.Application
import androidx.lifecycle.*
import com.example.musicandfilm.models.movies.FavoriteMovie
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FavoriteViewModel(application: Application) : AndroidViewModel(application)  {
    var moviess = MutableLiveData<List<FavoriteMovie>>()
    var mediatorLiveData = MediatorLiveData<List<FavoriteMovie>>()

    fun getLiveDataObserver():MutableLiveData<List<FavoriteMovie>>{
        moviess = getMovie()
        return moviess
    }

    fun getMovie(): MutableLiveData<List<FavoriteMovie>> {
        val firebaseUser = FirebaseAuth.getInstance().currentUser
        var userid = firebaseUser!!.uid
        val database = Firebase.database
        val favMovie = database.getReference("Movies")
        favMovie.orderByChild("userid").equalTo(userid).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (moviesSnapshot in snapshot.children) {
                        val mMovie = moviesSnapshot.getValue(FavoriteMovie::class.java)
                        mediatorLiveData.postValue(listOf(mMovie!!))
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
        return mediatorLiveData
    }

}