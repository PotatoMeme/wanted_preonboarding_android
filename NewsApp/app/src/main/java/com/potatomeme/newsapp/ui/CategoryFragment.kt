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
import com.potatomeme.newsapp.databinding.FragmentCategoryBinding
import com.potatomeme.newsapp.databinding.FragmentTopNewsBinding
import com.potatomeme.newsapp.gson.Article
import com.potatomeme.newsapp.gson.NewsResponse
import com.potatomeme.newsapp.helper.DbHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryFragment : Fragment() {

    private var mBinding: FragmentCategoryBinding? = null
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
        mBinding = FragmentCategoryBinding.inflate(inflater, container, false)

        binding.categoryBusiness.setOnClickListener { mainActivity?.showCategoryListFragment(0) }
        binding.categoryEntertainment.setOnClickListener { mainActivity?.showCategoryListFragment(1) }
        binding.categoryGeneral.setOnClickListener { mainActivity?.showCategoryListFragment(2) }
        binding.categoryHealth.setOnClickListener { mainActivity?.showCategoryListFragment(3) }
        binding.categoryScience.setOnClickListener { mainActivity?.showCategoryListFragment(4) }
        binding.categorySports.setOnClickListener { mainActivity?.showCategoryListFragment(5) }
        binding.categoryTechnology.setOnClickListener { mainActivity?.showCategoryListFragment(6) }


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
        mainActivity = null
    }

    companion object {
        private const val TAG = "CategoryFragment"
    }
}