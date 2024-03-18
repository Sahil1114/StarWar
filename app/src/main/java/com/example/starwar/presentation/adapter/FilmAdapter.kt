package com.example.starwar.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.starwar.databinding.FilmLayoutBinding

class FilmAdapter :RecyclerView.Adapter<FilmAdapter.FilmAdapterViewHolder>(){

    inner class FilmAdapterViewHolder(private val binding:FilmLayoutBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(string: String){
            binding.tvTitle.text=string
        }
    }

    private val diffUtilCallBack=object:DiffUtil.ItemCallback<String>(){
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem==newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem==newItem
        }
    }

    private var differ=AsyncListDiffer(this,diffUtilCallBack)

    var list:List<String>
        get()=differ.currentList
        set(value) =differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmAdapterViewHolder {
        return FilmAdapterViewHolder(
            FilmLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: FilmAdapterViewHolder, position: Int) {
        val filmUrl=list[position]
        val regex = Regex("/(\\d+)/$")
        val matchResult = regex.find(filmUrl)?.groupValues?.getOrNull(1)?.toInt()?:0
        val film="Film $matchResult"
        holder.bind(film)
    }

}