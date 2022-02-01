package com.example.myspots.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myspots.R
import com.example.myspots.database.DataBaseHandler
import com.example.myspots.databinding.ActivitySpotsListBinding
import com.example.myspots.models.SpotModel

class SpotsListActivity : AppCompatActivity() {
    var binding:ActivitySpotsListBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySpotsListBinding.inflate(layoutInflater)
        setContentView(binding?.root)
    }
    private fun getSpotsfromLocalDB(){
        val dbHandler=DataBaseHandler(this)
        val mySpotsList:ArrayList<SpotModel> =dbHandler.getMySpotsList()

        if(mySpotsList.size>0){
            for (i in mySpotsList){

            }
        }



    }
    override fun onDestroy() {
        super.onDestroy()
        binding=null
    }
}