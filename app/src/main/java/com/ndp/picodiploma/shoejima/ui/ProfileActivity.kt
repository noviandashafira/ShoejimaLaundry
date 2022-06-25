package com.ndp.picodiploma.shoejima.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.ndp.picodiploma.shoejima.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val name = intent.getStringExtra("Name")
        val number = intent.getStringExtra("Number")
        binding.tvName.text = name
        binding.tvPhone.text = number

        supportActionBar?.hide()
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        binding.btnLogout.setOnClickListener {
            firebaseAuth.signOut()
           val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }
        binding.tvRiwayat.setOnClickListener {
            startActivity(Intent(this, RiwayatActivity::class.java))
        }

    }

    private fun checkUser() {
        val email = firebaseAuth.currentUser!!.email
        if (firebaseAuth.currentUser != null ) {
            binding.tvEmail.text = email
        }
    }
}