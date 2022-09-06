package com.potatomeme.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import com.potatomeme.newsapp.databinding.ActivityMainBinding
import com.potatomeme.newsapp.gson.Article
import com.potatomeme.newsapp.helper.DbHelper
import com.potatomeme.newsapp.ui.*
import com.potatomeme.newsapp.utils.Constants
import java.util.*

class MainActivity : AppCompatActivity() {

    private var mBinding: ActivityMainBinding? = null
    private val binding get() = mBinding!!

    lateinit var newsDetailFragment: NewsDetailFragment
    lateinit var topNewsFragment: TopNewsFragment
    lateinit var saveNewsFragment: SaveNewsFragment
    lateinit var categoryNewsFragment: CategoryNewsFragment

    var fragState = Stack<Frag>()

    var curentCategory = 0

    enum class Frag {
        TopNews, TopNewsDetail, CategorySelect, CategoryList, CategoryNewsDetail, SavedNews, SavedNewsDetail
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //  DBsetting
        DbHelper.dbSetting(applicationContext)
        Thread(Runnable {
            Log.d(TAG, "size : ${DbHelper.findAllArticle()?.size}")
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
        fragState.add(Frag.TopNews)

        //  bottomNavigation
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
            when (item.itemId) {
                R.id.page_topnews -> {
                    setAppTitle("Top news")
                    Log.d(TAG, "page_topnews selected")

                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.content_container, topNewsFragment)
                        .commit()
                    fragState.clear()
                    fragState.add(Frag.TopNews)
                    true
                }
                R.id.page_category -> {
                    setAppTitle("Category")
                    Log.d(TAG, "page_category selected")

                    if (fragState.peek() == Frag.TopNews) {
                        supportFragmentManager.beginTransaction().remove(topNewsFragment)
                            .commit();
                        supportFragmentManager.popBackStackImmediate()
                    }

                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.content_container, CategoryFragment())
                        .commit()
                    fragState.clear()
                    fragState.add(Frag.CategorySelect)
                    true
                }
                R.id.page_save -> {
                    setAppTitle("Saved News")
                    Log.d(TAG, "page_save selected")
                    saveNewsFragment = SaveNewsFragment()

                    if (fragState.peek() == Frag.TopNews) {
                        supportFragmentManager.beginTransaction().remove(topNewsFragment)
                            .commit();
                        supportFragmentManager.popBackStackImmediate()
                    }

                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.content_container, saveNewsFragment)
                        .commit()
                    fragState.clear()
                    fragState.add(Frag.SavedNews)
                    true
                }
                else -> false
            }
        }
    }

    // DetailFragment show
    fun showDetailFragment(data: Article) {
        newsDetailFragment = NewsDetailFragment(data)

        setAppTitle(data.title)

        when (fragState.peek()) {
            Frag.TopNews -> fragState.add(Frag.TopNewsDetail)
            Frag.CategoryList -> fragState.add(Frag.CategoryNewsDetail)
            Frag.SavedNews -> fragState.add(Frag.SavedNewsDetail)
            else -> {}
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportFragmentManager
            .beginTransaction()
            .add(R.id.content_container, newsDetailFragment)
            .addToBackStack(null)
            .commit()
    }

    // CategoryList show
    fun showCategoryListFragment(i: Int) {
        curentCategory = i
        fragState.add(Frag.CategoryList)
        currentAppTitle()
        categoryNewsFragment = CategoryNewsFragment(i)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportFragmentManager
            .beginTransaction()
            .add(R.id.content_container, categoryNewsFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onResume() {
        super.onResume()
        currentAppTitle()
    }

    // fragment 별
    private fun currentAppTitle() {
        when (fragState.peek()) {
            Frag.TopNews -> setAppTitle("Top news")
            Frag.CategorySelect -> setAppTitle("Category")
            Frag.CategoryList -> setAppTitle(
                "Category - ${
                    Constants.categoryShowList.get(
                        curentCategory
                    )
                }"
            )
            Frag.SavedNews -> setAppTitle("Saved News")
            else -> return
        }
    }

    fun setAppTitle(str: String) {
        binding.topAppBar.title = str
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                Log.d(TAG, "R.id.home click")
                when (fragState.pop()) {
                    Frag.TopNewsDetail-> {
                        supportFragmentManager.beginTransaction().remove(newsDetailFragment)
                            .commit();
                        supportActionBar?.setDisplayHomeAsUpEnabled(false)
                    }
                    Frag.SavedNewsDetail -> {
                        supportFragmentManager.beginTransaction().remove(newsDetailFragment)
                            .commit();
                        supportActionBar?.setDisplayHomeAsUpEnabled(false)
                        saveNewsFragment.reloadData()
                    }
                    Frag.CategoryNewsDetail -> {
                        supportFragmentManager.beginTransaction().remove(newsDetailFragment)
                            .commit();
                    }
                    Frag.CategoryList -> {
                        supportFragmentManager.beginTransaction().remove(categoryNewsFragment)
                            .commit();
                        supportActionBar?.setDisplayHomeAsUpEnabled(false)
                    }
                    else -> return false
                }
                supportFragmentManager.popBackStackImmediate()
                currentAppTitle()
                return true
            }
            else -> return false
        }
    }

    override fun onBackPressed() {
        when (fragState.pop()) {
            Frag.TopNewsDetail, Frag.CategoryList -> {
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
            }
            Frag.SavedNewsDetail -> {
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
                saveNewsFragment.reloadData()
            }
            else -> {}
        }
        currentAppTitle()
        super.onBackPressed()
    }


    companion object {
        private const val TAG = "MainActivity"
    }

}