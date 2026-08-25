package com.example.animation

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.gms.common.util.CollectionUtils.listOf
import com.google.android.material.button.MaterialButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class IntroActivitty : AppCompatActivity() {
    private lateinit var viewPager: ViewPager2
    private lateinit var btnNext: MaterialButton
    private lateinit var btnSkip: MaterialButton
    private lateinit var tabLayout: TabLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_intro_activitty)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        viewPager=findViewById(R.id.viewPager)
        btnNext=findViewById(R.id.btnNext)
        btnSkip=findViewById(R.id.btnSkip)
        tabLayout=findViewById(R.id.tabDots)

        val list=listOf(IntroItem(R.drawable.firstpage,"Find Nearby Hospitals","Locate hospitals and clinics near you easily."),
            IntroItem(R.drawable.secondpage,"Get Details & Reviews","See hospital info, ratings, and patient reviews."),
            IntroItem(R.drawable.thirdpage,"24/7 Availability","Get help anytime, anywhere."))
        viewPager.adapter= IntroAdapter(list)
        TabLayoutMediator(tabLayout,viewPager){_,_->}.attach()
        btnNext.setOnClickListener {
            if (viewPager.currentItem<list.size-1){
                viewPager.currentItem+=1
            }
            else
            {
                openMain()
            }
        }
        btnSkip.setOnClickListener {
            openMain()
        }
    }
    private fun openMain(){
        startActivity(Intent(this, LogInPage::class.java))
    }
}