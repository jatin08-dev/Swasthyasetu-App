package com.example.animation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Ambulance : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ambulance)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val Ai = findViewById<TextView>(R.id.aichatbot)
        Ai.setOnClickListener {
            // ✅ WhatsApp Click FIXED
                val number = "918738030604"

                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse("https://wa.me/$number")
                startActivity(intent)

        }

    }
}