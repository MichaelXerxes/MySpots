package com.example.myspots

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myspots.databinding.ActivityAddNewPlaceBinding
import com.example.myspots.databinding.ActivityImagesAdapterBinding

class ImagesAdapter : AppCompatActivity() {
    private var binding:ActivityImagesAdapterBinding?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityImagesAdapterBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolBarCameraPlace)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding?.toolBarCameraPlace?.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}