package com.example.musicandfilm.ui.login
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
import com.example.musicandfilm.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginFragment() : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var progressDialog: ProgressDialog
    private lateinit var firebaseAuth : FirebaseAuth
    private var email = ""
    private var password = ""
    val appContext = context
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val root: View = binding.root
        //return inflater.inflate(R.layout.fragment_main, container, false)
        return root
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()
        binding.regText.setOnClickListener {
            binding.regText.findNavController().navigate(R.id.action_navigation_login_to_navigation_register)
        }
        binding.logButton.setOnClickListener {
            validateData()
        }
    }
    private fun validateData(){
        email = binding.emails.text.toString()
        password = binding.passwords.text.toString().trim()
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.emails.error = "Некорректный формат"
        }
        else if ( TextUtils.isEmpty(password)){
            binding.passwords.error = "Введите пароль"
        } else {
            firebaseLogin()
        }
    }
    private fun firebaseLogin(){
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                binding.logButton.findNavController().navigate(R.id.action_navigation_login_to_navigation_register)
            }
            .addOnFailureListener { e->
                Toast.makeText(appContext,"Ошибка из-за ${e.message}", Toast.LENGTH_LONG).show()
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