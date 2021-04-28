package com.example.marvelhq.view.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity
import com.example.marvelhq.R
import com.example.marvelhq.model.Result
import com.squareup.picasso.Picasso


class ComicDetailsActivity : AppCompatActivity() {

    private val imageComic by lazy { findViewById<ImageView>(R.id.img_comic) }
    private val imageBackground by lazy { findViewById<ImageView>(R.id.img_background) }
    private val textTitle by lazy { findViewById<TextView>(R.id.tv_title) }
    private val textDesc by lazy { findViewById<TextView>(R.id.tv_desc) }
    private val textPublished by lazy { findViewById<TextView>(R.id.tv_published) }
    private val textPrice by lazy { findViewById<TextView>(R.id.tv_price) }
    private val textPages by lazy { findViewById<TextView>(R.id.tv_pages) }
    private val btnBack by lazy { findViewById<Button>(R.id.btn_back) }
    private val btnFavorite by lazy { findViewById<ToggleButton>(R.id.btn_favorite) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comic_details)

        val info = intent.extras
        val comic = info?.getSerializable("comic") as Result


        Picasso.get().load(comic.images[0].path + ".jpg").into(imageComic)


        if (comic.images.size > 1){
            Picasso.get().load(comic.images[1].path + ".jpg").into(imageBackground)
        }else{
            Picasso.get().load(comic.images[0].path + ".jpg").into(imageBackground)
        }


        textTitle.text = comic.title
        textDesc.text = comic.description


        comic.dates.forEach {
            if(it.type == "focDate"){
                textPublished.text = it.date
            }
        }

        comic.prices.forEach {
            if(it.type == "printPrice"){
                textPrice.text = it.price
            }
        }

        textPages.text = comic.pageCount


        imageComic.setOnClickListener {
            val intent = Intent(this, FullScreenImageActivity::class.java)
            intent.putExtra("image", comic.images[0].path + ".jpg")
            startActivity(intent)
        }

        btnFavorite.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked){
               // btnFavorite.setBackgroundResource(R.drawable.ic_baseline_favorite_24)

            }else{
             //  btnFavorite.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24)
            }
        }

        btnBack.setOnClickListener {
            onBackPressed()
        }
    }



}