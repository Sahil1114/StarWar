package com.example.starwar.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.starwar.common_utils.FilterListener
import com.example.starwar.common_utils.FilterStatus
import com.example.starwar.common_utils.OnItemClickListener
import com.example.starwar.databinding.FragmentListBinding
import com.example.starwar.domain.model.PeopleEntity
import com.example.starwar.presentation.adapter.ListAdapter
import com.example.starwar.presentation.adapter.ListLoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class ListFragment : Fragment() , OnItemClickListener,FilterListener {

    private lateinit var binding:FragmentListBinding
    private lateinit var listAdapter:ListAdapter
    private val viewModel:ListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentListBinding.inflate(inflater, container, false)
        setUpRecyclerView()
        collectList(FilterStatus.ALL)
        binding.fbFilter.setOnClickListener {
            showFilterBottomSheet()
        }
        return binding.root
    }

    private fun setUpRecyclerView(){
        Log.d("recyclerView","Called")
        listAdapter= ListAdapter(this)
        binding.apply {
            recyclerView.apply {
                adapter=listAdapter
                layoutManager= GridLayoutManager(requireContext(),2, LinearLayoutManager.VERTICAL,false)
                this.adapter=listAdapter.withLoadStateHeaderAndFooter(
                    header = ListLoadStateAdapter{ listAdapter.retry() },
                    footer = ListLoadStateAdapter{ listAdapter.retry() }
                )
            }
            listAdapter.addLoadStateListener {loadstate->
                progressBar.isVisible=loadstate.source.refresh is LoadState.Loading
                recyclerView.isVisible=loadstate.source.refresh is LoadState.NotLoading
                btnRetry.isVisible=loadstate.source.refresh is LoadState.Error
                errorMessage.isVisible=loadstate.source.refresh is LoadState.Error
                btnRetry.setOnClickListener {
                    listAdapter.retry()
                }
            }
        }
    }

    override fun onItemClicked(data: PeopleEntity) {
        val action = ListFragmentDirections.actionListFragmentToPeopleDetailFragment(data)
        findNavController().navigate(action)

    }

    private fun collectList(status: FilterStatus){
        lifecycleScope.launchWhenCreated {
            viewModel.getList(status).collectLatest {
                listAdapter.submitData(it)
            }
        }
    }

    private fun showFilterBottomSheet() {
        val filterBottomSheetFragment = FilterBottomSheet()
        filterBottomSheetFragment.setFilterListener(this)
        filterBottomSheetFragment.show(parentFragmentManager, filterBottomSheetFragment.tag)
    }


    override fun onFilterSelected(selectedFilter: FilterStatus) {
        listAdapter.refresh()
        collectList(selectedFilter)
//
    }
}