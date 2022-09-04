package com.potatomeme.newsapp.ui

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.potatomeme.newsapp.MainActivity
import com.potatomeme.newsapp.R
import com.potatomeme.newsapp.adapter.NewsAdapter
import com.potatomeme.newsapp.api.RetrofitInstance
import com.potatomeme.newsapp.databinding.ActivityMainBinding
import com.potatomeme.newsapp.databinding.FragmentNewsDetailBinding
import com.potatomeme.newsapp.databinding.FragmentTopNewsBinding
import com.potatomeme.newsapp.gson.Article
import com.potatomeme.newsapp.gson.NewsResponse
import com.potatomeme.newsapp.helper.AppHelper
import com.potatomeme.newsapp.helper.DbHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsDetailFragment(var data: Article) : Fragment() {

    private var mBinding: FragmentNewsDetailBinding? = null
    private val binding get() = mBinding!!
    var mainActivity: MainActivity? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentNewsDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        setUi(data)
    }

    fun setUi(data: Article) {

        data.checkNull()

        mainActivity?.setAppTitle(data.title)
        binding.newsTitle.text = data.title

        val text ="${data.author} ${AppHelper.intervalBetweenDate(data.publishedAt)}"
        val spannable = SpannableStringBuilder(text)
        spannable.setSpan(
            ForegroundColorSpan(Color.GRAY),
            data.author.length, // start
            text.length, // end
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        )
        binding.newsReporter.text = spannable


        binding.newsContent.text = data.content
        context?.let {
            Glide.with(it)
                .load(data.urlToImage)
                .into(binding.newsImage)
        }
        Thread(Runnable {
            binding.newsStarToggle.isActivated = DbHelper.findByUrlBoolean(data.url)
        }).start()
        binding.newsStarToggle.setOnClickListener {
            Thread(Runnable {
                if (it.isActivated) {
                    Log.d(TAG,"insert: ${data.toString()}")
                    DbHelper.insertArticle(data)
                } else {
                    DbHelper.findByUrl(data.url)?.let { it1 -> DbHelper.deletArticle(it1) }
                }
            }).start()

            it.isActivated = !it.isActivated
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding = null
        //mainActivity = null
    }


    companion object {
        private const val TAG = "NewsDetailFragment"
    }
}