package com.example.myspots.adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myspots.activities.SpotsListActivity
import com.example.myspots.databinding.ActivityAddNewPlaceBinding.inflate
import com.example.myspots.databinding.ActivitySpotsListBinding
import com.example.myspots.databinding.SlotSpotBinding
import com.example.myspots.models.SpotModel

open class MySpotsAdapter(
    private val context: Context,

    private val list: ArrayList<SpotModel>
) :RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SpotViewHolder(
            //LayoutInflater.from(context).inflate(

            SlotSpotBinding.inflate(
            LayoutInflater.from(parent.context)
                ,
            parent,false
        ))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model=list[position]
        if (holder is SpotViewHolder ){
            holder.description.text=model.description
            holder.title.text=model.title
            holder.idNum.text=model.id.toString()
            holder.location.text=model.location
            holder.lat.text=model.latitude.toString()
            holder.long.text=model.longitude.toString()
            holder.date.text=model.date
            holder.image.setImageURI(Uri.parse(model.image))
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }
    override fun getItemViewType(position: Int): Int {
        return list[position].id    //.viewType !!!
    }
    companion object{
        const val VIEW_TYPE_ONE=1
        const val VIEW_TYPE_TWO=2

    }
    class SpotViewHolder(mergeBinding:SlotSpotBinding)
        :RecyclerView.ViewHolder(mergeBinding.root){
            val idNum=mergeBinding.rvIDID
        val title=mergeBinding.rvTitleID
        val image=mergeBinding.rvImageID
        val date=mergeBinding.rvDateID
        val description=mergeBinding.rvDescriptionID
        val location=mergeBinding.rvLocationID
        val lat=mergeBinding.rvLatitudeID
        val long=mergeBinding.rvLongitudeID
        }
}