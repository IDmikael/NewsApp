package com.idmikael.newstestapp.ui.fragments.recycler_view

import com.idmikael.newstestapp.data.model.NewsItem

interface NewsClickListener {
    fun onItemClicked(position: Int, cardItem: NewsItem)
}