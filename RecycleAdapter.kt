package com.example.animation

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class RecycleAdapter (options: FirestoreRecyclerOptions<RecycleData?>) :
   FirestoreRecyclerAdapter<RecycleData, RecyclerView.ViewHolder>(options) {
    class VH(v: View) : RecyclerView.ViewHolder(v) {

        val img: ImageView = v.findViewById(R.id.hospitalimg)
        val name: TextView = v.findViewById(R.id.name)
        val degree: TextView = v.findViewById(R.id.degree)
        val specialist: TextView = v.findViewById(R.id.specialist)
        val location: TextView = v.findViewById(R.id.location)
        val contact: TextView = v.findViewById(R.id.contact)
        val email: TextView = v.findViewById(R.id.email)
    }
    override fun onCreateViewHolder(p: ViewGroup, v: Int): VH {
        val view = LayoutInflater.from(p.context)
            .inflate(R.layout.gov_item_xml, p, false)
        return VH(view)
    }


     override fun onBindViewHolder(
        p0: RecyclerView.ViewHolder,
        p1: Int,
        p2: RecycleData
    ) {
         val h = p0 as VH
         h.name.text = p2.name
         h.degree.text = p2.degree
         h.specialist.text = p2.Specialist
         h.location.text = p2.Location
         h.contact.text = p2.Contact
         h.email.text = p2.Email
         Glide.with(h.itemView.context)
             .load(p2.image)
             .into(h.img)
         // 📞 CALL CLICK
         h.contact.setOnClickListener {
             val intent = Intent(Intent.ACTION_DIAL)
             intent.data = Uri.parse("tel:${p2.Contact}")
             h.itemView.context.startActivity(intent)
         }

         // 📍 LOCATION CLICK (Google Maps)
         h.location.setOnClickListener {
             val uri = Uri.parse("geo:0,0?q=${p2.Location}")
             val intent = Intent(Intent.ACTION_VIEW, uri)
             intent.setPackage("com.google.android.apps.maps")
             h.itemView.context.startActivity(intent)
         }
     }
}