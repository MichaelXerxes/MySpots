package com.example.myspots.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
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
            Toast.makeText(this,"hahahahah2222222222222ahahahah", Toast.LENGTH_SHORT).show()
        }
        binding?.flCameraBtn?.setOnClickListener {
            val intent=Intent(this, ImagesAdapter::class.java)
            startActivity(intent)
        }

    }
    override fun onDestroy() {
        super.onDestroy()
        binding=null
    }
}
