package com.dicoding.picodiploma.mynotesapp.Helper

import android.database.Cursor
import com.dicoding.picodiploma.mynotesapp.entity.Note
import com.ndp.picodiploma.shoejima.db.DatabaseContract

object MappingHelper {

    fun mapCursorToArrayList(noteCursor: Cursor?): ArrayList<Note> {
        val noteList = ArrayList<Note>()

        noteCursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(DatabaseContract.customerColumns._ID))
                val name = getString(getColumnIndexOrThrow(DatabaseContract.customerColumns.NAME))
                val email = getString(getColumnIndexOrThrow(DatabaseContract.customerColumns.EMAIL))
                val date = getString(getColumnIndexOrThrow(DatabaseContract.customerColumns.DATE))
                noteList.add(Note(id, name, email, date))
            }
        }
        return noteList
    }

}