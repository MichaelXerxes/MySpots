package com.example.myspots.activities

import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.example.myspots.R
import com.example.myspots.databinding.ActivityStartDemoBinding
import com.example.myspots.databinding.DemoWhiteBinding

class StartDemoActivity : AppCompatActivity() {
    var binding:ActivityStartDemoBinding?=null
    var mergeBindind:DemoWhiteBinding?=null
    var view:View?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityStartDemoBinding.inflate(layoutInflater)
        mergeBindind= DemoWhiteBinding.inflate(layoutInflater)
        val view1: View =layoutInflater.inflate(R.layout.demo_white,null)
        view=view1

        setContentView(binding?.root)
        binding?.btnBOUNCE?.setOnClickListener {


            val anima=AnimationUtils.loadAnimation(applicationContext,R.anim.bounce)
            val anima2=AnimationUtils.loadAnimation(applicationContext,R.anim.zoom_in)
            val animation3=AnimationUtils.loadAnimation(applicationContext,R.anim.explosion)


            binding?.imgLogo?.startAnimation(anima2)
            val handler=Handler()
            handler.postDelayed({binding?.imgLogo?.startAnimation(anima)},1500)
            handler.postDelayed({

                val aniD:AnimationDrawable=binding?.llDemoId?.background as AnimationDrawable
                //binding?.llDemoId?.background=startAnimation(animation3)
              //  aniD.apply {
                //    binding?.llDemoId?.startAnimation(animation3)
                   // start()
                //}

              //  val animation4=AnimationUtils.loadAnimation(this,R.anim.explosion).apply {
              //      duration=5100
               //     interpolator=AccelerateDecelerateInterpolator()
               // }

                binding?.btnExplosion?.startAnimation(animation3)
                                },3000)


          //  handler.postDelayed({


               // setContentView(view)
                             //   },6100)

        }
        binding?.btnFinishBounce?.setOnClickListener {
            finish()
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        binding=null
        mergeBindind=null
        view=null
    }
}