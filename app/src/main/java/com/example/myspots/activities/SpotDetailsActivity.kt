package com.example.myspots.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myspots.R
import com.example.myspots.databinding.ActivitySpotDetailsBinding

class SpotDetailsActivity : AppCompatActivity() {
    private var binding:ActivitySpotDetailsBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySpotDetailsBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolBarSpotDetails)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding?.toolBarSpotDetails?.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}