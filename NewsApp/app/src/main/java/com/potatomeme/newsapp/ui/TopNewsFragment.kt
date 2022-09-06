package com.potatomeme.newsapp.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.potatomeme.newsapp.MainActivity
import com.potatomeme.newsapp.adapter.NewsAdapter
import com.potatomeme.newsapp.api.RetrofitInstance
import com.potatomeme.newsapp.databinding.FragmentNewsListBinding
import com.potatomeme.newsapp.gson.Article
import com.potatomeme.newsapp.gson.NewsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TopNewsFragment : Fragment() {

    private var mBinding: FragmentNewsListBinding? = null
    private val binding get() = mBinding!!

    private var mainActivity: MainActivity? = null
    private var result: NewsResponse? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentNewsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        binding.swipe.setOnRefreshListener {
            Thread {
                mainActivity?.let { sendRequest() }
            }.start()
            binding.swipe.isRefreshing = false
        }
        super.onResume()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //mBinding = null
        mainActivity = null
    }


    // retrofit 으로 데이터 수신
    private fun sendRequest() {
        RetrofitInstance.api.getTopNews().enqueue(object : Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                if (response.isSuccessful) {
                    result = response.body()
                    Log.d(TAG, "onResponse 성공: " + result?.totalResults)
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
                Log.d(TAG, "onFailure 에러: " + t.message.toString())
            }
        })
    }

    companion object {
        private const val TAG = "TopNewsFragment"
    }
}