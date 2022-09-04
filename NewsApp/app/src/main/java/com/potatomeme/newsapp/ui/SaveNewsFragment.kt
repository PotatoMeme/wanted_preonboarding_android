package com.potatomeme.newsapp.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.potatomeme.newsapp.MainActivity
import com.potatomeme.newsapp.R
import com.potatomeme.newsapp.adapter.NewsAdapter
import com.potatomeme.newsapp.api.RetrofitInstance
import com.potatomeme.newsapp.databinding.ActivityMainBinding
import com.potatomeme.newsapp.databinding.FragmentTopNewsBinding
import com.potatomeme.newsapp.gson.Article
import com.potatomeme.newsapp.gson.NewsResponse
import com.potatomeme.newsapp.helper.DbHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SaveNewsFragment : Fragment() {

    private var mBinding: FragmentTopNewsBinding? = null
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
        mBinding = FragmentTopNewsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        Thread(Runnable {
            val adapter = DbHelper.findAllArticle()?.let { NewsAdapter(it) }
            adapter?.setOnItemClickListener(object : NewsAdapter.OnItemClickListener {
                override fun onItemClick(v: View, data: Article, pos: Int) {
                    //Toast.makeText(context,data.toString(),Toast.LENGTH_SHORT).show()
                    mainActivity?.showDetailFragment(data)
                }
            })
            binding.newsList.adapter = adapter
        }).start()
    }


    override fun onDestroy() {
        super.onDestroy()
        mBinding = null
        mainActivity = null
    }


    companion object {
        private const val TAG = "TopNewsFragment"
    }
}