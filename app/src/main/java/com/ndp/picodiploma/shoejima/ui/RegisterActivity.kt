package com.ndp.picodiploma.shoejima.ui

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.google.firebase.auth.FirebaseAuth
import com.ndp.picodiploma.shoejima.MainActivity
import com.ndp.picodiploma.shoejima.R
import com.ndp.picodiploma.shoejima.databinding.ActivityRegisterBinding
import javax.sql.StatementEvent

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRegisterBinding

    private lateinit var actionBar: ActionBar
    private lateinit var progressDialog: ProgressDialog

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var email : String
    private lateinit var password : String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        actionBar = supportActionBar!!
        actionBar.hide()
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)

        progressDialog()

        //Init firebase
        firebaseAuth = FirebaseAuth.getInstance()

        //event clik
        binding.tvLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        binding.btnRegister.setOnClickListener {
            validate()
        }

    }

    private fun validate() {
        //getData
        email = binding.etEmail.text.toString().trim()
        password = binding.etEmail.text.toString().trim()

        //validate Data
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etEmail.error = "Invalid email format"
        }else if(TextUtils.isEmpty(password) && password.length < 6) {
            binding.etPassword.error = "Please enter password more than 6 char"
        }else if(TextUtils.isEmpty(binding.etName.text.toString().trim())) {
            binding.etName.error = "This field cannot be empty"
        }else if (TextUtils.isEmpty(binding.etPhone.text.toString().trim())) {
            binding.etPhone.error = "This field cannot be empty"
        }
        else {
            statusSignUp()
        }
    }

    private fun statusSignUp () {
        progressDialog.show()
        val name = binding.etName.text.toString().trim()
        val number = binding.etPhone.text.toString().trim()
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                progressDialog.dismiss()
                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email
                Toast.makeText(this, "Account created with email $email", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("Name", name)
                intent.putExtra("Number", number)
                startActivity(intent)
                finish()

            }
            .addOnFailureListener {
                progressDialog.show()
                Toast.makeText(this, "Sign Up failed due to ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun progressDialog() {
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please Wait")
        progressDialog.setMessage("Creating in...")
        progressDialog.setCanceledOnTouchOutside(false)
    }


}