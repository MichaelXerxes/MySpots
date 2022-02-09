package com.example.myspots.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myspots.adapters.ImagesAdapter
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
            startActivityForResult(intent, ADD_PLACE_ACTIVITY_REQUEST_CODE)

        }
        binding?.flCameraBtn?.setOnClickListener {
            val intent=Intent(this, ImagesAdapter::class.java)
            startActivity(intent)
        }
        binding?.fllistBtn?.setOnClickListener {
            val intent=Intent(this, SpotsListActivity::class.java)
            startActivityForResult(intent, ADD_PLACE_ACTIVITY_REQUEST_CODE)
        }

    }
    override fun onDestroy() {
        super.onDestroy()
        binding=null
    }
    companion object{
        private const val ADD_PLACE_ACTIVITY_REQUEST_CODE = 1
    }
}
