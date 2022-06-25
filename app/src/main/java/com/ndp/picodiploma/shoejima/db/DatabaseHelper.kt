package com.ndp.picodiploma.shoejima.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.ndp.picodiploma.shoejima.db.DatabaseContract.customerColumns.Companion.TABLE_NAME

internal class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

    companion object {
        private const val DATABASE_NAME = "dbshoejimaapp"
        private const val DATABASE_VERSION = 1

        private const val SQL_CREATE_TABLE_CUSTOMER = "CREATE TABLE $TABLE_NAME" +
                " (${DatabaseContract.customerColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                " ${DatabaseContract.customerColumns.NAME} TEXT NOT NULL," +
                " ${DatabaseContract.customerColumns.EMAIL} TEXT NOT NULL," +
                " ${DatabaseContract.customerColumns.DATE} TEXT NOT NULL)"

    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_CUSTOMER)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

}