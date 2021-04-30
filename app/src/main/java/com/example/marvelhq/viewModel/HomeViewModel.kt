package com.example.marvelhq.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.marvelhq.model.ComicResponse
import com.example.marvelhq.model.Result
import com.example.marvelhq.repository.RepositoryApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.UnknownHostException

class HomeViewModel : ViewModel() {

    val comicsList = MutableLiveData<List<Result>>()
    val errorMessage = MutableLiveData<String>()
    val nextPageLoading by lazy { MutableLiveData<Boolean>() }
    val firstPageLoading by lazy { MutableLiveData<Boolean>() }
    var nextPage = 0

    private val repository = RepositoryApi()

    init {
        getAllComics()
    }

    private fun getAllComics() = CoroutineScope(IO).launch {

        try {
            firstPageLoading.postValue(true)
            val comicsFinal = mutableListOf<Result>()
            repository.getComicsService().let { comicsPage ->
                val comics = comicsPage.data.results
                val img = "http://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available"

                comics.forEach {
                    if(it.thumbnail.path != img){
                        comicsFinal.add(it)
                    }
                }
                comicsList.postValue(comicsFinal)
                updateNextPage(comicsPage)
            }
        }catch (error: Throwable){
            handleError(error)
        }finally {
            firstPageLoading.postValue(false)
        }
    }

    private fun updateNextPage(comicsPage: ComicResponse) {
        nextPage = comicsPage.data.offset.plus(comicsPage.data.limit) ?: 1
    }

    fun requestNextPage() = CoroutineScope(IO).launch {
        try {
            nextPageLoading.postValue(true)
            val comicsFinal = mutableListOf<Result>()
            repository.getComicsService(nextPage).let { comicsPage ->
                val comics = comicsPage.data.results
                val img = "http://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available"

                comics.forEach {
                    if(it.thumbnail.path != img){
                        comicsFinal.add(it)
                    }
                }
                comicsList.postValue(comicsFinal)
                updateNextPage(comicsPage)
            }
        } catch (error: Throwable) {
            handleError(error)
        } finally {
            nextPageLoading.postValue(false)
        }
    }

    private fun handleError(error: Throwable) {
        when(error){
            is HttpException -> errorMessage.postValue("Erro de conexão código: ${error.code()}")
            is UnknownHostException -> errorMessage.postValue("Verifique sua conexão")
        }
    }
}