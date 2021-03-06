package com.example.tianhao.snapchat

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.provider.MediaStore
import android.content.Intent
import android.content.pm.PackageManager
import android.view.View
import java.util.jar.Manifest
import android.graphics.Bitmap
import android.support.annotation.NonNull
import android.Manifest.permission
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.OnFailureListener
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import android.widget.Toast
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream
import java.util.*


class CreateSnapActivity : AppCompatActivity() {


    var createSnapImageView: ImageView? = null
    var messageEditText: EditText? = null
    val imageName = UUID.randomUUID().toString() + ".jpg"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_snap)
        createSnapImageView = findViewById(R.id.createSnapImageVIew)
        messageEditText = findViewById(R.id.messageEditText)

    }

    fun getPhoto() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, 1)
    }

    fun chooseImageClicked(view: View){
        if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        } else {
            getPhoto()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val selectedImage = data!!.data
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null) {
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, selectedImage)
                createSnapImageView?.setImageBitmap(bitmap)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getPhoto()
            }
        }
    }

    fun nextClicked(view: View){
        // Get the data from an ImageView as bytes
        try{
            createSnapImageView?.setDrawingCacheEnabled(true)
            createSnapImageView?.buildDrawingCache()
            val bitmap = (createSnapImageView?.getDrawable() as BitmapDrawable).bitmap
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val data = baos.toByteArray()

            val uploadTask = FirebaseStorage.getInstance().getReference().child("images").child(imageName).putBytes(data)

            uploadTask.addOnFailureListener(OnFailureListener {
                // Handle unsuccessful uploads
                Toast.makeText(this, "UPloadFailed", Toast.LENGTH_SHORT).show()
            }).addOnSuccessListener(OnSuccessListener<Any> {

                Log.i("URL", "upload sucessfully")
                val intent = Intent(this,ChooseUserActivity::class.java)
                intent.putExtra("imageName", imageName)
                intent.putExtra("message", messageEditText?.text.toString())
                startActivity(intent)
            })

        }catch(e:Exception){
            val intent = Intent(this,ChooseUserActivity::class.java)
            startActivity(intent)
            print("some error occrued")
        }

    }



}
