package com.example.shop.feature.list

import android.content.DialogInterface
import android.content.DialogInterface.OnClickListener
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.shop.R
import com.example.shop.base.BaseActivity
import com.example.shop.base.EXTRA_KEY
import com.example.shop.databinding.ActivityDetailBinding
import com.example.shop.databinding.ActivityListBinding
import com.example.shop.feature.common.ProductListAdapter
import com.example.shop.feature.common.VIEW_TYPE_LARGE
import com.example.shop.feature.common.VIEW_TYPE_SMALL
import com.example.shop.services.data.dataclasses.Product
import com.example.shop.services.data.dataclasses.SORT_LATEST
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class ListActivity : BaseActivity() {
    private val viewModel: ListViewModel by inject { parametersOf(intent.extras!!.getInt(EXTRA_KEY)) }
    private val listAdapter: ProductListAdapter by inject { parametersOf(VIEW_TYPE_SMALL) }
    private lateinit var binding: ActivityListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        observe()
        initViews()
    }

    private fun initViews() {
        binding.listToolbar.backBtn.setOnClickListener {
            finish()
        }
        val gridLayoutManager = GridLayoutManager(this, 2)
        binding.productListRv.layoutManager = gridLayoutManager
        binding.productListRv.adapter = listAdapter

        binding.viewTypeChanger.setOnClickListener {
            if (listAdapter.viewType == VIEW_TYPE_SMALL) {
                listAdapter.viewType = VIEW_TYPE_LARGE
                gridLayoutManager.spanCount = 1
                binding.viewTypeChanger.setImageResource(R.drawable.outline_list_24)
            } else {
                listAdapter.viewType = VIEW_TYPE_SMALL
                gridLayoutManager.spanCount = 2
                binding.viewTypeChanger.setImageResource(R.drawable.baseline_grid_view_24)
            }
        }

        binding.sortTypeChanger.setOnClickListener {
            val dialog = MaterialAlertDialogBuilder(this)
                .setSingleChoiceItems(
                    R.array.sortTitlesArray, viewModel.sort
                ) { dialog, selectedSortIndex ->
                    dialog.dismiss()
                    viewModel.onSortTypeChangerSelectByUser(selectedSortIndex)
                }
                .setTitle(R.string.sort_by)
            dialog.show()
        }
    }

    private fun observe() {
        viewModel.productsLiveData.observe(this) {
            listAdapter.products = it as ArrayList<Product>
        }

        viewModel.progressBarLiveData.observe(this) {
            setProgressBarIndicator(it)
        }

        viewModel.selectedSortsLiveData.observe(this) {
            binding.sortType.text = getString(it)
        }
    }
}