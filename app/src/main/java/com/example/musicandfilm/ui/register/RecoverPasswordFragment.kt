package com.example.musicandfilm.ui.register

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.musicandfilm.LoggedActivity
import com.example.musicandfilm.R
import com.example.musicandfilm.databinding.FragmentRecoverBinding
import com.example.musicandfilm.databinding.FragmentRegisterBinding
import com.google.firebase.auth.FirebaseAuth

class RecoverPasswordFragment(): Fragment() {
    private var _binding: FragmentRecoverBinding? = null
    private val binding get() = _binding!!
    private lateinit var progressDialog: ProgressDialog
    private lateinit var firebaseAuth : FirebaseAuth
    private var email = ""


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecoverBinding.inflate(inflater, container, false)
        val root: View = binding.root
        //return inflater.inflate(R.layout.fragment_main, container, false)
        return root
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()
        binding.logText.setOnClickListener {
            binding.logText.findNavController().navigate(R.id.action_navigation_recover_to_navigation_login)
        }
        binding.regText.setOnClickListener {
            binding.regText.findNavController().navigate(R.id.action_navigation_recover_to_navigation_register)
        }
        binding.regButton.setOnClickListener {
            validateData()
        }
    }
    private fun validateData(){
        email = binding.email.text.toString().trim()
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.email.error = "Некорректный формат"
        }
        else {
            firebaseReset()
        }
    }
    private fun firebaseReset(){
        firebaseAuth.sendPasswordResetEmail(email)
            .addOnSuccessListener {
                activity?.let {
                    Toast.makeText(it, "Отправлено письмо с ссылкой для восстановления", Toast.LENGTH_LONG)
                        .show()
                    startActivity(Intent(it, LoggedActivity::class.java))
                }
            }
            .addOnFailureListener { e->
                activity?.let {
                    Toast.makeText(it, "Ошибка из-за ${e.message}", Toast.LENGTH_LONG)
                        .show()
                }
            }
    }
    private fun checkUser(){
        val firebaseUser = firebaseAuth.currentUser
        activity?.let {
            if (firebaseUser != null) {
                startActivity(Intent(it, LoggedActivity::class.java))
            }
        }
    }
}
