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

class PrivateAdapter(options: FirestoreRecyclerOptions<PrivateData?>) :
    FirestoreRecyclerAdapter<PrivateData, RecyclerView.ViewHolder>(options) {
    class VH(v: View) : RecyclerView.ViewHolder(v) {

        val image: ImageView = v.findViewById(R.id.hospitalimg1)
        val name: TextView = v.findViewById(R.id.name1)
        val degree: TextView = v.findViewById(R.id.degree1)
        val specialist: TextView = v.findViewById(R.id.specialist1)
        val location: TextView = v.findViewById(R.id.location1)
        val contact: TextView = v.findViewById(R.id.contact1)
        val email: TextView = v.findViewById(R.id.email1)
    }
    override fun onBindViewHolder(
        p0: RecyclerView.ViewHolder,
        p1: Int,
        p2: PrivateData
    ) {
        val h = p0 as PrivateAdapter.VH
        h.name.text = p2.name
        h.degree.text = p2.degree
        h.specialist.text = p2.specialist
        h.location.text = p2.location
        h.contact.text = p2.contact
        h.email.text = p2.email
        Glide.with(h.itemView.context)
            .load(p2.image)
            .into(h.image)
        // 📞 CALL CLICK
        h.contact.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:${p2.contact}")
            h.itemView.context.startActivity(intent)
        }

        // 📍 LOCATION CLICK (Google Maps)
        h.location.setOnClickListener {
            val uri = Uri.parse("geo:0,0?q=${p2.location}")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            intent.setPackage("com.google.android.apps.maps")
            h.itemView.context.startActivity(intent)
        }
    }

    override fun onCreateViewHolder(
        p: ViewGroup,
        v: Int
    ): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(p.context)
            .inflate(R.layout.private_item, p, false)
        return VH(view)
    }
}