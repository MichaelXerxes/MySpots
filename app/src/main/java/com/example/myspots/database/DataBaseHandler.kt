package com.example.myspots.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.myspots.models.SpotModel

class DataBaseHandler(context: Context):
      SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION){
          companion object{
              private const val DATABASE_NAME="MySpotsDataBase"
              private const val DATABASE_VERSION=1
              private const val TABLE_MY_SPOTS="MySpotsTable"
              // All Columns names
              private const val KEY_ID="_id"
              private const val KEY_TITLE="title"
              private const val KEY_IMAGE="image"
              private const val KEY_DESCRIPTION="description"
              private const val KEY_DATE="date"
              private const val KEY_LOCATION="location"
              private const val KEY_LATITUDE="latitude"
              private const val KEY_LONGITUDE="longitude"
          }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE_MY_SPOTS=("CREATE TABLE"+ TABLE_MY_SPOTS+"("
                +KEY_DATE+"INTEGER PRIMARY KEY,"
                +KEY_TITLE+" TEXT,"
                +KEY_IMAGE+" TEXT,"
                + KEY_DESCRIPTION+" TEXT,"
                + KEY_DATE+" TEXT,"
                + KEY_LOCATION+" TEXT,"
                + KEY_LATITUDE+" TEXT,"
                + KEY_LONGITUDE+" TEXT)")
        db?.execSQL(CREATE_TABLE_MY_SPOTS)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_MY_SPOTS")
        onCreate(db)
    }
     fun addMySpots(mySpot:SpotModel):Long{
        val db =this.writableDatabase
        val contentValue= ContentValues()

        contentValue.put(KEY_TITLE,mySpot.title)
        contentValue.put(KEY_IMAGE,mySpot.image)
        contentValue.put(KEY_DESCRIPTION,mySpot.description)
        contentValue.put(KEY_DATE,mySpot.date)
        contentValue.put(KEY_LOCATION,mySpot.location)
        contentValue.put(KEY_LATITUDE,mySpot.latitude)
        contentValue.put(KEY_LONGITUDE,mySpot.longitude)

        val result=db.insert(TABLE_MY_SPOTS,null,contentValue)
        db.close()
        return result
    }

}