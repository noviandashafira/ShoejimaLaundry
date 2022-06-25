package com.ndp.picodiploma.shoejima

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.ndp.picodiploma.shoejima.databinding.ActivityGetStartedBinding
import com.ndp.picodiploma.shoejima.ui.LoginActivity

class GetStarted : AppCompatActivity() {

    private lateinit var binding : ActivityGetStartedBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGetStartedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        binding.btnStarted.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

    }
    override fun onStart() {
        super.onStart()
        checkUser()
    }

    private fun  checkUser() {
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }


}