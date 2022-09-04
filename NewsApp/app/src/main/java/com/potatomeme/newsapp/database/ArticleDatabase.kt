package com.potatomeme.newsapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.potatomeme.newsapp.gson.Article
import com.potatomeme.newsapp.repository.ArticleRepository

@Database(entities = [Article::class],exportSchema = false,version = 1)
abstract class ArticleDatabase : RoomDatabase() {
    abstract fun articleRepository() : ArticleRepository
}