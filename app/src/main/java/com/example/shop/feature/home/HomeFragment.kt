package com.example.shop.feature.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.shop.base.BaseFragment
import com.example.shop.databinding.FragmentHomeBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class HomeFragment : BaseFragment() {
    private val viewModel: HomeViewModel by viewModel()
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
        lifecycleScope.launch {
            viewModel.uiState.onEach {
                if (it.errorResponse == null) {
                    if (it.progressBar)
                        setProgressBarIndicator(true)
                    else
                        setProgressBarIndicator(false)
                    val bannerAdapter = BannerAdapter(this@HomeFragment, it.banners)
                    binding.bannerSlider.adapter = bannerAdapter
                    binding.dotsIndicator.attachTo(binding.bannerSlider)
                }

            }.collect()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}