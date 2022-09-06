package com.potatomeme.newsapp.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.potatomeme.newsapp.MainActivity
import com.potatomeme.newsapp.adapter.NewsAdapter
import com.potatomeme.newsapp.databinding.FragmentSaveNewsListBinding
import com.potatomeme.newsapp.gson.Article
import com.potatomeme.newsapp.helper.DbHelper

class SaveNewsFragment : Fragment() {

    private var mBinding: FragmentSaveNewsListBinding? = null
    private val binding get() = mBinding!!

    private var mainActivity: MainActivity? = null
    private var adapter : NewsAdapter? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentSaveNewsListBinding.inflate(inflater, container, false)

        Thread {
            adapter = DbHelper.findAllArticle()?.let { NewsAdapter(it) }
            adapter?.setOnItemClickListener(object : NewsAdapter.OnItemClickListener {
                override fun onItemClick(v: View, data: Article, pos: Int) {
                    mainActivity?.showDetailFragment(data)
                }
            })
            binding.newsList.adapter = adapter
        }.start()

        return binding.root
    }

    fun reloadData(){
        Thread {
            DbHelper.findAllArticle()?.let { adapter?.setNewsList(it) }
            binding.newsList.adapter = adapter
        }.start()
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