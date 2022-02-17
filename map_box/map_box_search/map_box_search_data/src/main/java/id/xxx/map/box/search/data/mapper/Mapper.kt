package id.xxx.map.box.search.data.mapper

import android.location.Address
import id.xxx.map.box.search.data.source.local.entity.PlacesEntity
import id.xxx.map.box.search.data.source.remote.response.Features
import id.xxx.map.box.search.data.source.remote.response.PlacesResponse
import id.xxx.map.box.search.domain.model.AddressModel
import id.xxx.map.box.search.domain.model.PlacesModel

fun List<PlacesEntity>.toListPlacesModel() = map {
    PlacesModel(
        id = it.id,
        name = it.name,
        address = it.address,
        latitude = it.latitude,
        longitude = it.longitude
    )
}

fun PlacesResponse.toListPlacesEntity(query: String?) = features.toListPlacesEntity(query)

fun List<Features>.toListPlacesEntity(query: String?) = map {
    PlacesEntity(
        id = it.id,
        name = it.text,
        address = it.place_name,
        longitude = it.center[0],
        latitude = it.center[1],
        query = query
    )
}

fun Address.toAddressModel() = AddressModel(
    address = getAddressLine(0),
    latitude = latitude,
    longitude = longitude
)