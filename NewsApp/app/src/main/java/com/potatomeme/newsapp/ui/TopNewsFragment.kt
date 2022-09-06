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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TopNewsFragment : Fragment() {

    private var mBinding: FragmentTopNewsBinding? = null
    private val binding get() = mBinding!!
    var mainActivity: MainActivity? = null
    var result: NewsResponse? = null

    var sendThread: Thread? = null

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
        sendThread = Thread(Runnable {
            container?.let { sendRequest(it.context) }
        })
        return binding.root
    }

    override fun onResume() {
        sendThread?.start()
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        //mBinding = null
        mainActivity = null
    }

    private fun sendRequest(context: Context) {
        RetrofitInstance.api.getTopNews().enqueue(object : Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                if (response.isSuccessful) {
                    result = response.body()
                    Log.d(TAG, "onResponse 성공: " + result?.articles?.get(19));
                    Log.d(TAG, "onResponse 성공: " + result?.totalResults);
                    val adapter: NewsAdapter? = result?.articles?.let { NewsAdapter(it) }
                    adapter?.setOnItemClickListener(object : NewsAdapter.OnItemClickListener {
                        override fun onItemClick(v: View, data: Article, pos: Int) {
                            mainActivity?.showDetailFragment(data)
                        }
                    })
                    binding.newsList.adapter = adapter
                } else {
                    Log.d(TAG, "onResponse 실패")
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                Log.d(TAG, "onFailure 에러: " + t.message.toString());
            }
        })
    }

    companion object {
        private const val TAG = "TopNewsFragment"
    }
}