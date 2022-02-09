package com.example.myspots.activities

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myspots.R
import com.example.myspots.databinding.ActivitySpotDetailsBinding
import com.example.myspots.models.SpotModel

class SpotDetailsActivity : AppCompatActivity() {
    private var binding:ActivitySpotDetailsBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySpotDetailsBinding.inflate(layoutInflater)
        setContentView(binding?.root)


        var spotModel:SpotModel?=null
        if(intent.hasExtra(SpotsListActivity.EXTRA_SPOT_DETAILS)){
            spotModel=intent.getSerializableExtra(SpotsListActivity.EXTRA_SPOT_DETAILS) as SpotModel
        }

        if (spotModel!=null){
            setSupportActionBar(binding?.toolBarSpotDetails)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            binding?.toolBarSpotDetails?.setNavigationOnClickListener {
                onBackPressed()
            }

            supportActionBar!!.title=spotModel.title
            binding!!.imageViewDetailsID.setImageURI(Uri.parse(spotModel.image))
        }


    }






}