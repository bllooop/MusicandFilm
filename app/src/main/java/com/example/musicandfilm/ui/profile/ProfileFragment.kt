package com.example.musicandfilm.ui.profile

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musicandfilm.LoggedActivity
import com.example.musicandfilm.MainActivity
import com.example.musicandfilm.R
import com.example.musicandfilm.databinding.FragmentProfileBinding
import com.example.musicandfilm.databinding.FragmentRegisterBinding
import com.example.musicandfilm.ui.events.EventAdapter
import com.example.musicandfilm.ui.events.EventViewModel
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var progressDialog: ProgressDialog
    private lateinit var firebaseAuth : FirebaseAuth
    private var email = ""
    private var password = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root
        //return inflater.inflate(R.layout.fragment_main, container, false)
        return root
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()
        binding.logout.setOnClickListener {
            firebaseAuth.signOut()
            checkUser()
        }
        init()

    }

    private fun init() {
        var rv_recents_list: RecyclerView = binding.rvHistoryList
        rv_recents_list.layoutManager = LinearLayoutManager(activity)
        rv_recents_list.setHasFixedSize(true)
        val viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        viewModel.initDatabase()
        viewModel.getAllRecents().observe(this,{listRecents ->
            listRecents.asReversed()
            var adapter = ProfileAdapter(listRecents)
            rv_recents_list.adapter = adapter
        })
    }

    private fun checkUser(){
        val firebaseUser = firebaseAuth.currentUser
        if(firebaseUser!=null){
            val email = firebaseUser.email
            binding.emailname.text = email
        }
        if(firebaseUser==null){
            activity?.let {
                startActivity(Intent(it, MainActivity::class.java))
            }
        }
    }
}