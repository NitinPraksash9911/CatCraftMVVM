package com.example.catcraft.ui.listframent

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle.State
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.catcraft.arch.BaseFragment
import com.example.catcraft.arch.ViewState.Error
import com.example.catcraft.arch.ViewState.Loading
import com.example.catcraft.arch.ViewState.Success
import com.example.catcraft.databinding.FragmentBreedListBinding
import com.example.catcraft.ui.listframent.adapter.CatBreedAdapter
import com.example.catcraft.utils.hide
import com.example.catcraft.utils.launchAndCollectIn
import com.example.catcraft.utils.show
import com.example.catcraft.utils.snack
import com.example.catcraft.viewmodel.CatBreedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

const val TAG = "BreedListFragment"

@InternalCoroutinesApi
@AndroidEntryPoint
class BreedListFragment : BaseFragment<FragmentBreedListBinding>
    (FragmentBreedListBinding::inflate) {

    private val viewModel: CatBreedViewModel by viewModels()

    private lateinit var breedAdapter: CatBreedAdapter

    override fun initViews(savedInstanceState: Bundle?) {

        breedAdapter = CatBreedAdapter(::onListItemClick)

        binding.breedListRv.adapter = breedAdapter

        dataObserver()

        binding.pullToRefresh.setOnRefreshListener {
            viewModel.fetchCatBreeds()
            binding.pullToRefresh.isRefreshing = false
        }
    }


    private fun dataObserver() {


        viewModel.breedListData.launchAndCollectIn(this, State.STARTED) { breedEvent ->
            when (breedEvent) {
                is Loading -> {
                    binding.progressCircular.visibility = View.VISIBLE
                    binding.breedListRv.hide()
                    binding.errorTv.hide()
                    Log.d(TAG, "Loading")
                }
                is Success -> {
                    binding.progressCircular.visibility = View.GONE
                    binding.errorTv.hide()
                    binding.breedListRv.show()
                    breedAdapter.submitList(breedEvent.item)
                    Log.d(TAG, breedEvent.item.toString())
                }
                is Error -> {
                    binding.progressCircular.visibility = View.GONE
                    binding.breedListRv.hide()
                    breedEvent.errorMsg.snack(Color.RED, binding.root)
                    binding.errorTv.show()
                    Log.d(TAG, breedEvent.errorMsg)
                }
            }

        }
    }



    private fun onListItemClick(position: Int) {
        val currentCatBreed = breedAdapter.currentList[position]
        val directions = BreedListFragmentDirections.actionBreedListFragmentToBreedDetailFragment(currentCatBreed)
        findNavController().navigate(directions)
    }

}