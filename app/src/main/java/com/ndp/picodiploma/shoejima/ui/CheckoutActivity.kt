package com.ndp.picodiploma.shoejima.ui

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.dicoding.picodiploma.mynotesapp.entity.Note
import com.ndp.picodiploma.shoejima.MainActivity
import com.ndp.picodiploma.shoejima.SuccessActivity
import com.ndp.picodiploma.shoejima.databinding.ActivityCheckoutBinding
import com.ndp.picodiploma.shoejima.db.DatabaseContract
import com.ndp.picodiploma.shoejima.db.DatabaseContract.customerColumns.Companion.DATE
import com.ndp.picodiploma.shoejima.db.NoteHelper
import com.ndp.picodiploma.shoejima.model.Payment
import com.ndp.picodiploma.shoejima.model.Service
import java.text.SimpleDateFormat
import java.util.*

class CheckoutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheckoutBinding
    private var note: Note? = null
    private var isEdit = false
    private var position: Int = 0
    private lateinit var noteHelper: NoteHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        getData()

        noteHelper = NoteHelper.getInstance(applicationContext)
        noteHelper.open()

        note = intent.getParcelableExtra(EXTRA_NOTE)
        if (note != null) {
            position = intent.getIntExtra(EXTRA_POSITION, 0)
            isEdit = true
        } else {
            note = Note()
        }

        binding.btnOrder.setOnClickListener {
            val nameType = binding.tvService.text.toString().trim()
            val sepatu = binding.tvSepatu.text.toString().trim()


            note?.name = nameType
            note?.email = sepatu

            val intent = Intent()
            intent.putExtra(EXTRA_NOTE, note)
            intent.putExtra(EXTRA_POSITION, position)

            val values = ContentValues()
            values.put(DatabaseContract.customerColumns.NAME, nameType)
            values.put(DatabaseContract.customerColumns.EMAIL, sepatu)

            note?.date = getCurrentDate()
            values.put(DATE, getCurrentDate())
            val result = noteHelper.insert(values)

            if (result > 0) {
                note?.id = result.toInt()
                setResult(RESULT_ADD, intent)
                Toast.makeText(this, "Order Berhasil ", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, SuccessActivity::class.java))
                finish()
            }else {
                Toast.makeText(this, "Order Gagal", Toast.LENGTH_SHORT).show()
            }

        }

    }
    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault())
        val date = Date()
        return dateFormat.format(date)
    }

    private fun getData() {
        val payment = intent.getParcelableExtra<Service>(CheckoutActivity.EXTRA_PAYMENT) as Payment
        binding.tvService.text = payment.type
        binding.tvSepatu.text = payment.shoes
        binding.tvColor.text = payment.color
        binding.tvSubTotal.text = payment.price
        binding.ivDeep.setImageResource(payment.icon)

        val subTotal = Integer.parseInt(payment.price)
        val total = subTotal + 5000

        binding.textView28.text = total.toString()
    }

    companion object {
        const val EXTRA_PAYMENT = "extra_payment"
        const val EXTRA_NOTE = "extra_note"
        const val EXTRA_POSITION = "extra_position"
        const val RESULT_ADD = 101
    }

}