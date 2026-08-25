package com.example.animation

import ads_mobile_sdk.p2
import ads_mobile_sdk.ro0.h
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.Toast

import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewpager2.widget.ViewPager2
import com.google.android.gms.common.util.CollectionUtils.listOf

import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Runnable

class HomePage : AppCompatActivity() {
    lateinit var ambulance: ImageButton
    lateinit var allday: ImageButton
    lateinit var govt: ImageButton
    lateinit var priHospital: ImageButton
    lateinit var btnHospital: CardView
    lateinit var btnAmbulance: CardView
    lateinit var auth: FirebaseAuth

    private lateinit var viewPager: ViewPager2
    private lateinit var handler: Handler

    private val slideRunnable = object : Runnable {
        override fun run() {
            val count = viewPager.adapter?.itemCount ?: 0
            if (count > 0) {
                viewPager.currentItem = (viewPager.currentItem + 1) % count
            }
            handler.postDelayed(this, 4000)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home_page)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.drawerlayout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        ambulance=findViewById(R.id.ambulance)
        ambulance.setOnClickListener{
            startActivity(Intent(this, Ambulance::class.java))
        }
        priHospital = findViewById(R.id.priHospital)
        priHospital.setOnClickListener {
            startActivity(Intent(this, Private::class.java))
        }

        allday = findViewById(R.id.alltime)
        allday.setOnClickListener {
            startActivity(Intent(this, AllDay::class.java))
        }

        govt = findViewById(R.id.govt)
        govt.setOnClickListener {
            startActivity(Intent(this, Goverment::class.java))
        }

        btnHospital=findViewById(R.id.btnHospital)
        btnHospital.setOnClickListener {
            val uri = Uri.parse("geo:0,0?q=nearby hospital")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            intent.setPackage("com.google.android.apps.maps")
            startActivity(intent)
        }

        btnAmbulance=findViewById(R.id.btnAmbulance)
        btnAmbulance.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:108")
            startActivity(intent)
        }

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        val drawer = findViewById<DrawerLayout>(R.id.drawerlayout)
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        val navView = findViewById<NavigationView>(R.id.navview)



        toolbar.setNavigationOnClickListener {
            drawer.openDrawer(GravityCompat.START)
        }

        bottomNav.setOnItemSelectedListener {
            auth = FirebaseAuth.getInstance()
            when (it.itemId) {
                R.id.home -> {
                    val intent=Intent(Intent.ACTION_VIEW)
                    intent.setPackage("home")
                    startActivity(intent)
                }

                R.id.logout1 -> {
                    auth.signOut()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }

                R.id.map -> {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.setPackage("com.google.android.apps.maps")
                    startActivity(intent)
                }

                R.id.profile -> {
                    val intent=Intent(this, Profile::class.java)
                    startActivity(intent)
                    finish()
                }
            }
            drawer.closeDrawer(GravityCompat.START)
            true
        }
        viewPager = findViewById(R.id.viewPagers)
        val tabLayout = findViewById<TabLayout>(R.id.tabLayouts)
        val images = listOf(
            R.drawable.slide1,
            R.drawable.slide2,
            R.drawable.slide3
        )

        viewPager.adapter = SliderAdapter(images)
        viewPager.setPageTransformer(ZoomPage())

        TabLayoutMediator(tabLayout, viewPager) { _, _ -> }.attach()

        handler = Handler(Looper.getMainLooper())

    }


    override fun onResume() {
        super.onResume()
        handler.postDelayed(slideRunnable, 4000)
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(slideRunnable)
    }
}
