package com.example.starwar.common_utils

import com.example.starwar.domain.model.PeopleEntity

interface OnItemClickListener {
    fun onItemClicked(data:PeopleEntity)
}

interface FilterListener {
    fun onFilterSelected(selectedFilter: FilterStatus)
}