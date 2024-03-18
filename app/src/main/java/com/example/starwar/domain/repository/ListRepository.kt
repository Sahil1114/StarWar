package com.example.starwar.domain.repository

import androidx.paging.PagingData
import com.example.starwar.common_utils.FilterStatus
import com.example.starwar.domain.model.PeopleEntity
import kotlinx.coroutines.flow.Flow

interface ListRepository {

     fun getPagingData(status: FilterStatus): Flow<PagingData<PeopleEntity>>
}