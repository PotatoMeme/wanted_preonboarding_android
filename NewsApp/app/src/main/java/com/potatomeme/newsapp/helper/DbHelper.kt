package com.potatomeme.newsapp.helper

import android.content.Context
import androidx.room.Room
import com.potatomeme.newsapp.database.ArticleDatabase
import com.potatomeme.newsapp.gson.Article
import com.potatomeme.newsapp.repository.ArticleRepository

class DbHelper {
    companion object {
        private var articleDatabase: ArticleDatabase? = null
        private var articleRepository: ArticleRepository? = null

        fun dbSetting(context: Context) {
            articleDatabase = Room.databaseBuilder(
                context,
                ArticleDatabase::class.java, "database-name"
            ).build()
            articleRepository = articleDatabase!!.articleRepository()
        }

        fun findAllArticle() = articleRepository?.getAll()

        fun findByUrl(url: String) = articleRepository?.findByUrl(url)
        fun findByUrlBoolean(url: String) = articleRepository?.findByUrl(url) != null

        fun insertArticle(article: Article) {
            articleRepository?.insert(article)
        }

        fun deletArticle(article: Article) {
            articleRepository?.delete(article)
        }
    }
}