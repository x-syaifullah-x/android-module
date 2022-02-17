package id.xxx.map.box.search.domain.model

import android.os.Parcelable
import id.xxx.module.model.IModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlacesModel(

    override val id: String,

    val name: String,

    val address: String,

    val latitude: Double,

    val longitude: Double

) : IModel<String>, Parcelable