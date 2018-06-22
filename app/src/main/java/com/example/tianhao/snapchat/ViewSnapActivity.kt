package com.example.tianhao.snapchat

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import java.net.HttpURLConnection
import java.net.URL

class ViewSnapActivity : AppCompatActivity() {

    var messageTextView: TextView? = null
    var snapImageView: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_snap)

        messageTextView = findViewById(R.id.messageText)
        snapImageView = findViewById(R.id.snapImageView)

        messageTextView?.text = intent.getStringExtra("message")

        val task = ImageDownloader()
        val myImage: Bitmap
        try {
            myImage = task.execute("https://firebasestorage.googleapis.com/v0/b/snapchatoreo-272ab.appspot.com/o/images%2F77357573-9a39-44f3-9ae2-c0daab85f960.jpg?alt=media&token=fe67a4d4-305c-4c01-bb8c-302378fcc69c").get()
            snapImageView?.setImageBitmap(myImage)
        } catch (e: Exception) {
            e.printStackTrace()
        }


    }

    inner class ImageDownloader : AsyncTask<String, Void, Bitmap>() {

        override fun doInBackground(vararg urls: String): Bitmap? {

            val result = ""
            val url: URL
            var urlConnection: HttpURLConnection? = null

            try {
                url = URL(urls[0])
                urlConnection = url.openConnection() as HttpURLConnection
                urlConnection.connect()
                val `in` = urlConnection.inputStream

                return BitmapFactory.decodeStream(`in`)

            } catch (e: Exception) {
                e.printStackTrace()
                return null
            }

        }
    }
}
