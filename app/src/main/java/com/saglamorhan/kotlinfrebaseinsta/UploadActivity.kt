package com.saglamorhan.kotlinfrebaseinsta

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_upload.*
import java.lang.Exception

class UploadActivity : AppCompatActivity() {

    var selectedPicture : Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)
    }
    fun imageViewClicked(view: View){

     //galeriye gitme islemleri
        //izin islemleri icin
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            //Manifeste izni eklemeyi unutma!!!
            //izin verilmediyse calisacak
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),1)
        }else{
            val intent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent,2)
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

        if (requestCode == 1){
            if (grantResults.size >0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                val intent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(intent,2)

            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 2 && resultCode == RESULT_OK && data != null){

            selectedPicture = data.data

            try {

                if (selectedPicture != null){

                    if (Build.VERSION.SDK_INT >=28){
                        val source = ImageDecoder.createSource(contentResolver,selectedPicture!!)
                        val bitmap = ImageDecoder.decodeBitmap(source)
                        uploadImageView.setImageBitmap(bitmap)

                    }else{
                        val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver,selectedPicture)
                        uploadImageView.setImageBitmap(bitmap)
                    }

                }

            }catch (e: Exception){
                e.printStackTrace()
            }

        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    fun uoploadClicked(view: View){

    }
}