package com.example.animation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class SliderAdapter(private val images:List<Int>):RecyclerView.Adapter<SliderAdapter.SliderViewHolder>() {

    class SliderViewHolder(view: View): RecyclerView.ViewHolder(view){
        val image: ImageView=view.findViewById(R.id.sliderImage)
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SliderAdapter.SliderViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.slider,parent,false)
        return SliderViewHolder(view)
    }

    override fun onBindViewHolder(
        holder:SliderAdapter.SliderViewHolder,
        position: Int
    ) {
        holder.image.setImageResource(images[position])
    }

    override fun getItemCount(): Int=images.size

}