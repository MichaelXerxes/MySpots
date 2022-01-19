package com.example.myspots

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.media.audiofx.Equalizer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.view.View
import android.widget.Toast

import com.example.myspots.databinding.ActivityAddNewPlaceBinding
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import java.io.IOException
import java.security.Permission
import java.text.SimpleDateFormat
import java.util.*
import java.util.jar.Manifest

class AddNewPlace : AppCompatActivity(), View.OnClickListener {
    private var binding:ActivityAddNewPlaceBinding?=null
    private var calendar=Calendar.getInstance()
    private lateinit var dateSetListener:DatePickerDialog.OnDateSetListener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAddNewPlaceBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolBarAddPlace)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding?.toolBarAddPlace?.setNavigationOnClickListener {
            onBackPressed()
        }

        dateSetListener=DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR,year)
            calendar.set(Calendar.MONTH,month)
            calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth)
            updateDateEditText()
        }
        binding?.etDate?.setOnClickListener(this)
        binding?.tvAddImageID?.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.et_date->{
                DatePickerDialog(this@AddNewPlace,dateSetListener,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)).show()

            }
            R.id.tv_add_imageID->{
                val pictureDialog=AlertDialog.Builder(this)
                pictureDialog.setTitle("Select Action")
                val pictureDialogItems= arrayOf("Select photo from Gallery","Capture photo from Camera")
                pictureDialog.setItems(pictureDialogItems){
                    dialog,which ->
                    when(which){
                        0-> choosePhotoFromGallery()

                        1->{ Toast.makeText(this@AddNewPlace,
                            "You decided not to ",
                            Toast.LENGTH_SHORT).show()}

                    }
                }
                pictureDialog.show()
            }
        }

    }
    private fun updateDateEditText(){
        val format="dd.MM.yyyy"
        val sdf=SimpleDateFormat(format, Locale.getDefault())
        binding?.etDate?.setText(sdf.format(calendar.time).toString())
    }
    private fun choosePhotoFromGallery(){
        Dexter.withContext(this).withPermissions(
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        ).withListener(object : MultiplePermissionsListener{
            override fun onPermissionsChecked(report:MultiplePermissionsReport){
                if(report!!.areAllPermissionsGranted()){
                  //  Toast.makeText(this@AddNewPlace,
                  //  "Storage READ / WRITE permission are granted. You can select an image from Gallery  ",
                  //  Toast.LENGTH_SHORT).show()
                    val galleryIntent=Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    startActivityForResult(galleryIntent,GALLERY)
                }
            }
            override fun onPermissionRationaleShouldBeShown(permissions: MutableList<PermissionRequest> ,
            token: PermissionToken){
                showRationalDialogForPermissions()
            }
        }).onSameThread().check()
    }
    private fun showRationalDialogForPermissions(){
        AlertDialog.Builder(this).setMessage("" +
                "You have Turned-Off permission required for this feature." +
                " You can enabled under Application Settings")
            .setPositiveButton("Go To Settings")
            { _,_ ->
                       try {
                           val intent=Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                           val uri=Uri.fromParts("package",packageName,null)
                           intent.data=uri
                           startActivity(intent)
                       } catch (e: ActivityNotFoundException) {
                           e.printStackTrace()
                       }
            }.setNegativeButton("Cancel"){dialog , _ ->
                dialog.dismiss()
        }.show()

    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode==Activity.RESULT_OK){
            if(requestCode== GALLERY){
                if(data!=null){
                    val contentInfo=data.data
                    try {
                        val selectedImageBitmap=MediaStore.Images.Media.
                        getBitmap(this.contentResolver, contentInfo)
                        binding?.appCompatImageView?.setImageBitmap(selectedImageBitmap)
                    }catch (e: IOException){
                        e.printStackTrace()
                          Toast.makeText(this@AddNewPlace,
                          "Failed to load Image!",
                         Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    companion object{
        private const val GALLERY=1
    }
}