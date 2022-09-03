package com.potatomeme.newsapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.potatomeme.newsapp.R
import com.potatomeme.newsapp.gson.Article


class NewsAdapter(private val news: List<Article>,private val context: Context) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val newsImage : ImageView
        val newsTitle : TextView
        val newsReporter : TextView
        val newsTime : TextView
        init {
            newsImage = view.findViewById(R.id.news_image)
            newsTitle = view.findViewById(R.id.news_title)
            newsReporter = view.findViewById(R.id.news_reporter)
            newsTime = view.findViewById(R.id.news_time)
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
        viewHolder.newsTitle.text = item.title
        viewHolder.newsTime.text = item.publishedAt
        viewHolder.newsReporter.text = item.author
        Glide.with(context)
            .load(item.urlToImage)
            .into(viewHolder.newsImage)
    }



    override fun getItemCount(): Int  = news.size

}