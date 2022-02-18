package id.xxx.map.box.search.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class PlacesResponse(
    @SerializedName("type") val type: String,
    @SerializedName("query") val query: List<String>,
    @SerializedName("features") val features: List<Features>,
    @SerializedName("attribution") val attribution: String
)