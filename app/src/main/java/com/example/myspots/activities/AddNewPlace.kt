package com.example.myspots.activities

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.myspots.R

import com.example.myspots.databinding.ActivityAddNewPlaceBinding
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*

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
            R.id.et_date ->{
                DatePickerDialog(this@AddNewPlace,dateSetListener,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)).show()

            }
            R.id.tv_add_imageID ->{
                val pictureDialog=AlertDialog.Builder(this)
                pictureDialog.setTitle("Select Action")
                val pictureDialogItems= arrayOf("Select photo from Gallery","Capture photo from Camera")
                pictureDialog.setItems(pictureDialogItems){
                    dialog,which ->
                    when(which){
                        0-> choosePhotoFromGallery()

                        1-> takePhotoFromCamera()


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
                    startActivityForResult(galleryIntent, GALLERY)
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
    private fun takePhotoFromCamera(){
        Dexter.withContext(this).withPermissions(
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.CAMERA
        ).withListener(object : MultiplePermissionsListener{
            override fun onPermissionsChecked(report:MultiplePermissionsReport){
                if(report!!.areAllPermissionsGranted()){
                    //  Toast.makeText(this@AddNewPlace,
                    //  "Storage READ / WRITE permission are granted. You can select an image from Gallery  ",
                    //  Toast.LENGTH_SHORT).show()
                    val galleryIntent=Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(galleryIntent, CAMERA)
                }
            }
            override fun onPermissionRationaleShouldBeShown(permissions: MutableList<PermissionRequest> ,
                                                            token: PermissionToken){
                showRationalDialogForPermissions()
            }
        }).onSameThread().check()
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
                        ///save Image
                        val savedImage=saveImageToInternalStorgae(selectedImageBitmap)
                        Log.e("Saved Image","Path::$savedImage")


                        binding?.appCompatImageView?.setImageBitmap(selectedImageBitmap)
                    }catch (e: IOException){
                        e.printStackTrace()
                          Toast.makeText(this@AddNewPlace,
                          "Failed to load Image!",
                         Toast.LENGTH_SHORT).show()
                    }
                }
            }else if (requestCode== CAMERA){
                val thumBnail:Bitmap=data!!.extras!!.get("data") as Bitmap

                val savedImage=saveImageToInternalStorgae(thumBnail)
                Log.e("Saved Image","Path::$savedImage")
                binding?.appCompatImageView?.setImageBitmap(thumBnail)

            }
        }
    }
    private fun saveImageToInternalStorgae(bitmap: Bitmap):Uri{
        val wrapper =ContextWrapper(applicationContext)
        var file=wrapper.getDir(IMAGE_DIRECTORY,Context.MODE_PRIVATE)
        file= File(file,"${UUID.randomUUID()}.jpg")
        try {
            val stream:OutputStream=FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream)
            stream.flush()
            stream.close()

        }catch (e:IOException){
            e.printStackTrace()
        }
        return Uri.parse(file.absolutePath)
    }


    companion object{
        private const val GALLERY=1
        private const val CAMERA=2
        private const val IMAGE_DIRECTORY="MySpots"
    }
}