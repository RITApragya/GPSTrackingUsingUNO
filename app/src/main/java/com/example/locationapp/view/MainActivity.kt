package com.example.locationapp.view

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.locationapp.R
import com.example.locationapp.model.CurrentLocationResponse
import com.example.locationapp.viewmodel.MainViewModel
import java.lang.StringBuilder
import java.util.Calendar
import java.util.TimeZone

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel

    private lateinit var tvlat: TextView
    private lateinit var tvlong: TextView
//    private lateinit var tvele: TextView
    private lateinit var tvtime: TextView
    private lateinit var butreq: Button
//    val url = "https://api.thingspeak.com/channels/2521665/feeds/last.json"

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        mainViewModel = MainViewModel()
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        subscribe()
//        getCurrentTime("")

//        etCityName = findViewById(R.id.et_city_name)
//        imgCondition = findViewById(R.id.img_condition)
//        tvResult = findViewById(R.id.tv_result)
//        btnSend = findViewById(R.id.btn_send_request)
        tvlat = findViewById(R.id.latitudeVal)
        tvlong = findViewById(R.id.longitudeVal)
//        tvele = findViewById(R.id.altVal)
        tvtime = findViewById(R.id.timeVal)
        butreq = findViewById(R.id.buttonreq)

        // Add on click event to the send button
        butreq.setOnClickListener {
            // Text field validation
            mainViewModel.getLocationData()

        }
    }

    private fun subscribe() {
        mainViewModel.isLoading.observe(this) { isLoading ->
            // Set the result text to Loading
            if (isLoading) {
                tvlat.text = resources.getString(R.string.loading)
                tvlong.text = resources.getString(R.string.loading)
//                tvele.text = resources.getString(R.string.loading)
                tvtime.text = resources.getString(R.string.loading)
            }
        }

        mainViewModel.isError.observe(this) { isError ->
            // Hide display image and set the result text to the error message
            if (isError) {
//                imgCondition.visibility = View.GONE
                tvlat.text = mainViewModel.errorMessage
                tvlong.text = mainViewModel.errorMessage
                tvtime.text = mainViewModel.errorMessage
//                tvele.text = mainViewModel.errorMessage
            }
        }

        mainViewModel.locationData.observe(this) { locationData ->
            // Display weather data to the UI
            setResultText(locationData)
        }
    }

    private fun getCurrentTime(): Calendar {
        return Calendar.getInstance(TimeZone.getTimeZone("IST"))
    }

    private fun setResultText(locationData: CurrentLocationResponse) {
        val latText = StringBuilder("\n")
        val longText = StringBuilder("\n")
//        val eleText = StringBuilder("\n")
        val timeText = StringBuilder("\n")

        //get time
        val currentTime = getCurrentTime() // Example: you can specify your desired timezone
        val hour = currentTime.get(Calendar.HOUR_OF_DAY)
        val minute = currentTime.get(Calendar.MINUTE)
        val second = currentTime.get(Calendar.SECOND)

        locationData.let {
            latText.append("${it.latitude}\n")
            longText.append("${it.longitude}\n")
//            eleText.append("${it.altitude}\n")
            timeText.append("${hour}:${minute}:${second} IST\n")
//            resultText.append("Local Time: ${location?.localtime}\n")
        }

        tvlat.text = latText
        tvlong.text = longText
//        tvele.text = eleText
        tvtime.text = timeText
    }


}