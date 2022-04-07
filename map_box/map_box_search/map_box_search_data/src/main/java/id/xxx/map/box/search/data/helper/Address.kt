package id.xxx.map.box.search.data.helper

import android.content.Context
import android.location.Geocoder
import id.xxx.module.data.helper.DataHelper
import java.util.*

class Address private constructor(context: Context) {

    companion object {
        @Volatile
        private var INSTANCE: Address? = null

        fun getInstance(context: Context) = INSTANCE ?: synchronized(this) {
            INSTANCE ?: Address(context)
                .also { INSTANCE = it }
        }

        fun isLatLong(value: String): Boolean =
            value.trim().matches(Regex("[-]?[0-9]*[.]?[0-9]*[,][-]?[0-9]*[.]?[0-9]*"))
    }

    private val geoCoder by lazy { Geocoder(context) }

    suspend fun geoCoder(value: String) = DataHelper.get(
        blockFetch = {
            val result = runCatching {
                if (isLatLong(value)) {
                    val stringTokenizer = StringTokenizer(value.replace("\\s".toRegex(), ""), ",")
                    val latitude = stringTokenizer.nextToken().toDouble()
                    val longitude = stringTokenizer.nextToken().toDouble()
                    val listAddress = geoCoder.getFromLocation(latitude, longitude, 1)
                    if (listAddress.isNotEmpty()) listAddress[0] else null
                } else {
                    val listAddress = geoCoder.getFromLocationName(value, 1)
                    if (listAddress.isNotEmpty()) listAddress[0] else null
                }
            }
            return@get result.getOrNull()
        }
    )
}