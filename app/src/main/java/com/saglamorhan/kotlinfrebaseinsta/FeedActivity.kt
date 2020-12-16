package com.saglamorhan.kotlinfrebaseinsta

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FeedActivity : AppCompatActivity() {
    private lateinit var auth : FirebaseAuth
    private lateinit var db : FirebaseFirestore

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.options_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.add_post){
            //upload ativity
            val intent = Intent(applicationContext,UploadActivity::class.java)
            startActivity(intent)


        }else if(item.itemId == R.id.logout){
            //logout
            auth.signOut()
            val intent =Intent(applicationContext,MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        getDataFromFirestore()
    }


    fun getDataFromFirestore(){

        db.collection("Posts").addSnapshotListener { snapshot, exception ->

            if (exception != null){
                Toast.makeText(applicationContext,exception.localizedMessage.toString(),Toast.LENGTH_LONG).show()
            }else{

                if (snapshot != null){
                    if (!snapshot.isEmpty){

                        val documents = snapshot.documents
                        for (document in documents){
                            val comment = document.get("comment") as String
                            val useremail = document.get("userEmail") as String
                            val downoladUrl = document.get("downloadUrl") as String
                            val timestamp = document.get("date") as Timestamp
                            val date = timestamp.toDate()
                            println(comment)
                            println(useremail)
                            println(downoladUrl)
                            println(date)
                        }
                    }
                }

            }
        }


    }
}