package com.ndp.picodiploma.shoejima.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ndp.picodiploma.shoejima.R
import com.ndp.picodiploma.shoejima.databinding.ActivityDetailServiceBinding
import com.ndp.picodiploma.shoejima.model.Payment
import com.ndp.picodiploma.shoejima.model.Service

class DetailServiceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailServiceBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        getData()

        binding.btnPesan.setOnClickListener {
            passData()
        }

    }

    private fun getData() {
        val service = intent.getParcelableExtra<Service>(EXTRA_SERVICE) as Service
        binding.tvTitle.text = service.serviceType
        binding.tvDesc.text = service.desc
        binding.tvPrice.text = service.price
    }
    private fun passData(){
       var dataType = binding.tvTitle.text.toString().trim()
       var dataPhoto = resources.obtainTypedArray(R.array.data_photo)
       var dataShoes = binding.etJenis.text.toString().trim()
       var dataColor = binding.etWarna.text.toString().trim()
       var dataPrice = binding.tvPrice.text.toString().trim()

       var payment : Payment

        if (dataType == "Deep Clean") {
            payment = Payment(
                dataType,dataShoes,dataColor,dataPrice, dataPhoto.getResourceId(0, -1)
            )
        }else if(dataType == "Unyellowing") {
            var id = 1
            payment = Payment(
                dataType,dataShoes,dataColor,dataPrice, dataPhoto.getResourceId(id, -1)
            )
        }else {
            var id = 1
            payment = Payment(
                dataType,dataShoes,dataColor,dataPrice, dataPhoto.getResourceId(id, -1)
            )
        }
            val moveWithObjectIntent = Intent(this, CheckoutActivity::class.java)
            moveWithObjectIntent.putExtra(CheckoutActivity.EXTRA_PAYMENT, payment)
            startActivity(moveWithObjectIntent)
    }

    companion object {
        const val EXTRA_SERVICE = "extra_service"
    }

}