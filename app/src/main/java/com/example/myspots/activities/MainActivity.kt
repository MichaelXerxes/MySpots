package com.example.myspots.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myspots.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var binding:ActivityMainBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intentDemo=Intent(this,StartDemoActivity::class.java)
        startActivity(intentDemo)

        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.flactionBtn?.setOnClickListener {
            val intent =Intent(this, AddNewPlace::class.java)
            startActivity(intent)
        }
        binding?.flCameraBtn?.setOnClickListener {
            val intent=Intent(this, ImagesAdapter::class.java)
            startActivity(intent)
        }
    }
}