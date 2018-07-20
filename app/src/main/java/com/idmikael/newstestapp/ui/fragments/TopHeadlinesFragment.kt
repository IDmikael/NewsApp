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
import com.idmikael.newstestapp.ui.fragments.recycler_view.NewsAdapter
import com.idmikael.newstestapp.ui.fragments.recycler_view.NewsClickListener
import com.idmikael.newstestapp.ui.fragments.view_model.AllNewsFragmentViewModel
import com.idmikael.newstestapp.ui.fragments.view_model.AllNewsFragmentViewModelFactory
import com.idmikael.newstestapp.utils.InfiniteScrollListener
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_top_headlines.*
import timber.log.Timber
import javax.inject.Inject

class TopHeadlinesFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: AllNewsFragmentViewModelFactory
    private lateinit var viewModel: AllNewsFragmentViewModel

    lateinit var adapter: NewsAdapter
    var itemsList: ArrayList<NewsResponseItem> = ArrayList()
    private var page = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_top_headlines, container, false)
    }

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this) // Providing the dependency
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(
                AllNewsFragmentViewModel::class.java)

        initializeRecycler()

        pbTopHeadlines.visibility = View.VISIBLE
        loadData()

        super.onViewCreated(view, savedInstanceState)
    }

    private fun initializeRecycler() {
        val layoutManager = LinearLayoutManager(activity!!)
        rvTopHeadlines.layoutManager = layoutManager
        rvTopHeadlines.itemAnimator = DefaultItemAnimator()

        rvTopHeadlines.addOnScrollListener(InfiniteScrollListener({ loadData() }, layoutManager))

        adapter = NewsAdapter(itemsList, activity!!, object : NewsClickListener {
            override fun onItemClicked(position: Int, cardItem: NewsItem) {
                //on item click
            }
        })

        rvTopHeadlines.adapter = adapter
    }

    private fun loadData() {

        itemsList.clear()
        viewModel.loadTopHeadlines(page)
        page++

        viewModel.newsResult().observe(this,
                Observer<NewsResponse> {
                    if (it != null) {
                        val position = adapter.itemCount
                        //adapter.addCardsList(it)
                        it.articles.forEach {
                            itemsList.add(it)
                        }

                        adapter.setItemsList(itemsList)
                        rvTopHeadlines.adapter = adapter
                        rvTopHeadlines.scrollToPosition(position - 20)
                    }
                })

        viewModel.newsError().observe(this, Observer<String> {
            if (it != null) {
                Timber.e(it)
            }
        })

        viewModel.newsLoader().observe(this, Observer<Boolean> {
            if (it == false) pbTopHeadlines.visibility = View.GONE
        })
    }

}
