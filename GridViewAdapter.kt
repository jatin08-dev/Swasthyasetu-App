package com.example.animation



import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import java.util.ArrayList

class GridViewAdapter(
    context: Context,
    list: ArrayList<CourseModel>
): ArrayAdapter<CourseModel?>(context,0,list as List<CourseModel?>) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var itemView= convertView
        if (itemView==null){
            itemView= LayoutInflater.from(context).inflate(R.layout.card_item,parent,false)
        }
        val model: CourseModel?=getItem(position)
        val textView=itemView!!.findViewById<TextView>(R.id.text_view)
        val imageView=itemView.findViewById<ImageView>(R.id.image_view)

        textView.text=model!!.name
        imageView.setImageResource(model.image)
        return itemView
    }
}