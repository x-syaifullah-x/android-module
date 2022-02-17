package id.xxx.map.box.search.data.source.remote.service

import id.xxx.map.box.search.data.source.remote.response.PlacesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @Suppress("SpellCheckingInspection")
    @GET("/geocoding/v5/mapbox.places/{query}.json")
    suspend fun getPlaces(
        @Path("query") query: String?,
        @Query("access_token") accessToken: String? = id.xxx.map.box.search.data.BuildConfig.API_KEY,
        @Query("autocomplete") autoComplete: Boolean? = true
    ): PlacesResponse
}