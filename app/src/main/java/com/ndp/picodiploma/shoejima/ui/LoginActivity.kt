package com.ndp.picodiploma.shoejima.ui

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.ndp.picodiploma.shoejima.MainActivity
import com.ndp.picodiploma.shoejima.R
import com.ndp.picodiploma.shoejima.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var progressDialog : ProgressDialog
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var email : String
    private lateinit var password : String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //configure action bar
        supportActionBar?.hide()

        progressDialog()

        //initialization firebase
        firebaseAuth = FirebaseAuth.getInstance()
//        checkUser()

        //go To Register
        binding.tvSignUp.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
        binding.btnLogin.setOnClickListener {
            validate()
        }

    }

    private fun validate(){
        //getData
        email = binding.etEmail.text.toString().trim()
        password = binding.etEmail.text.toString().trim()

        //validate
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etEmail.error = "Invalid email format"
        }else if (TextUtils.isEmpty(password)) {
            binding.etPassword.error = "this field cannot be empty"
        }
        else {
            statusLogin()
        }
    }

    private fun progressDialog(){
        //configure progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please Wait")
        progressDialog.setMessage("Logging in...")
        progressDialog.setCanceledOnTouchOutside(false)
    }

    private fun statusLogin() {
        progressDialog.show()
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                //Login Success
                progressDialog.dismiss()
                //get User info
                val firebaseUser = firebaseAuth.currentUser
                val emailUser = firebaseUser!!.email
                Toast.makeText(this, "Logged In as $emailUser", Toast.LENGTH_SHORT).show()

                //intent Main Activity
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            .addOnFailureListener {
                progressDialog.show()
                Toast.makeText(this, "Login failed due to ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }
//    override fun onStart() {
//        super.onStart()
//        checkUser()
//    }
//
//    private fun  checkUser() {
//        val firebaseUser = firebaseAuth.currentUser
//        if (firebaseUser != null) {
//            startActivity(Intent(this, MainActivity::class.java))
//            finish()
//        }
//    }


}
