package id.xxx.map.box.search.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AddressModel(
    val address: String,
    val latitude: Double,
    val longitude: Double,
) : Parcelable