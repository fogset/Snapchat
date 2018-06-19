package com.example.tianhao.snapchat

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import android.widget.Toast
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.AuthResult
import com.google.android.gms.tasks.Task
import android.support.annotation.NonNull
import com.google.android.gms.tasks.OnCompleteListener
import android.R.attr.password
import android.content.Intent
import android.support.v4.app.FragmentActivity
import android.util.Log
import android.R.attr.password
import android.R.attr.password






class MainActivity : AppCompatActivity() {
    lateinit var emailEditText: EditText
    lateinit var passwordEditText: EditText
    val mAuth = FirebaseAuth.getInstance()
    var email = ""
    var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        emailEditText = findViewById(R.id.emailEditText) as EditText
        passwordEditText = findViewById(R.id.passwordEditText) as EditText
        email = emailEditText.text.toString()
        password = passwordEditText.text.toString()

        if (mAuth.currentUser != null) {
            logIn()
        }
    }
    fun loginButton(view: View) {

        Toast.makeText(this, "email: " + emailEditText.text.toString() + " password:" +passwordEditText.text.toString(), Toast.LENGTH_LONG).show()
        if (! emailEditText.text.toString().isEmpty() && !emailEditText.text.toString().isEmpty()) {
            mAuth.signInWithEmailAndPassword(emailEditText.text.toString(), passwordEditText.text.toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information

                            Toast.makeText(this, "signInWithEmail:success", Toast.LENGTH_LONG).show()
                            logIn()
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(this, "Authentication failed", Toast.LENGTH_LONG).show()

                        }

                        // ...
                    }


        }else{
            Toast.makeText(this, "Please fill up the credentials :(", Toast.LENGTH_LONG).show()
        }
    }


        //Toast.makeText(this, "Login Failed. Try Again.", Toast.LENGTH_LONG).show()

    fun goClick(view: View){
        //Check if we can log in the user
        if (! emailEditText.text.toString().isEmpty() && !emailEditText.text.toString().isEmpty()) {
            mAuth.createUserWithEmailAndPassword(emailEditText.text.toString(), passwordEditText.text.toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(this, "createUserWithEmail:success", Toast.LENGTH_LONG).show()
                            logIn()

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(this, "user already existed or wrong format", Toast.LENGTH_LONG).show()

                        }

                        // ...
                    }
        }else{
            Toast.makeText(this, "Please fill up the credentials :(", Toast.LENGTH_LONG).show()
        }
    }
    fun logIn(){
        //Move to next Activity
        val intent = Intent(this, SnapsActivity::class.java)
        startActivity(intent)

    }
}
