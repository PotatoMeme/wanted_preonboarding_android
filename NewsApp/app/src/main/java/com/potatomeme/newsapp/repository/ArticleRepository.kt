package com.potatomeme.newsapp.repository

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.potatomeme.newsapp.gson.Article

@Dao
interface ArticleRepository {
    @Query("SELECT * FROM article")
    fun getAll(): List<Article>

    @Query("SELECT * FROM article WHERE seq = :seq")
    fun loadBySeq(seq: Int): Article

    @Query("SELECT * FROM article WHERE url = :url")
    fun findByUrl(url: String): Article

    @Insert
    fun insert(article: Article)

    @Delete
    fun delete(article: Article)
}