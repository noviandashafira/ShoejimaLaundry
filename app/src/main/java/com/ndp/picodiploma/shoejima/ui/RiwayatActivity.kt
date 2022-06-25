package com.ndp.picodiploma.shoejima.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.mynotesapp.Helper.MappingHelper
import com.dicoding.picodiploma.mynotesapp.entity.Note
import com.google.android.material.snackbar.Snackbar
import com.ndp.picodiploma.shoejima.MainActivity
import com.ndp.picodiploma.shoejima.NoteAdapter
import com.ndp.picodiploma.shoejima.databinding.ActivityRiwayatBinding
import com.ndp.picodiploma.shoejima.db.NoteHelper
import com.ndp.picodiploma.shoejima.ui.CheckoutActivity.Companion.EXTRA_NOTE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class RiwayatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRiwayatBinding
    private lateinit var adapter: NoteAdapter

    val resultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.data != null) {
            // Akan dipanggil jika request codenya ADD
            when (result.resultCode) {
                CheckoutActivity.RESULT_ADD -> {
                    val note =
                        result.data?.getParcelableExtra<Note>(CheckoutActivity.EXTRA_NOTE) as Note
                    adapter.addItem(note)
                    binding.rvRiwayat.smoothScrollToPosition(adapter.itemCount - 1)
                    showSnackbarMessage("Satu item berhasil ditambahkan")
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRiwayatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        binding.rvRiwayat.layoutManager = LinearLayoutManager(this)
        binding.rvRiwayat.setHasFixedSize(true)

        adapter = NoteAdapter(object : NoteAdapter.OnItemClickCallback {
            override fun onItemClicked(selectedNote: Note?, position: Int?) {
                val intent = Intent(this@RiwayatActivity, MainActivity::class.java)
                intent.putExtra(MainActivity.EXTRA_NOTE, selectedNote)
                intent.putExtra(MainActivity.EXTRA_POSITION, position)
                resultLauncher.launch(intent)
            }
        })
        binding.rvRiwayat.adapter = adapter

        loadNotesAsync()

    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
    private fun showSnackbarMessage(message: String) {
        Snackbar.make(binding.rvRiwayat, message, Snackbar.LENGTH_SHORT).show()
    }
    private fun loadNotesAsync() {
        lifecycleScope.launch {
            binding.progressbar.visibility = View.VISIBLE
            val noteHelper = NoteHelper.getInstance(applicationContext)
            noteHelper.open()
            val deferredNotes = async(Dispatchers.IO) {
                val cursor = noteHelper.queryAll()
                MappingHelper.mapCursorToArrayList(cursor)
            }
            binding.progressbar.visibility = View.INVISIBLE
            val notes = deferredNotes.await()
            if (notes.size > 0) {
                adapter.listNotes = notes
            } else {
                adapter.listNotes = ArrayList()
                showSnackbarMessage("Tidak ada data saat ini")
            }
            noteHelper.close()
        }
    }
}