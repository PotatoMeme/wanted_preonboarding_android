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

    fun setNewsList(news: List<Article>) {
        this.news = news
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val newsImage: ImageView
        private val newsTitle: TextView
        private val newsReporter: TextView
        private val newsTime: TextView

        init {
            newsImage = view.findViewById(R.id.news_image)
            newsTitle = view.findViewById(R.id.news_title)
            newsReporter = view.findViewById(R.id.news_reporter)
            newsTime = view.findViewById(R.id.news_time)
        }

        fun bind(data: Article) {
            newsTitle.text = data.title
            newsTime.text = AppHelper.intervalBetweenDate(data.publishedAt)
            newsReporter.text = data.author

            Glide.with(itemView)
                .load(data.urlToImage)
                .into(newsImage)

            val pos = adapterPosition
            if (pos != RecyclerView.NO_POSITION) {
                itemView.setOnClickListener {
                    listener?.onItemClick(itemView, data, pos)
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