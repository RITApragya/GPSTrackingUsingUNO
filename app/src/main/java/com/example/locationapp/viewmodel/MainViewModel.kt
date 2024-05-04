package com.example.locationapp.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.locationapp.model.CurrentLocationResponse
import com.example.locationapp.networking.ApiConfig
import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response

class MainViewModel() : ViewModel() {

    private val _LocationData = MutableLiveData<CurrentLocationResponse>()
    val locationData: LiveData<CurrentLocationResponse> get() = _LocationData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> get() = _isError

    var errorMessage: String = ""
        private set

    fun getLocationData() {

        _isLoading.value = true
        _isError.value = false

        val client = ApiConfig.getApiService().getCurrentLocation()

        // Send API request using Retrofit
        client.enqueue(object : Callback<CurrentLocationResponse> {

            override fun onResponse(
                call: Call<CurrentLocationResponse>,
                response: Response<CurrentLocationResponse>
            ) {
                val responseBody = response.body()
                if (!response.isSuccessful || responseBody == null) {
                    onError("Data Processing Error")
                    return
                }

                _isLoading.value = false
                _LocationData.postValue(responseBody)
            }

            override fun onFailure(call: Call<CurrentLocationResponse>, t: Throwable) {
                onError(t.message)
                t.printStackTrace()
            }

        })
    }

    private fun onError(inputMessage: String?) {

        val message = if (inputMessage.isNullOrBlank() or inputMessage.isNullOrEmpty()) "Unknown Error"
        else inputMessage

        errorMessage = StringBuilder("ERROR: ")
            .append("$message some data may not displayed properly").toString()

        _isError.value = true
        _isLoading.value = false
    }

}
