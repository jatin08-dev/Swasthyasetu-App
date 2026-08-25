package com.example.animation

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class AllDay : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AllDayAdapter
    private lateinit var firestore: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_all_day)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.recycler_view4)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        recyclerView = findViewById(R.id.recycler_view4)
        recyclerView.layoutManager = LinearLayoutManager(this)
        firestore = FirebaseFirestore.getInstance()

        //🔥 Firestore Collection
        val query: Query = firestore.collection("AllTime")

        val options= FirestoreRecyclerOptions.Builder<AllDayData>()
            .setQuery(query, AllDayData::class.java)
            .build()


        adapter = AllDayAdapter(options)
        recyclerView.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }
}