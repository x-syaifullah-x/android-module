package id.xxx.map.box.search.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import id.xxx.module.domain.model.IModel

@Entity
data class PlacesEntity(

    @PrimaryKey
    override val id: String,

    val name: String,

    val address: String,

    val latitude: Double,

    val longitude: Double,

    val query: String?

) : IModel<String>