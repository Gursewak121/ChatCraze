package com.gursewak.chatcraze

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUp : AppCompatActivity() {

    private lateinit var edtName : EditText
    private lateinit var edtEmail : EditText
    private lateinit var edtPassword : EditText
//    private lateinit var btnLogin : Button
    private lateinit var btnSignUP : Button
    private lateinit var auth: FirebaseAuth
    private lateinit var dbRef :DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        supportActionBar?.hide()

        edtName = findViewById(R.id.edtName)
        edtEmail = findViewById(R.id.edtEmail)
        edtPassword = findViewById(R.id.edtPassword)
//        btnLogin = findViewById(R.id.btnLogin)
        btnSignUP = findViewById(R.id.btnSignUp)

        auth = FirebaseAuth.getInstance()

        btnSignUP.setOnClickListener {
            val name = edtName.text.toString()
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()

            signUp(name,email,password)
        }

    }

    private fun signUp(name :String, email: String, password: String) {

        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener { task->
                if(task.isSuccessful){
                    userAddToDatabase(name,password,auth.currentUser?.uid!!)
                    val intent = Intent(this@SignUp, MainActivity::class.java)
//                    finish()
                    startActivity(intent)
                    Toast.makeText(this@SignUp,"User Sign Up Successfully", Toast.LENGTH_SHORT).show()

                }else{

                    Toast.makeText(this@SignUp,"Some Error occured during SignUp", Toast.LENGTH_SHORT).show()
                }
            }

    }

    private fun userAddToDatabase(name: String, password: String, uid: String) {
        dbRef = FirebaseDatabase.getInstance().getReference()
        dbRef.child("user").child(uid).setValue(User(name,password,uid))
    }
}