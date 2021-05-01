package com.example.marvelhq.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelhq.R
import com.example.marvelhq.database.Comic
import com.example.marvelhq.model.Result
import com.squareup.picasso.Picasso

class FavoriteComicsAdapter(val callback: (Result) -> Unit) :
    RecyclerView.Adapter<FavoriteComicsAdapter.FavComicsViewHolder>() {

    private val listFavComics =  mutableListOf<Result>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteComicsAdapter.FavComicsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.comic_item, parent, false)
        return FavComicsViewHolder(view)
    }

    override fun getItemCount() = listFavComics.size

    override fun onBindViewHolder(holder: FavoriteComicsAdapter.FavComicsViewHolder, position: Int) {

        val comic = listFavComics.elementAt(position)
        val imageURL = comic.thumbnail.path + ".jpg"
        Picasso.get().load(imageURL).into(holder.image)
        holder.numberComic.text = comic.issueNumber.toString()


        holder.itemView.setOnClickListener {
            callback(comic)
        }
    }

    fun addFavComics(comics: List<Result>){
        listFavComics.addAll(comics)
        notifyDataSetChanged()
    }

    inner class FavComicsViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val image: ImageView = view.findViewById(R.id.image_comic)
        val numberComic: TextView = view.findViewById(R.id.number_comic)
    }

}