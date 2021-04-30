package com.example.marvelhq.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marvelhq.database.Comic
import com.example.marvelhq.database.ComicsDatabase
import com.example.marvelhq.model.Result
import com.example.marvelhq.repository.RepositoryApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.UnknownHostException

class FavoriteViewModel : ViewModel() {

    lateinit var database: ComicsDatabase
    val favComicsLiveData by lazy { MutableLiveData<List<Result>>() }
    private val repository = RepositoryApi()
    val errorMessage = MutableLiveData<String>()
    val loading by lazy { MutableLiveData<Boolean>() }

    private val listFavs = mutableListOf<List<Comic>>()
    private val listIds = mutableListOf<Int>()
    private val listResults = mutableListOf<Result>()


    fun getFavComics() {
        viewModelScope.launch {
            val comics = database.comicsDao().getAll()
            listFavs.add(comics)
            getIDs()
            getComic()
        }
    }

    private fun getIDs() {
        listFavs.forEach { comicsDataBase ->
            comicsDataBase.forEach { favComic ->
                listIds.add(favComic.idApi)
            }
        }
    }

    private fun getComic() = CoroutineScope(IO).launch {
        loading.postValue(true)
        try {
            listIds.forEach { idComic ->
                repository.getComicByIdService(idComic).let { result ->
                    result.data.results.forEach {
                        listResults.add(it)
                    }
                }
            }
            favComicsLiveData.postValue(listResults)
        } catch (error: Throwable) {
            handleError(error)
        } finally {
            loading.postValue(false)
        }
    }

    private fun handleError(error: Throwable) {
        when (error) {
            is HttpException -> errorMessage.postValue("Erro de conexão código: ${error.code()}")
            is UnknownHostException -> errorMessage.postValue("Verifique sua conexão")
        }
    }


}