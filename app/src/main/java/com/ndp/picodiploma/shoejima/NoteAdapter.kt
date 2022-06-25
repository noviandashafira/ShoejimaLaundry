package com.ndp.picodiploma.shoejima

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.picodiploma.mynotesapp.entity.Note
import com.ndp.picodiploma.shoejima.databinding.ItemRiwayatBinding

class NoteAdapter(private val onItemClickCallback: OnItemClickCallback) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    var listNotes = ArrayList<Note>()
    set(listNotes) {
        if (listNotes.size > 0) {
            this.listNotes.clear()
        }
        this.listNotes.addAll(listNotes)
    }

    fun addItem(note: Note) {
        this.listNotes.add(note)
        notifyItemInserted(this.listNotes.size - 1)
    }
    fun updateItem(position: Int, note: Note) {
        this.listNotes[position] = note
        notifyItemChanged(position, note)
    }
    fun removeItem(position: Int) {
        this.listNotes.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, this.listNotes.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteAdapter.NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_riwayat, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteAdapter.NoteViewHolder, position: Int) {
        holder.bind(listNotes[position])
    }

    override fun getItemCount(): Int = this.listNotes.size

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemRiwayatBinding.bind(itemView)
        fun bind(note: Note) {
            binding.tvName.text = note.name
            binding.tvEmail.text = note.email
            binding.tvTanggal.text = note.date
//            binding.cvItemRiwayat.setOnClickListener {
//                onItemClickCallback.onItemClicked(note, adapterPosition)
//            }
        }
    }

    interface OnItemClickCallback {
            fun onItemClicked(selectedNote: Note?, position: Int?)
    }
}