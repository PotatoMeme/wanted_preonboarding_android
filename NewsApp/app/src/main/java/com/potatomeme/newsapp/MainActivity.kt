package com.potatomeme.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.potatomeme.newsapp.adapter.NewsAdapter
import com.potatomeme.newsapp.api.NewsApi
import com.potatomeme.newsapp.api.RetrofitInstance
import com.potatomeme.newsapp.databinding.ActivityMainBinding
import com.potatomeme.newsapp.gson.NewsResponse
import com.potatomeme.newsapp.ui.TopNewsFragment
import com.potatomeme.newsapp.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type

class MainActivity : AppCompatActivity() {

    private var mBinding : ActivityMainBinding? = null
    private val binding get() = mBinding!!




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //  Activity_Main UI setting
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.topAppBar)


        //sendRequest()
        val transaction = supportFragmentManager.beginTransaction()
            .add(R.id.content_container, TopNewsFragment())
        transaction.commit()

        //  bottomNavigation
        binding.bottomNavigation.setOnItemSelectedListener {item ->
            setAppTitle(item.itemId)
            when(item.itemId) {
                R.id.page_topnews -> {
                    Log.d(TAG,"page_topnews selected")
                    true
                }
                R.id.page_category -> {
                    Log.d(TAG,"page_category selected")
                    true
                }
                R.id.page_save -> {
                    Log.d(TAG,"page_save selected")
                    true
                }
                else -> false
            }
        }


    }

    /*
    var result: NewsResponse? = null
    private fun sendRequest() {
        //Log.d(TAG,"${RetrofitInstance.api::class.simpleName}")
        RetrofitInstance.api.getTopNews().enqueue(object : Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                if(response.isSuccessful){
                    result = response.body()
                    Log.d(TAG, "onResponse 성공: " + result?.articles?.get(19));
                    Log.d(TAG, "onResponse 성공: " + result?.totalResults);
                    val adapter = result?.articles?.let { NewsAdapter(it,this@MainActivity) }
                    binding.newsList.adapter = adapter
                }else{
                    Log.d(TAG, "onResponse 실패")
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                Log.d("YMC", "onFailure 에러: " + t.message.toString());
            }
        })
    }
    */


    override fun onResume() {
        super.onResume()
        setAppTitle(binding.bottomNavigation.selectedItemId)
    }

    private fun setAppTitle(Id: Int) {
        when(Id){
            R.id.page_topnews -> {
                binding.topAppBar.title = "NewsApp"
            }
            R.id.page_category -> {
                binding.topAppBar.title = "Category"
            }
            R.id.page_save -> {
                binding.topAppBar.title = "Saved News"
            }
        }
    }


    companion object {
        private const val TAG = "MainActivity"
    }

}