package com.example.starwar.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.starwar.R
import com.example.starwar.databinding.FragmentListBinding
import com.example.starwar.databinding.FragmentPeopleDetailBinding
import com.example.starwar.domain.model.PeopleEntity
import com.example.starwar.presentation.adapter.FilmAdapter

class PeopleDetailFragment : Fragment() {

    private lateinit var filmAdapter: FilmAdapter
    private lateinit var binding:FragmentPeopleDetailBinding
    private val args by navArgs<PeopleDetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentPeopleDetailBinding.inflate(inflater,container,false)
        val data=args.peopleData!!
        bindData(data)

        return binding.root
    }

    private fun bindData(data:PeopleEntity){
        binding.apply {
            tvTitle.text=data.name
            tvHeight.text=data.height
            tvBirth.text=data.birth_year
            tvGender.text=data.gender
            setUpRecyclerView()
            filmAdapter.list=data.films
        }
    }

    private fun setUpRecyclerView()=binding.rvFilms.apply {
        filmAdapter= FilmAdapter()
        adapter=filmAdapter
        layoutManager=GridLayoutManager(requireContext(),2, LinearLayoutManager.VERTICAL,false)
    }

}