package com.example.locationapp.networking

import com.example.locationapp.model.CurrentLocationResponse
import retrofit2.Call
import retrofit2.http.*


interface ApiService {

    // Get current location data
    @GET("feeds/last.json")
    fun getCurrentLocation(
        @Query("api_key") key: String = ApiConfig.API_KEY
    ): Call<CurrentLocationResponse>
}