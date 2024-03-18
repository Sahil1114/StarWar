package com.example.starwar.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.starwar.R
import com.example.starwar.common_utils.FilterListener
import com.example.starwar.common_utils.FilterStatus
import com.example.starwar.databinding.BottomSheetFragmentBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FilterBottomSheet:BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetFragmentBinding
    private var filterListener: FilterListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=BottomSheetFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textViewAll.setOnClickListener {
            passFilterSelection(FilterStatus.ALL)
            dismiss()
        }

        binding.textViewMale.setOnClickListener {
            passFilterSelection(FilterStatus.MALE)
            dismiss()
        }

        binding.textViewFemale.setOnClickListener {
            passFilterSelection(FilterStatus.FEMALE)
            dismiss()
        }

        binding.textViewNotMaleOrFemale.setOnClickListener {
            passFilterSelection(FilterStatus.NA)
            dismiss()
        }

        binding.textViewCreated.setOnClickListener {
            passFilterSelection(FilterStatus.CREATED)
            dismiss()
        }

        binding.textViewEdited.setOnClickListener {
            passFilterSelection(FilterStatus.EDITED)
            dismiss()
        }
    }

    fun setFilterListener(listener: FilterListener) {
        this.filterListener = listener
    }

    private fun passFilterSelection(selectedFilter: FilterStatus) {
        filterListener?.onFilterSelected(selectedFilter)
    }
}