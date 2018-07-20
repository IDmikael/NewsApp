package com.idmikael.newstestapp.ui.fragments.view_model

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.idmikael.newstestapp.data.model.NewsItem
import com.idmikael.newstestapp.data.model.NewsResponse
import com.idmikael.newstestapp.data.repository.NewsRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class AllNewsFragmentViewModel @Inject constructor(
        private val repository: NewsRepository) : ViewModel() {

    var newsResult: MutableLiveData<NewsResponse> = MutableLiveData()
    var newsDatabaseResult: MutableLiveData<List<NewsItem>> = MutableLiveData()
    var newsError: MutableLiveData<String> = MutableLiveData()
    var newsLoader: MutableLiveData<Boolean> = MutableLiveData()
    private lateinit var disposableObserver: DisposableObserver<NewsResponse>
    private lateinit var disposableDatabaseObserver: DisposableObserver<List<NewsItem>>

    fun newsResult(): LiveData<NewsResponse> {
        return newsResult
    }

    fun newsDatabaseResult(): LiveData<List<NewsItem>> {
        return newsDatabaseResult
    }

    fun newsError(): LiveData<String> {
        return newsError
    }

    fun newsLoader(): LiveData<Boolean> {
        return newsLoader
    }

    fun loadNews(page: Int) {

        disposableObserver = object : DisposableObserver<NewsResponse>() {
            override fun onComplete() {

            }

            override fun onNext(response: NewsResponse) {
                newsResult.postValue(response)
                newsLoader.postValue(false)
            }

            override fun onError(e: Throwable) {
                newsError.postValue(e.message)
                newsLoader.postValue(false)
            }
        }

        repository.getNews(page)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .debounce(400, TimeUnit.MILLISECONDS)
                .subscribe(disposableObserver)
    }

    fun loadTopHeadlines(page: Int){

        disposableObserver = object : DisposableObserver<NewsResponse>() {
            override fun onComplete() {

            }

            override fun onNext(response: NewsResponse) {
                newsResult.postValue(response)
                newsLoader.postValue(false)
            }

            override fun onError(e: Throwable) {
                newsError.postValue(e.message)
                newsLoader.postValue(false)
            }
        }

        repository.getTopHeadlines(page)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .debounce(400, TimeUnit.MILLISECONDS)
                .subscribe(disposableObserver)
    }

    fun loadNewsFromDb(limit: Int, offset: Int){

        disposableDatabaseObserver = object : DisposableObserver<List<NewsItem>>() {
            override fun onComplete() {

            }

            override fun onNext(response: List<NewsItem>) {
                newsDatabaseResult.postValue(response)
                newsLoader.postValue(false)
            }

            override fun onError(e: Throwable) {
                newsError.postValue(e.message)
                newsLoader.postValue(false)
            }
        }

        repository.getNewsFromDb(limit, offset)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .debounce(400, TimeUnit.MILLISECONDS)
                .subscribe(disposableDatabaseObserver)
    }

    fun disposeElements() {
        if (!disposableObserver.isDisposed) disposableObserver.dispose()
    }

}