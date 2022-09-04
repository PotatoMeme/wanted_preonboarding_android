package com.potatomeme.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.room.Room
import com.potatomeme.newsapp.adapter.NewsAdapter
import com.potatomeme.newsapp.api.NewsApi
import com.potatomeme.newsapp.api.RetrofitInstance
import com.potatomeme.newsapp.database.ArticleDatabase
import com.potatomeme.newsapp.databinding.ActivityMainBinding
import com.potatomeme.newsapp.gson.Article
import com.potatomeme.newsapp.gson.NewsResponse
import com.potatomeme.newsapp.helper.DbHelper
import com.potatomeme.newsapp.ui.NewsDetailFragment
import com.potatomeme.newsapp.ui.SaveNewsFragment
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

    lateinit var newsDetailFragment : NewsDetailFragment
    lateinit var topNewsFragment: TopNewsFragment
    lateinit var saveNewsFragment: SaveNewsFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //  DBsetting
        DbHelper.dbSetting(applicationContext)
        Thread(Runnable {
            Log.d(TAG,"size : ${DbHelper.findAllArticle()?.size}")
        }).start()

        //  Activity_Main UI setting
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.topAppBar)

        // default fragment
        topNewsFragment = TopNewsFragment()
        supportFragmentManager
            .beginTransaction()
            .add(R.id.content_container, topNewsFragment)
            .commit()

        //  bottomNavigation
        binding.bottomNavigation.setOnItemSelectedListener {item ->
            when(item.itemId) {
                R.id.page_topnews -> {
                    setAppTitle("Top news")
                    Log.d(TAG,"page_topnews selected")
                    supportActionBar?.setDisplayHomeAsUpEnabled(false)
                    topNewsFragment
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.content_container, topNewsFragment)
                        .commit()
                    true
                }
                R.id.page_category -> {
                    setAppTitle("Category")
                    Log.d(TAG,"page_category selected")
                    true
                }
                R.id.page_save -> {
                    setAppTitle("Saved News")
                    Log.d(TAG,"page_save selected")
                    saveNewsFragment = SaveNewsFragment()
                    supportActionBar?.setDisplayHomeAsUpEnabled(false)
                    topNewsFragment
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.content_container, saveNewsFragment)
                        .commit()
                    true
                }
                else -> false
            }
        }
    }

    // DetailFragment show
    fun showDetailFragment(data :Article){
        newsDetailFragment = NewsDetailFragment(data)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportFragmentManager
            .beginTransaction()
            .add(R.id.content_container,newsDetailFragment )
            .commit()
    }

    override fun onResume() {
        super.onResume()
        currentAppTitle()
    }

    private fun currentAppTitle() {
        when(binding.bottomNavigation.selectedItemId){
            R.id.page_topnews -> setAppTitle("Top news")
            R.id.page_category -> setAppTitle("Category")
            R.id.page_save -> setAppTitle("Save")
        }
    }

    fun setAppTitle(str: String) {
        binding.topAppBar.title = str
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                Log.d(TAG,"R.id.home click")
                when(binding.bottomNavigation.selectedItemId){
                    R.id.page_topnews,R.id.page_save -> {
                        supportFragmentManager.beginTransaction().remove(newsDetailFragment).commit();
                    }
                    R.id.page_category -> {
                    }
                }
                supportFragmentManager.beginTransaction().remove(newsDetailFragment).commit();
                supportFragmentManager.popBackStackImmediate()
                currentAppTitle()
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
                return true
            }
            else -> return  false
        }
    }

    override fun onBackPressed() {
        when(binding.bottomNavigation.selectedItemId){
            R.id.page_topnews,R.id.page_save -> {
                supportFragmentManager.beginTransaction().remove(newsDetailFragment).commit();
            }
            R.id.page_category -> {
            }
        }
        supportFragmentManager.popBackStackImmediate()
        currentAppTitle()
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        //super.onBackPressed()
    }
    companion object {
        private const val TAG = "MainActivity"
    }

}