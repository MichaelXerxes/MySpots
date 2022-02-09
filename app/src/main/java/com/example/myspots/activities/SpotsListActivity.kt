package com.example.myspots.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myspots.R
import com.example.myspots.adapters.ImagesAdapter
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
        setSupportActionBar(binding?.toolBarSpotsList)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding?.toolBarSpotsList?.setNavigationOnClickListener {
            onBackPressed()
        }


      getSpotsfromLocalDB()



    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // check if the request code is same as what is passed  here it is 'ADD_PLACE_ACTIVITY_REQUEST_CODE'
        if (requestCode == ADD_PLACE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                getSpotsfromLocalDB()
            }else{
                Log.e("Activity", "Cancelled or Back Pressed")
                Toast.makeText(this@SpotsListActivity,
                    "Start Activity and Nothing here",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater :MenuInflater=menuInflater
        inflater.inflate(R.menu.menu_for_list,menu)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding?.toolBarSpotsList?.setNavigationOnClickListener {
            onBackPressed()
        }

        return true
        //super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.menuOPt1 ->{
                val intent =Intent(this, AddNewPlace::class.java)
                startActivity(intent)
                finish()

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info =item.menuInfo as AdapterView.AdapterContextMenuInfo
        return  when(item.itemId) {
            R.id.menuOPt1 ->{
                val intent =Intent(this, AddNewPlace::class.java)
                startActivity(intent)
                finish()

                true
            }

           else -> super.onContextItemSelected(item)
        }
    }
    private fun getSpotsfromLocalDB(){
        val dbHandler=DataBaseHandler(this)
        val mySpotsList:ArrayList<SpotModel> =dbHandler.getMySpotsList()

        if(mySpotsList.size>0){
            for (i in mySpotsList){
                setupSpotsStatusRecyclerView(mySpotsList)
                Toast.makeText(this@SpotsListActivity,
                    "Eureka jest tutaj",
                    Toast.LENGTH_SHORT).show()
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

        adapterList!!.setOnClickListenerNew(object: MySpotsAdapter.OnClickListenerNew{
            override fun onClick(position: Int, model: SpotModel) {
                val intent=Intent(this@SpotsListActivity, SpotDetailsActivity::class.java)
                intent.putExtra(EXTRA_SPOT_DETAILS,model)
                startActivity(intent)
            }
        })


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
         var EXTRA_SPOT_DETAILS="spot details"
    }
}