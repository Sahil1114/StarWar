package com.example.starwar.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.starwar.common_utils.OnItemClickListener
import com.example.starwar.databinding.ListLayoutBinding
import com.example.starwar.domain.model.PeopleEntity

class ListAdapter  (  private val listener: OnItemClickListener
) : PagingDataAdapter<PeopleEntity, ListAdapter.ViewHolder>(DiffCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)
        currentItem?.let {
            holder.bind(it)
        }
    }


    inner class ViewHolder(private val binding: ListLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                root.setOnClickListener {
                    val position = bindingAdapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val data = getItem(position)
                        if (data != null) {
                            listener.onItemClicked(data)
                        }
                    }
                }
            }
        }

        fun bind(data: PeopleEntity) {
            binding.apply {
                tvTitle.text=data.name
                tvGender.text=data.gender.toString()
            }
        }

    }

    object DiffCallBack : DiffUtil.ItemCallback<PeopleEntity>() {
        override fun areItemsTheSame(oldItem: PeopleEntity, newItem: PeopleEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PeopleEntity, newItem: PeopleEntity): Boolean {
            return oldItem == newItem
        }
    }

}