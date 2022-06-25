package com.ndp.picodiploma.shoejima

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ndp.picodiploma.shoejima.databinding.ActivitySuccessBinding
import com.ndp.picodiploma.shoejima.ui.RiwayatActivity

class SuccessActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySuccessBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySuccessBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.btnNext.setOnClickListener {
            startActivity(Intent(this, RiwayatActivity::class.java))
        }

    }
}