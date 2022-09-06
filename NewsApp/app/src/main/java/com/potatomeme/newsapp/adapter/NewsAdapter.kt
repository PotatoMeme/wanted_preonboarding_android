package com.potatomeme.newsapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.potatomeme.newsapp.R
import com.potatomeme.newsapp.gson.Article
import com.potatomeme.newsapp.helper.AppHelper


class NewsAdapter(private var news: List<Article>) :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(v: View, data: Article, pos: Int)
    }

    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    fun setNewsList(news: List<Article>){
        this.news = news
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val newsImage: ImageView
        val newsTitle: TextView
        val newsReporter: TextView
        val newsTime: TextView

        init {
            newsImage = view.findViewById(R.id.news_image)
            newsTitle = view.findViewById(R.id.news_title)
            newsReporter = view.findViewById(R.id.news_reporter)
            newsTime = view.findViewById(R.id.news_time)
        }

        fun bind(item: Article) {
            newsTitle.text = item.title
            newsTime.text = AppHelper.intervalBetweenDate(item.publishedAt)
            newsReporter.text = item.author
            Glide.with(itemView)
                .load(item.urlToImage)
                .into(newsImage)

            val pos = adapterPosition
            if (pos != RecyclerView.NO_POSITION) {
                itemView.setOnClickListener {
                    listener?.onItemClick(itemView, item, pos)
                }
            }
        }
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.news_item, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = news[position]
        viewHolder.bind(item)
    }


    override fun getItemCount(): Int = news.size

}