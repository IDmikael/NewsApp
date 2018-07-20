package com.idmikael.newstestapp.data.repository

import com.idmikael.newstestapp.data.model.NewsItem
import com.idmikael.newstestapp.data.model.NewsResponse
import com.idmikael.newstestapp.data.remote.ApiInterface
import com.idmikael.newstestapp.persistance.dao.NewsDao
import com.idmikael.newstestapp.utils.Utils
import io.reactivex.Observable
import timber.log.Timber
import javax.inject.Inject

class NewsRepository @Inject constructor(private val apiInterface: ApiInterface,
                                         private val newsDao: NewsDao, private val utils: Utils) {

    fun getNews(page: Int): Observable<NewsResponse> {
        val hasConnection = utils.isConnectedToInternet()

        if (!hasConnection) {
            return Observable.error(Throwable("No internet connection"))
        }

        return getNewsFromApi(page)
    }

    fun getTopHeadlines(page: Int): Observable<NewsResponse> {
        val hasConnection = utils.isConnectedToInternet()

        if (!hasConnection) {
            return Observable.error(Throwable("No internet connection"))
        }

        return getTopHeadlinesFromApi(page)
    }

    private fun getNewsFromApi(page: Int): Observable<NewsResponse> {
        return apiInterface.getAllNews("wsj.com,nytimes.com", page)
                .doOnNext {
                    Timber.e(it.toString())

                    it.articles.forEach {
                        newsDao.insertNewsItem(NewsItem(null, it.author, it.title, it.description,
                                it.url, it.urlToImage, it.publishedAt, it.source.name))
                    }
                }
    }

    private fun getTopHeadlinesFromApi(page: Int): Observable<NewsResponse> {
        return apiInterface.getTopHeadlines("us", page)
                .doOnNext {
                    Timber.e(it.toString())

                    it.articles.forEach {
                        newsDao.insertNewsItem(NewsItem(null, it.author, it.title, it.description,
                                it.url, it.urlToImage, it.publishedAt, it.source.name))
                    }

                }
    }

    fun getNewsFromDb(limit: Int, offset: Int): Observable<List<NewsItem>> {
        return newsDao.queryNews(limit, offset)
                .toObservable()
                .doOnNext {
                    Timber.e(it.size.toString())
                }
    }
}