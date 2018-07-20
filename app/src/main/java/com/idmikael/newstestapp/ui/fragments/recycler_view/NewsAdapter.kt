package com.idmikael.newstestapp.ui.fragments.recycler_view

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.idmikael.newstestapp.R
import com.idmikael.newstestapp.data.model.NewsItem
import com.idmikael.newstestapp.data.model.NewsResponseItem
import com.idmikael.newstestapp.utils.getFormat
import com.idmikael.newstestapp.utils.simpleDateFormat
import kotlinx.android.synthetic.main.item_news.view.*
import java.text.SimpleDateFormat
import java.util.*

class NewsAdapter(internal var items: ArrayList<NewsResponseItem>,
                  var context: Context,
                  var clickListener: NewsClickListener
)  : RecyclerView.Adapter<NewsAdapter.NewsItemViewHolder>()  {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: NewsItemViewHolder, position: Int) {
        val tip = items[position]
        holder.bindItem(tip, context, clickListener)
    }

    fun setItemsList(list: ArrayList<NewsResponseItem>){
        items = list
        notifyDataSetChanged()
    }

    fun addItem(item: NewsResponseItem){
        items.add(item)
        this.notifyItemInserted(items.size)
    }

    fun removeItem(position: Int){
        items.removeAt(position)
        this.notifyItemRemoved(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsItemViewHolder {
        val view = View.inflate(parent.context, R.layout.item_news, null)
        return NewsItemViewHolder(view)
    }

    class NewsItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(item: NewsResponseItem, context: Context, listener: NewsClickListener) {

            itemView.tvItemNewsTitle.text = item.title ?: ""
            itemView.tvItemNewsDesc.text = item.description ?: ""
            itemView.tvItemNewsSource.text = item.source.name ?: ""
            itemView.tvItemNewsDate.text = getFormat(item.publishedAt!!).simpleDateFormat()

            Glide.with(context)
                    .load(item.urlToImage ?: R.drawable.icon_news)
                    .into(itemView.ivItemNewsImage)

            //itemView.ibItemEdit.setOnClickListener({ listener.onItemClicked(this.adapterPosition, item) })
        }


    }
}