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

class HomeAdapter (val callback: (Result) -> Unit) :
RecyclerView.Adapter<HomeAdapter.ComicsViewHolder>(){

     private val comicList =  mutableListOf<Result>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapter.ComicsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.comic_item, parent, false)
        return ComicsViewHolder(view)
    }

    override fun getItemCount() = comicList.size

    override fun onBindViewHolder(holder: ComicsViewHolder, position: Int) {

        val comic = comicList.elementAt(position)
        val imageURL = comic.thumbnail.path + ".jpg"
        Picasso.get().load(imageURL).into(holder.image)
        holder.numberComic.text = comic.issueNumber

        holder.itemView.setOnClickListener {
            callback(comic)
        }
    }

    fun addComics(comics: List<Result>){
        comicList.addAll(comics)
        notifyDataSetChanged()
    }

    inner class ComicsViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val image: ImageView = view.findViewById(R.id.image_comic)
        val numberComic: TextView = view.findViewById(R.id.number_comic)
    }
}