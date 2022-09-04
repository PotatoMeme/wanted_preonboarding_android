package com.potatomeme.newsapp.gson

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Article(
    @PrimaryKey(autoGenerate = true)
    var seq: Int,

    var author: String,
    var content: String,
    var description: String,
    var publishedAt: String,
    //val source: Source,
    var title: String,
    var url: String,
    var urlToImage: String

){
    fun checkNull(){
        if(author == null){
            author = ""
        }
        if(content == null){
            content = ""
        }
        if(description == null){
            description = ""
        }
        if(publishedAt == null){
            publishedAt = ""
        }
        if(title == null){
            title = ""
        }
        if(author == null){
            url = ""
        }
        if(urlToImage == null){
            urlToImage = ""
        }

    }
}