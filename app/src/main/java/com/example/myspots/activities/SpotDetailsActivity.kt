package com.example.myspots.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myspots.R
import com.example.myspots.databinding.ActivitySpotDetailsBinding
import com.example.myspots.models.SpotModel

class SpotDetailsActivity : AppCompatActivity() {
    private var binding:ActivitySpotDetailsBinding?=null
    private val uri="geo:0,0?q=india"
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
            binding!!.tvDescriptionD.text=spotModel.description
            binding!!.tvLocationD.text=spotModel.location
            binding!!.tvDataD.text=spotModel.date
            binding!!.tvLatitD.text=spotModel.latitude.toString()
            binding!!.tvLongiD.text=spotModel.longitude.toString()
        }

        binding!!.btnViewOnMap.setOnClickListener {
            val uriMap=Uri.parse(uri)
            val intent=Intent(Intent.ACTION_VIEW,uriMap)
            intent.setPackage("com.google.android.apps.maps")
            if(intent.resolveActivity(packageManager)!=null){
                startActivity(intent)
            }
        }


    }






}