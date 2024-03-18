package com.example.starwar.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.starwar.databinding.FragmentNetworkStateLayoutBinding

class ListLoadStateAdapter(   private val retry:()->Unit
) : LoadStateAdapter<ListLoadStateAdapter.LoadStateViewHolder>(){

    inner class LoadStateViewHolder(val binding:FragmentNetworkStateLayoutBinding ): RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.binding.apply {
            if (loadState is LoadState.Loading) {
                progressBar.isVisible=true
                errorMessage.isVisible=false
                btnRetry.isVisible=false
            }else{
                progressBar.isVisible=false
                errorMessage.isVisible=true
                btnRetry.isVisible=true
            }
            btnRetry.setOnClickListener {
                retry.invoke()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState)=
        LoadStateViewHolder(
            FragmentNetworkStateLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false))
}