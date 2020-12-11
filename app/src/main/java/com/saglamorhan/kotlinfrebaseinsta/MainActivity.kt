package com.saglamorhan.kotlinfrebaseinsta

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()


        val currentUser = auth.currentUser
        if (currentUser != null) {
            val intent = Intent(applicationContext,FeedActivity::class.java)
            startActivity(intent)
            finish()
        }

        
    }

    fun signInClicked(view: View){

        auth.signInWithEmailAndPassword(userEmailText.text.toString(),passwordText.text.toString()).addOnCompleteListener {
            if (it.isSuccessful){
                Toast.makeText(applicationContext,"Welcome: ${auth.currentUser?.email.toString()}",Toast.LENGTH_LONG).show()
                val intent = Intent(applicationContext,FeedActivity::class.java)
                startActivity(intent)
                finish()
            }
        }.addOnFailureListener {
            Toast.makeText(applicationContext,it.localizedMessage.toString(),Toast.LENGTH_LONG).show()

        }

    }
    fun signUpClicked(view: View){

        val email = userEmailText.text.toString()
        val password = passwordText.text.toString()

        if (email != null && password != null){

            auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener { task ->

                if (task.isSuccessful){

                    val intent = Intent(applicationContext,FeedActivity::class.java)
                    startActivity(intent)
                    finish()
                }

            }.addOnFailureListener { exception ->
                if (exception != null){
                    Toast.makeText(applicationContext,exception.localizedMessage,Toast.LENGTH_LONG).show()
                }
            }

        }else{

            Toast.makeText(this,"Email ve password bos birakilamaz!",Toast.LENGTH_LONG).show()

        }



    }

}