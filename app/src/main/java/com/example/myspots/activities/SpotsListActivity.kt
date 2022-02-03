package com.example.myspots.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myspots.R
import com.example.myspots.adapters.MySpotsAdapter
import com.example.myspots.database.DataBaseHandler
import com.example.myspots.databinding.ActivitySpotsListBinding
import com.example.myspots.models.SpotModel

class SpotsListActivity : AppCompatActivity() {
    private var binding:ActivitySpotsListBinding?=null
    private var adapterList:MySpotsAdapter?=null
    private var spotsList:ArrayList<SpotModel>?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySpotsListBinding.inflate(layoutInflater)
        setContentView(binding?.root)

      // getSpotsfromLocalDB()



    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // check if the request code is same as what is passed  here it is 'ADD_PLACE_ACTIVITY_REQUEST_CODE'
        if (requestCode == ADD_PLACE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                getSpotsfromLocalDB()
            }else{
                Log.e("Activity", "Cancelled or Back Pressed")
            }
        }
    }
    private fun getSpotsfromLocalDB(){
        val dbHandler=DataBaseHandler(this)
        val mySpotsList:ArrayList<SpotModel> =dbHandler.getMySpotsList()

        if(mySpotsList.size>0){
            for (i in mySpotsList){
                setupSpotsStatusRecyclerView(mySpotsList)
            }

        }else{
            Toast.makeText(this@SpotsListActivity,
                "No record or Error List",
                Toast.LENGTH_SHORT).show()
        }



    }
    private fun setupSpotsStatusRecyclerView(spList:ArrayList<SpotModel>){

        binding?.rvSpotsList?.layoutManager=
            LinearLayoutManager(this
               // ,LinearLayoutManager.HORIZONTAL,
               // false
            )
        binding?.rvSpotsList?.setHasFixedSize(true)
        adapterList= MySpotsAdapter(this,spList)
        binding?.rvSpotsList?.adapter=adapterList


    }
    override fun onDestroy() {
        super.onDestroy()
        binding=null
        if(adapterList!=null){
            adapterList=null
        }
        if(spotsList!=null){
            spotsList=null
        }
    }
    companion object{
        private const val ADD_PLACE_ACTIVITY_REQUEST_CODE = 1
    }
}