package com.ndp.picodiploma.shoejima

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.ndp.picodiploma.shoejima.databinding.ActivityMainBinding
import com.ndp.picodiploma.shoejima.model.Service
import com.ndp.picodiploma.shoejima.ui.DetailServiceActivity
import com.ndp.picodiploma.shoejima.ui.LoginActivity
import com.ndp.picodiploma.shoejima.ui.ProfileActivity
import com.ndp.picodiploma.shoejima.ui.RiwayatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        val name = intent.getStringExtra("Name")
        val number = intent.getStringExtra("Number")
        binding.nameUser.text = name

        binding.ivProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            intent.putExtra("Name", name)
            intent.putExtra("Number", number)
            startActivity(intent)
        }

        binding.btnUnyellow.setOnClickListener {
            var dataType = resources.getStringArray(R.array.data_layanan)
            var dataDesc = resources.getStringArray(R.array.data_description)
            var dataPrize = resources.getStringArray(R.array.data_prize)


            val service = Service(
                dataType[1],
                dataDesc[1],
                dataPrize[1],
            )

            val moveWithObjectIntent = Intent(this@MainActivity, DetailServiceActivity::class.java)
            moveWithObjectIntent.putExtra(DetailServiceActivity.EXTRA_SERVICE, service)
            startActivity(moveWithObjectIntent)
        }

        binding.btnRepaint.setOnClickListener {

            var dataType = resources.getStringArray(R.array.data_layanan)
            var dataDesc = resources.getStringArray(R.array.data_description)
            var dataPrize = resources.getStringArray(R.array.data_prize)


            val service = Service(
                dataType[2],
                dataDesc[2],
                dataPrize[2],
            )

            val moveWithObjectIntent = Intent(this@MainActivity, DetailServiceActivity::class.java)
            moveWithObjectIntent.putExtra(DetailServiceActivity.EXTRA_SERVICE, service)
            startActivity(moveWithObjectIntent)
        }

        binding.btnClean.setOnClickListener {

            var dataType = resources.getStringArray(R.array.data_layanan)
            var dataDesc = resources.getStringArray(R.array.data_description)
            var dataPrize = resources.getStringArray(R.array.data_prize)


            val service = Service(
                dataType[0],
                dataDesc[0],
                dataPrize[0],
            )

            val moveWithObjectIntent = Intent(this@MainActivity, DetailServiceActivity::class.java)
            moveWithObjectIntent.putExtra(DetailServiceActivity.EXTRA_SERVICE, service)
            startActivity(moveWithObjectIntent)
        }
    }
    private fun checkUser() {
        val email = firebaseAuth.currentUser!!.email
        if (firebaseAuth.currentUser != null ) {
            Toast.makeText(this, "You Logged in as $email", Toast.LENGTH_SHORT).show()
        }else  {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
    companion object {
        const val EXTRA_PAYMENT = "extra_payment"
        const val EXTRA_NOTE = "extra_note"
        const val EXTRA_POSITION = "extra_position"
        const val RESULT_ADD = 101
    }


}