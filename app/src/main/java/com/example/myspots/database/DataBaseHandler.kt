package com.example.myspots.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.example.myspots.models.SpotModel

class DataBaseHandler(context: Context):
      SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION){
          companion object{
              private const val DATABASE_NAME="MySpotsDataBase"
              private const val DATABASE_VERSION=2
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
        val CREATE_TABLE_MY_SPOTS=("CREATE TABLE "+ TABLE_MY_SPOTS+"("
                + KEY_ID+" INTEGER PRIMARY KEY,"
                +KEY_TITLE+" TEXT,"
                +KEY_IMAGE+" TEXT,"
                + KEY_DESCRIPTION+" TEXT,"
                + KEY_DATE+" TEXT,"
                + KEY_LOCATION+" TEXT,"
                + KEY_LATITUDE+" TEXT,"
                + KEY_LONGITUDE+" TEXT)")
        db!!.execSQL(CREATE_TABLE_MY_SPOTS)
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
    @SuppressLint("Range")
    fun getMySpotsList():ArrayList<SpotModel>{
        val db =this.writableDatabase
        val mySpotsLits=ArrayList<SpotModel>()
        var cursor:Cursor?=null
        val selectQuery=("SELECT * FROM $TABLE_MY_SPOTS")
        try {
            cursor=db.rawQuery(selectQuery,null)
        }catch (e:SQLiteException){
            db.execSQL(selectQuery)
            return ArrayList()
        }
            if(cursor.moveToFirst()){
                do {
                    val place=SpotModel(
                        cursor.getInt(cursor.getColumnIndex(KEY_ID)),
                            cursor.getString(cursor.getColumnIndex(KEY_TITLE)),
                            cursor.getString(cursor.getColumnIndex(KEY_IMAGE)),
                        cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndex(KEY_DATE)),
                        cursor.getString(cursor.getColumnIndex(KEY_LOCATION)),
                        cursor.getDouble(cursor.getColumnIndex(KEY_LATITUDE)),
                        cursor.getDouble(cursor.getColumnIndex(KEY_LONGITUDE))
                    )
                    mySpotsLits.add(place)


                }while (cursor.moveToNext())
            }
            cursor.close()



        return mySpotsLits
    }

}