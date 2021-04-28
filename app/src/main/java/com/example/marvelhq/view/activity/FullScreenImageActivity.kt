package com.example.marvelhq.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.example.marvelhq.R
import com.squareup.picasso.Picasso

class FullScreenImageActivity : AppCompatActivity() {

    private val imageComicFull by lazy { findViewById<ImageView>(R.id.img_full) }
    private val btnClose by lazy { findViewById<Button>(R.id.btn_close) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_screen_image)

        val info = intent.extras

        val image = info?.getString("image")

        if (image != null) {
            Picasso.get().load(image).into(imageComicFull)
        }

        btnClose.setOnClickListener {
            onBackPressed()
        }
    }
}