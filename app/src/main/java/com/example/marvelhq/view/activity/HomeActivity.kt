package com.example.marvelhq.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ProgressBar
import android.widget.Toast
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelhq.R
import com.example.marvelhq.model.Result
import com.example.marvelhq.view.adapter.HomeAdapter
import com.example.marvelhq.view.adapter.RecyclerScrollListener
import com.example.marvelhq.viewModel.HomeViewModel
import java.io.Serializable

class HomeActivity : AppCompatActivity() {


    private val recycler by lazy { findViewById<RecyclerView>(R.id.recycler_view)}
    private val viewModelHome by lazy { ViewModelProviders.of(this).get(HomeViewModel::class.java)}
    private val nextPageLoading by lazy { findViewById<ProgressBar>(R.id.nextLoading) }
    private val firstPageLoading by lazy { findViewById<ProgressBar>(R.id.firstLoading) }
    private val btnFav by lazy { findViewById<Button>(R.id.btn_fav) }
    lateinit var adapter: HomeAdapter

    private val recyclerScrollListener by lazy {
        RecyclerScrollListener {
            viewModelHome.requestNextPage()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        configRecycler()

        configLoading()

        showErrorMessage()

        configClicks()
    }

    private fun configClicks() {
        btnFav.setOnClickListener {
            val intent = Intent(this, FavoriteComicsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun configLoading() {
        viewModelHome.nextPageLoading.observe(this) {
            if (it) {
                nextPageLoading.visibility = VISIBLE
            } else {
                nextPageLoading.visibility = GONE
            }
        }

        viewModelHome.firstPageLoading.observe(this) {
            if (it) {
                firstPageLoading.visibility = VISIBLE
            } else {
                firstPageLoading.visibility = GONE
            }
        }
    }

    private fun configRecycler() {
        adapter = HomeAdapter { comic ->
            navigateToComicDetails(comic)
        }

        recycler.adapter = adapter
        val layoutManager = GridLayoutManager(this, 3)
        recycler.layoutManager = layoutManager

        recycler.addOnScrollListener(recyclerScrollListener)

        viewModelHome.comicsList.observe(this) { comics ->
            setRequestingNextPage()
            adapter.addComics(comics)
        }
    }

    private fun navigateToComicDetails(comic: Result) {
        val intent = Intent(this, ComicDetailsActivity::class.java)
        intent.putExtra("comic", comic as Serializable)
        startActivity(intent)
    }

    private fun showErrorMessage() {
        viewModelHome.errorMessage.observe(this, Observer {
            it?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun setRequestingNextPage() {
        recyclerScrollListener.requesting = false
    }

}