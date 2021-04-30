package com.example.marvelhq.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelhq.R
import com.example.marvelhq.model.Result
import com.squareup.picasso.Picasso

class FavoriteComicsAdapter(private val listFavComics: List<Result>, val callback: (Result) -> Unit) :
    RecyclerView.Adapter<FavoriteComicsAdapter.FavComicsViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteComicsAdapter.FavComicsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.comic_item, parent, false)
        return FavComicsViewHolder(view)
    }

    override fun getItemCount() = listFavComics.size

    override fun onBindViewHolder(holder: FavoriteComicsAdapter.FavComicsViewHolder, position: Int) {

        val comic = listFavComics.elementAt(position)
        val imageURL = comic.thumbnail.path + ".jpg"
        Picasso.get().load(imageURL).into(holder.image)
        holder.numberComic.text = comic.issueNumber

        holder.itemView.setOnClickListener {
            callback(comic)
        }
    }

    inner class FavComicsViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val image: ImageView = view.findViewById(R.id.image_comic)
        val numberComic: TextView = view.findViewById(R.id.number_comic)
    }

}