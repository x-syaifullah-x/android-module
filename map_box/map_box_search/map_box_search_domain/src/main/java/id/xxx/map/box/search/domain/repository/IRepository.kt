package id.xxx.map.box.search.domain.repository

import id.xxx.map.box.search.domain.model.AddressModel
import id.xxx.map.box.search.domain.model.PlacesModel
import id.xxx.module.model.sealed.ResourceSealed
import kotlinx.coroutines.flow.Flow

interface IRepository {

    fun getPlaces(query: String?): Flow<ResourceSealed<List<PlacesModel>>>

    fun getAddress(value: String?): Flow<ResourceSealed<AddressModel>>
}