package com.example.myspots

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myspots.databinding.ActivityAddNewPlaceBinding

class AddNewPlace : AppCompatActivity() {
    private var binding:ActivityAddNewPlaceBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAddNewPlaceBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolBarAddPlace)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding?.toolBarAddPlace?.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}