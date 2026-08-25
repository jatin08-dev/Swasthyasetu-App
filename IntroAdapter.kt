package com.example.animation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView

class IntroAdapter (private val list: List<IntroItem>):
    RecyclerView.Adapter<IntroAdapter.ViewHolder>()
{
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view)
    {
        val image: ImageView=view.findViewById(R.id.imgslide)
        val title: TextView=view.findViewById(R.id.txtTitle)
        val description: TextView=view.findViewById(R.id.txtDescr)
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.item_intro,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    )    {
        val item=list[position]
        holder.title.text=item.title
        holder.image.setImageResource(item.image)
        holder.description.text=item.description
    }

    override fun getItemCount(): Int=list.size
}