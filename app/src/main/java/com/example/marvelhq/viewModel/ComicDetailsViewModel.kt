package com.example.marvelhq.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marvelhq.database.Comic
import com.example.marvelhq.database.ComicsDatabase
import com.example.marvelhq.model.Result
import kotlinx.coroutines.launch

class ComicDetailsViewModel : ViewModel() {

    lateinit var database: ComicsDatabase

    val comicsLiveData by lazy { MutableLiveData<List<Comic>>() }

    fun getComics() {
        viewModelScope.launch {
            val comics = database.comicsDao().getAll()
            comicsLiveData.postValue(comics)
        }
    }

    fun addComic(comic: Comic) {
        viewModelScope.launch {
            database.comicsDao().insertAll(comic)
        }.invokeOnCompletion {
            getComics()
        }
    }

    fun delete(comic: Comic) {
        viewModelScope.launch {
            database.comicsDao().delete(comic)
        }.invokeOnCompletion {
            getComics()
        }
    }
}