package com.example.marvelhq.view.activity

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.marvelhq.R
import com.example.marvelhq.database.Comic
import com.example.marvelhq.database.ComicsDatabase
import com.example.marvelhq.model.Result
import com.example.marvelhq.viewModel.ComicDetailsViewModel
import com.squareup.picasso.Picasso
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class ComicDetailsActivity : AppCompatActivity() {

    private val dataBase by lazy {
        Room.databaseBuilder(
            applicationContext,
            ComicsDatabase::class.java,
            "database"
        ).build()
    }

    private val viewModel by lazy { ViewModelProvider(this).get(ComicDetailsViewModel::class.java) }
    private val imageComic by lazy { findViewById<ImageView>(R.id.img_comic) }
    private val imageBackground by lazy { findViewById<ImageView>(R.id.img_background) }
    private val textTitle by lazy { findViewById<TextView>(R.id.tv_title) }
    private val textDesc by lazy { findViewById<TextView>(R.id.tv_desc) }
    private val textPublished by lazy { findViewById<TextView>(R.id.tv_published) }
    private val textPrice by lazy { findViewById<TextView>(R.id.tv_price) }
    private val textPages by lazy { findViewById<TextView>(R.id.tv_pages) }
    private val btnBack by lazy { findViewById<Button>(R.id.btn_back) }
    private val btnFavorite by lazy { findViewById<ToggleButton>(R.id.btn_favorite) }

    lateinit var comic: Result

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comic_details)

        val info = intent.extras
        comic = info?.getSerializable("comic") as Result

        viewModel.database = dataBase
        viewModel.getComics()

        configImages()
        configTexts()
        configClicks()
        isFavorite()
    }

    private fun configClicks() {
        imageComic.setOnClickListener {
            val intent = Intent(this, FullScreenImageActivity::class.java)
            intent.putExtra("image", comic.images[0].path + ".jpg")
            startActivity(intent)
        }

        btnBack.setOnClickListener {
            onBackPressed()
        }

        btnFavorite.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                showAsFavorite()
                Toast.makeText(this, "Added to favorites", Toast.LENGTH_SHORT).show()
                addComic(comic.id)
            } else {
                showAsUnFavorite()
                Toast.makeText(this, "Removed from favorites", Toast.LENGTH_SHORT).show()
                deleteComic(comic.id)
            }
        }
    }

    private fun isFavorite() {
        val comic = findComic(comic.id)
        if (comic != null) {
            btnFavorite.isChecked = true
            showAsFavorite()
        }
    }

    private fun showAsUnFavorite() {
        btnFavorite.setCompoundDrawablesWithIntrinsicBounds(
            R.drawable.ic_baseline_favorite_border_24,
            0,
            0,
            0
        )
    }

    private fun showAsFavorite() {
        btnFavorite.setCompoundDrawablesWithIntrinsicBounds(
            R.drawable.ic_baseline_favorite_24,
            0,
            0,
            0
        )
    }

    private fun configTexts() {
        textTitle.text = comic.title

        textDesc.text = comic.description
        textPages.text = comic.pageCount
        textDesc.text = comic.description


        comic.dates.forEach {
            if (it.type == "onsaleDate") {
                textPublished.text = formatDate(it.date)
            }
        }

        comic.prices.forEach {
            if (it.type == "printPrice") {
                textPrice.text = it.price
            }
        }
    }

    private fun formatDate(inputText: String): String {
        val outputFormat: DateFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH)
        val inputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)
        val date: Date = inputFormat.parse(inputText)
        val outputText = outputFormat.format(date)

        return outputText
    }

    private fun configImages() {
        Picasso.get().load(comic.thumbnail.path + ".jpg").into(imageComic)
        Picasso.get().load(comic.thumbnail.path + ".jpg").into(imageBackground)
    }

    private fun addComic(id: Int) {
        val comic = findComic(id)
        if(comic == null){
            val newComic = Comic(idApi = id)
            viewModel.addComic(newComic)
        }
    }

    private fun deleteComic(id: Int) {
        val comic = findComic(id)
        if(comic != null){
            viewModel.delete(comic)
        }
    }

    private fun findComic(id: Int) : Comic? {
        var comic : Comic? = null
        viewModel.comicsLiveData.observe(this){
            it.forEach { cc ->
                if(cc.idApi == id){
                    comic = cc
                }
            }
        }
        return comic
    }
}