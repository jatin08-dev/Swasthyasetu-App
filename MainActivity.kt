package com.example.animation

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AlphaAnimation
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.postDelayed
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val rlayout=findViewById<RelativeLayout>(R.id.main)
        val anidraw=rlayout.background as AnimationDrawable
        anidraw.setEnterFadeDuration(1000)
        anidraw.setEnterFadeDuration(1000)
        anidraw.start()
        val logo=findViewById<ImageView>(R.id.logo)
        val fin= AlphaAnimation(0f,1f)
        fin.duration=1500
        fin.fillAfter=true
        logo.startAnimation(fin)
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, IntroActivitty::class.java))
            finish()
        },2000)
    }
}