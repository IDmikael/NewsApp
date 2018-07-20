package com.idmikael.newstestapp.ui.fragments


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.idmikael.newstestapp.R
import com.idmikael.newstestapp.data.model.NewsItem
import com.idmikael.newstestapp.data.model.NewsResponse
import com.idmikael.newstestapp.data.model.NewsResponseItem
import com.idmikael.newstestapp.data.model.NewsSource
import com.idmikael.newstestapp.ui.fragments.recycler_view.NewsAdapter
import com.idmikael.newstestapp.ui.fragments.recycler_view.NewsClickListener
import com.idmikael.newstestapp.ui.fragments.view_model.AllNewsFragmentViewModel
import com.idmikael.newstestapp.ui.fragments.view_model.AllNewsFragmentViewModelFactory
import com.idmikael.newstestapp.utils.InfiniteScrollListener
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_news_from_database.*
import timber.log.Timber
import javax.inject.Inject


class NewsFromDatabase : Fragment() {

    @Inject
    lateinit var viewModelFactory: AllNewsFragmentViewModelFactory
    private lateinit var viewModel: AllNewsFragmentViewModel

    lateinit var adapter: NewsAdapter
    var itemsList: ArrayList<NewsResponseItem> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news_from_database, container, false)
    }

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this) // Providing the dependency
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(
                AllNewsFragmentViewModel::class.java)

        initializeRecycler()
        loadData()

        super.onViewCreated(view, savedInstanceState)
    }

    private fun initializeRecycler() {
        val layoutManager = LinearLayoutManager(activity!!)
        rvNewsFromDatabase.layoutManager = layoutManager
        rvNewsFromDatabase.itemAnimator = DefaultItemAnimator()

        rvNewsFromDatabase.addOnScrollListener(InfiniteScrollListener({ loadData() }, layoutManager))

        adapter = NewsAdapter(itemsList, activity!!, object : NewsClickListener {
            override fun onItemClicked(position: Int, cardItem: NewsItem) {
                //on edit button click
            }
        })

        rvNewsFromDatabase.adapter = adapter
    }

    private fun loadData() {

        viewModel.loadNewsFromDb(20, 20)

        viewModel.newsDatabaseResult().observe(this,
                Observer<List<NewsItem>> {
                    if (it != null) {
                        val position = adapter.itemCount
                        it.forEach {
                            adapter.addItem(NewsResponseItem(it.author, it.title, it.description, it.url, it.urlToImage,
                                    it.publishedAt, NewsSource(null, it.source)))
                        }
                        rvNewsFromDatabase.adapter = adapter
                        rvNewsFromDatabase.scrollToPosition(position - 20)
                    }
                })

        viewModel.newsError().observe(this, Observer<String> {
            if (it != null) {
                Timber.e(it)
            }
        })

        viewModel.newsLoader().observe(this, Observer<Boolean> {

        })
    }
}
