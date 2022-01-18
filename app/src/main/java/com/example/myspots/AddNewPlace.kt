package com.example.myspots

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.myspots.databinding.ActivityAddNewPlaceBinding
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
            UpdateDateEditText()
        }
        binding?.etDate?.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.et_date->{
                DatePickerDialog(this@AddNewPlace,dateSetListener,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)).show()

            }
        }

    }
    private fun UpdateDateEditText(){
        val format="dd.MM.yyyy"
        val sdf=SimpleDateFormat(format, Locale.getDefault())
        binding?.etDate?.setText(sdf.format(calendar.time).toString())
    }
}