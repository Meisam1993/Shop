package com.example.shop.feature.cart

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shop.base.BaseCompletableObserver
import com.example.shop.base.BaseFragment
import com.example.shop.base.BaseSingleObserver
import com.example.shop.base.EXTRA_KEY
import com.example.shop.databinding.FragmentCartBinding
import com.example.shop.feature.detail.product.DetailActivity
import com.example.shop.services.data.dataclasses.cart.CartItem
import com.example.shop.services.service.ImageLoadingService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class CartFragment : BaseFragment(), CartItemAdapter.CartItemViewCallBack {
    private val viewModel: CartViewModel by viewModel()
    private var cartItemAdapter: CartItemAdapter? = null
    private val imageLoadingService: ImageLoadingService by inject()
    private var _binding: FragmentCartBinding? = null
    private val compositeDisposable = CompositeDisposable()
    private val binding get() = _binding!!

    override fun onStart() {
        super.onStart()
        viewModel.refresh()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.progressBarLiveData.observe(viewLifecycleOwner) {
            setProgressBarIndicator(it)
        }

        viewModel.cartItemsListLiveData.observe(viewLifecycleOwner) {
            binding.cartListRv.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            cartItemAdapter = CartItemAdapter(
                it as MutableList<CartItem>,
                imageLoadingService,
                requireContext(),
                this
            )
            binding.cartListRv.adapter = cartItemAdapter
        }

        viewModel.purchaseDetailsLiveData.observe(viewLifecycleOwner) {
            cartItemAdapter?.let { adapter ->
                adapter.purchaseDetails = it
                adapter.notifyItemChanged(adapter.cartItemList.size)
            }
        }

        viewModel.emptyStateLiveData.observe(viewLifecycleOwner) {

        }
    }

    override fun onProductImageClick(cartItem: CartItem) {
        startActivity(Intent(requireActivity(), DetailActivity::class.java).apply {
            putExtra(EXTRA_KEY, cartItem.product)
        })
    }

    override fun onProductTitleClick(cartItem: CartItem) {
        startActivity(Intent(requireActivity(), DetailActivity::class.java).apply {
            putExtra(EXTRA_KEY, cartItem.product)
        })
    }

    override fun onIncreaserCartItemButtonClick(cartItem: CartItem) {
        viewModel.increaseCartItemCount(cartItem)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : BaseCompletableObserver(compositeDisposable) {
                override fun onComplete() {
                    cartItemAdapter?.changeCartItemCount(cartItem)
                }

            })
    }

    override fun onDecreaseCartItemButtonClick(cartItem: CartItem) {
        viewModel.decreaseCartItemCount(cartItem)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : BaseCompletableObserver(compositeDisposable) {
                override fun onComplete() {
                    cartItemAdapter?.changeCartItemCount(cartItem)

                }

            })
    }

    override fun onRemoveCartItemButtonClick(cartItem: CartItem) {
        viewModel.removeCartItem(cartItem)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : BaseCompletableObserver(compositeDisposable) {
                override fun onComplete() {
                    cartItemAdapter?.removeCartItem(cartItem)
                }

            })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        compositeDisposable.clear()
    }
}