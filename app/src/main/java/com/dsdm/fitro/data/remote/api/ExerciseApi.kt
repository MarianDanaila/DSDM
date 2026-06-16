package com.dsdm.fitro.data.remote.api

import com.dsdm.fitro.data.remote.model.ExerciseListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ExerciseApi {

    @GET("api/v2/exerciseinfo/?format=json")
    suspend fun getExercises(
        @Query("language") language: Int = 2,
        @Query("limit") limit: Int = 20
    ): ExerciseListResponse

    @GET("api/v2/exercisecategory/?format=json")
    suspend fun getCategories(): ExerciseListResponse
}
