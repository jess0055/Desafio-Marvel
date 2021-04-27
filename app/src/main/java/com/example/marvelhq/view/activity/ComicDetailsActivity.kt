package com.example.marvelhq.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.marvelhq.R
import com.example.marvelhq.model.Result
import com.squareup.picasso.Picasso

class ComicDetailsActivity : AppCompatActivity() {

    private val imageComic by lazy { findViewById<ImageView>(R.id.img_comic) }
    private val imageBackgroung by lazy { findViewById<ImageView>(R.id.img_background) }
    private val textTitle by lazy { findViewById<TextView>(R.id.tv_title) }
    private val textDesc by lazy { findViewById<TextView>(R.id.tv_desc) }
    private val textPublished by lazy { findViewById<TextView>(R.id.tv_published) }
    private val textPrice by lazy { findViewById<TextView>(R.id.tv_price) }
    private val textPages by lazy { findViewById<TextView>(R.id.tv_pages) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comic_details)

        val info = intent.extras
        val comic = info?.getSerializable("comic") as Result

        val imageURL = comic.thumbnail.path + ".jpg"
        Picasso.get().load(imageURL).into(imageComic)


        val imageURL2 = comic.thumbnail.path + ".jpg"
        Picasso.get().load(imageURL2).into(imageBackgroung)


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
    }



}