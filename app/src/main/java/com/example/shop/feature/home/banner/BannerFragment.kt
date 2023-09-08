package com.example.shop.feature.home.banner

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.shop.base.EXTRA_KEY
import com.example.shop.databinding.FragmentBannerBinding
import com.example.shop.services.data.dataclasses.Banner
import com.example.shop.services.service.ImageLoadingService
import org.koin.android.ext.android.inject
import java.lang.IllegalStateException

class BannerFragment : Fragment() {
    private val imageLoadingService: ImageLoadingService by inject()
    private var _binding: FragmentBannerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBannerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val banner =
            requireArguments().getParcelable<Banner>(EXTRA_KEY) ?: throw IllegalStateException(
                "Banner cannot be null"
            )
        imageLoadingService.loadImage(requireContext(), banner.image, binding.banner)
    }

    companion object {
        fun newInstance(banner: Banner): BannerFragment {
            return BannerFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(EXTRA_KEY, banner)
                }
            }
        }
    }
}