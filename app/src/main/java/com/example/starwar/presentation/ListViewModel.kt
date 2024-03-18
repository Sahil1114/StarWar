package com.example.starwar.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.cachedIn
import com.example.starwar.common_utils.FilterStatus
import com.example.starwar.data.repository.ListRepositoryImpl
import com.example.starwar.domain.repository.ListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListViewModel @OptIn(ExperimentalPagingApi::class)
@Inject constructor(
    private val repository: ListRepositoryImpl
):ViewModel() {

    @OptIn(ExperimentalPagingApi::class)
     fun getList(status: FilterStatus)= repository.getPagingData(status).cachedIn(viewModelScope)

}