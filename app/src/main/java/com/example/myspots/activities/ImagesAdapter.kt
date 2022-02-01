package com.example.myspots.activities

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.myspots.databinding.ActivityImagesAdapterBinding

class ImagesAdapter : AppCompatActivity() {
    companion object{
        private const val CAMERA_PERMISSION_CODE=1
        private const val CAMERA_REQUEST_CODE=2
    }
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

        binding?.btnCameraImageID?.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    this@ImagesAdapter,Manifest.permission.CAMERA
            )==PackageManager.PERMISSION_GRANTED){
                val intent=Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent, CAMERA_REQUEST_CODE)
            }else{
                ActivityCompat.requestPermissions(
                    this@ImagesAdapter, arrayOf(Manifest.permission.CAMERA),
                    CAMERA_PERMISSION_CODE
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode== CAMERA_PERMISSION_CODE){
            if(grantResults.isNotEmpty()&& grantResults[0]==PackageManager.PERMISSION_GRANTED){
                val intent=Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent, CAMERA_REQUEST_CODE)
            }else{
                Toast.makeText(this@ImagesAdapter,"Denied the permission for camera! ",
                Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==Activity.RESULT_OK){
            if(requestCode== CAMERA_REQUEST_CODE){
                val thumBm:Bitmap=data!!.extras!!.get("data") as Bitmap
                binding?.appCImageViewId?.setImageBitmap(thumBm)
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        binding=null
    }

}