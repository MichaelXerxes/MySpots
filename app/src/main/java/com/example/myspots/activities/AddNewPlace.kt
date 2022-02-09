package com.example.myspots.activities

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.myspots.R
import com.example.myspots.database.DataBaseHandler

import com.example.myspots.databinding.ActivityAddNewPlaceBinding
import com.example.myspots.models.SpotModel
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

    private var saveImageToInternalStorage:Uri?=null
    private var mLatitude:Double=0.0
    private var mLongitude:Double=0.0

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
        updateDateEditText()//call method to populate calendar on start
        ///
        binding?.etDate?.setOnClickListener(this)
        binding?.tvAddImageID?.setOnClickListener(this)
        binding?.btnSave22?.setOnClickListener(this)
        ///
        binding?.saveAllBtn?.setOnClickListener {
            //btnSaveSettings()

            Toast.makeText(this,"hahahahahahahahah",Toast.LENGTH_SHORT).show()
        }
    }

    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.et_date ->{
                DatePickerDialog(this@AddNewPlace,dateSetListener,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)).show()

            }
            R.id.tv_add_imageID ->{
                Toast.makeText(this@AddNewPlace,"**********************",Toast.LENGTH_SHORT).show()
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
            R.id.btn_Save22->{
                btnSaveSettings()
               // Toast.makeText(this@AddNewPlace, "Lol Lol Lol", Toast.LENGTH_SHORT).show()

            }
        }
    }

    private fun btnSaveSettings(){
        when{
            binding?.etTitle?.text.isNullOrEmpty() ->{
                Toast.makeText(this@AddNewPlace, "Please enter title", Toast.LENGTH_SHORT).show()
            }
            binding?.etDescription?.text.isNullOrEmpty() ->{
                Toast.makeText(this@AddNewPlace, "Please enter description", Toast.LENGTH_SHORT).show()
            }
            binding?.etLocation?.text.isNullOrEmpty() ->{
                Toast.makeText(this@AddNewPlace, "Please select location", Toast.LENGTH_SHORT)
                    .show()
            }
            saveImageToInternalStorage==null ->{
                Toast.makeText(this@AddNewPlace, "Please add image", Toast.LENGTH_SHORT).show()
            }else ->{
            val mySpot=SpotModel(0,binding?.etTitle?.text.toString(),
                saveImageToInternalStorage.toString(),
                binding?.etDescription?.text.toString(),
                binding?.etDate?.text.toString(),
                binding?.etLocation?.text.toString(),
                mLatitude,mLongitude)
            val dbHandler=DataBaseHandler(this)
            val addMySpotResult=dbHandler.addMySpots(mySpot)

            if(addMySpotResult > 0){
              //  Toast.makeText(this@AddNewPlace,"The Spot details are inserted successfuly ",
                  //  Toast.LENGTH_SHORT).show()
                      setResult(Activity.RESULT_OK)
                finish();
            }

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
                        @Suppress("DEPRECATION")
                        val selectedImageBitmap=MediaStore.Images.Media.
                        getBitmap(this.contentResolver, contentInfo)
                        ///save Image
                        saveImageToInternalStorage=saveImageToInternalStorgae(selectedImageBitmap)


                        Log.e("Saved Image","Path::$saveImageToInternalStorage")


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

                saveImageToInternalStorage=saveImageToInternalStorgae(thumBnail)
                Log.e("Saved Image","Path::$saveImageToInternalStorage")

                binding?.appCompatImageView?.setImageBitmap(thumBnail)  // Set to the imageView.

            }
        }
        else if (resultCode == Activity.RESULT_CANCELED) {
            Log.e("Cancelled", "Cancelled")
        }
    }
    private fun saveImageToInternalStorgae(bitmap: Bitmap):Uri{
        //val drawable = ContextCompat.getDrawable(applicationContext,drawableId)
        //val bitmap1 = (drawable as BitmapDrawable).bitmap
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

    override fun onDestroy() {
        super.onDestroy()
        binding=null
        calendar= Calendar.getInstance()
        saveImageToInternalStorage=null
        mLatitude=0.0
        mLongitude=0.0
    }
}