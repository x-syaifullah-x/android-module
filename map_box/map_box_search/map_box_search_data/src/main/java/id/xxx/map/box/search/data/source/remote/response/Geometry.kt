package id.xxx.map.box.search.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class Geometry(
	@SerializedName("type") val type: String,
	@SerializedName("coordinates") val coordinates: List<Double>
)