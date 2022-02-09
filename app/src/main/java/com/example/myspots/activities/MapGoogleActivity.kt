package com.example.myspots.activities

import android.app.ActivityGroup
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myspots.R
import com.example.myspots.databinding.ActivityMapGoogleBinding
import com.example.myspots.databinding.ActivitySpotsListBinding

class MapGoogleActivity : AppCompatActivity() {
    private var binding: ActivityMapGoogleBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMapGoogleBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolBarGoogleMap)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding?.toolBarGoogleMap?.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}