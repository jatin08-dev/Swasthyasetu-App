package com.example.animation

import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class Profile : AppCompatActivity() {

    private lateinit var firestore: FirebaseFirestore
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        val username = findViewById<TextView>(R.id.username)
        val useremail = findViewById<TextView>(R.id.useremail)
        val mobile = findViewById<TextView>(R.id.usermobile)
        val password = findViewById<TextView>(R.id.userpassword)


    val userId = auth.currentUser?.uid
          if (userId != null) {
              firestore.collection("UserInfo")
                  .document(userId)
                  .get()
                  .addOnSuccessListener { document ->
                      if (document != null) {
                          val user = document.toObject(ProfileData::class.java)
                          username.text="Name: ${user?.username}"
                          useremail.text="Email: ${user?.useremail}"
                          mobile.text="Mobile: ${user?.usermobile}"
                          password.text="Password: ${user?.userpassword}"
                    }
                  }
        }
    }
}
