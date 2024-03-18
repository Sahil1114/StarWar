package com.example.starwar.data.network

import com.example.starwar.common_utils.Constants
import com.example.starwar.data.model.PeopleList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("people")
    suspend fun getPeopleList(
        @Query("page") page:Int,
        @Query("format") format:String="json"
    ):PeopleList
}