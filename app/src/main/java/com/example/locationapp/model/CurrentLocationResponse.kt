package com.example.locationapp.model

import com.google.gson.annotations.SerializedName

data class CurrentLocationResponse(

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("Latitude")
	val latitude: String? = null,

	@field:SerializedName("entry_id")
	val entryId: Int? = null,

	@field:SerializedName("Longitude")
	val longitude: String? = null,

	@field:SerializedName("Altitude")
	val altitude: String? = null
)
