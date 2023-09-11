package com.example.shop.feature.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shop.services.data.dataclasses.Product
import com.example.shop.base.BaseFragment
import com.example.shop.base.EXTRA_KEY
import com.example.shop.databinding.FragmentHomeBinding
import com.example.shop.feature.detail.product.DetailActivity
import com.example.shop.feature.home.banner.BannerAdapter
import com.example.shop.feature.common.ProductListAdapter
import com.example.shop.feature.common.VIEW_TYPE_SMALL
import com.example.shop.feature.list.ListActivity
import com.example.shop.services.data.dataclasses.SORT_LATEST
import com.example.shop.services.data.dataclasses.SORT_POPULAR
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class HomeFragment : BaseFragment(), ProductListAdapter.OnProductItemClickListener {
    private val viewModel: HomeViewModel by viewModel()
    private val latestAdapter: ProductListAdapter by inject { parametersOf(VIEW_TYPE_SMALL) }
    private val popularAdapter: ProductListAdapter by inject { parametersOf(VIEW_TYPE_SMALL) }
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLatestAdapter()
        initPopularAdapter()
        latestAdapter.productClickListener = this
        popularAdapter.productClickListener = this

        lifecycleScope.launch {
            viewModel.uiState.onEach {
                if (it.errorResponse == null) {
                    if (it.progressBar)
                        setProgressBarIndicator(true)
                    else
                        setProgressBarIndicator(false)
                    if (!it.banners.isNullOrEmpty()) {
                        //Banner
                        val bannerAdapter = BannerAdapter(this@HomeFragment, it.banners)
                        binding.bannerSlider.adapter = bannerAdapter
                        binding.dotsIndicator.attachTo(binding.bannerSlider)
                    }
                    if (!it.products.isNullOrEmpty()) {
                        //Product
                        latestAdapter.products = it.products as ArrayList<Product>
                        popularAdapter.products = it.products as ArrayList<Product>
                    }

                } else {
                    Toast.makeText(
                        requireContext(),
                        "Error: ${it.errorResponse.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }.collect()
        }

        binding.allLatest.setOnClickListener {
            startActivity(Intent(requireContext(), ListActivity::class.java).apply {
                putExtra(EXTRA_KEY, SORT_LATEST)
            })
        }
        binding.allPopular.setOnClickListener {
            startActivity(Intent(requireContext(), ListActivity::class.java).apply {
                putExtra(EXTRA_KEY, SORT_POPULAR)
            })
        }
    }

    private fun initLatestAdapter() {
        binding.latestRv.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        binding.latestRv.adapter = latestAdapter
    }

    private fun initPopularAdapter() {
        binding.popularRv.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        binding.popularRv.adapter = popularAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onProductClick(product: Product) {
        startActivity(Intent(requireContext(), DetailActivity::class.java).apply {
            putExtra(EXTRA_KEY, product)
        })
    }
}