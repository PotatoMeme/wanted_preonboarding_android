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
    var adapter : NewsAdapter? = null

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

        Thread(Runnable {
            adapter = DbHelper.findAllArticle()?.let { NewsAdapter(it) }
            adapter?.setOnItemClickListener(object : NewsAdapter.OnItemClickListener {
                override fun onItemClick(v: View, data: Article, pos: Int) {
                    mainActivity?.showDetailFragment(data)
                }
            })
            binding.newsList.adapter = adapter
        }).start()

        return binding.root
    }

    //  detailview 나가고 확인용
    fun reloadData(){
        Thread(Runnable {
            DbHelper.findAllArticle()?.let { adapter?.setNewsList(it) }
            binding.newsList.adapter = adapter
        }).start()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
        mainActivity = null
    }


    companion object {
        private const val TAG = "SaveNewsFragment"
    }
}