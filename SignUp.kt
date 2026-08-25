package com.example.animation

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class SignUp : AppCompatActivity() {
    lateinit var firestore: FirebaseFirestore
    lateinit var auth: FirebaseAuth
    lateinit var names: TextInputEditText
    lateinit var email: TextInputEditText
    lateinit var mobile: TextInputEditText
    lateinit var password: TextInputEditText
    lateinit var confirmpassword: TextInputEditText
    lateinit var click: TextView
    lateinit var signup: MaterialButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        auth = FirebaseAuth.getInstance()
        names = findViewById(R.id.etname)
        email = findViewById(R.id.etemail2)
        mobile=findViewById(R.id.mobile)
        password = findViewById(R.id.pass)
        confirmpassword = findViewById(R.id.confirmpass)

        firestore= FirebaseFirestore.getInstance()


        signup = findViewById(R.id.signup)
        signup.setOnClickListener {
            val username = names.text.toString().trim()
            val useremail = email.text.toString().trim()
            val usermobile = mobile.text.toString().trim()
            val userpassword = password.text.toString().trim()
            val userconfirmpassword = confirmpassword.text.toString().trim()

            if (username.isEmpty() || useremail.isEmpty() || userpassword.isEmpty() || userconfirmpassword.isEmpty() || usermobile.isEmpty()) {
                Toast.makeText(this, "Enter All Fields", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (usermobile.length < 10) {
                Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
            if (userpassword.length < 6) {
                Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
            if (userpassword != userconfirmpassword) {
                Toast.makeText(this, "Password do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }



            auth.createUserWithEmailAndPassword(useremail, userpassword).addOnCompleteListener {
                if (it.isSuccessful) {
                    val userId = auth.currentUser?.uid
                    val swasthysetu = hashMapOf(
                        "username" to username,
                        "useremail" to useremail,
                        "usermobile" to usermobile,
                        "userpassword" to userpassword
                    )
                    if (userId != null) {
                        firestore.collection("UserInfo").document(userId).set(swasthysetu)
                            .addOnSuccessListener {
                                Toast.makeText(
                                    this,
                                    "User Registered Successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                                startActivity(Intent(this, LogInPage::class.java))
                            }.addOnFailureListener {
                                Toast.makeText(this, "User Registered Failed", Toast.LENGTH_SHORT)
                                    .show()
                            }


                    }
                }
            }
        }
        click = findViewById(R.id.login)
        click.setOnClickListener {

                val intent = Intent(this, LogInPage::class.java)
                startActivity(intent)
            }
        }
    }
