package id.xxx.map.box.search.data.repository

import id.xxx.map.box.search.data.helper.Address
import id.xxx.map.box.search.data.mapper.toAddressModel
import id.xxx.map.box.search.data.mapper.toListPlacesEntity
import id.xxx.map.box.search.data.mapper.toListPlacesModel
import id.xxx.map.box.search.data.source.local.LocalDataSource
import id.xxx.map.box.search.data.source.remote.RemoteDataSource
import id.xxx.map.box.search.domain.repository.IRepository
import id.xxx.module.model.sealed.Result
import id.xxx.module.data.mediator.ResourceNetworkBound
import id.xxx.module.model.sealed.Resource
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class RepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val address: Address
) : IRepository {

    override fun getPlaces(query: String?) = ResourceNetworkBound.create(
        blockResult = {
            localDataSource.getPlaces(query).map { it.toListPlacesModel() }
        },
        blockShouldFetch = { localDataSource.isShouldFetch(query) },
        blockFetch = { remoteDataSource.getPlaces(query) },
        blockOnFetch = { apiResponse, _ ->
            if (apiResponse is Result.Success) {
                localDataSource.saveDataPlaces(apiResponse.data.toListPlacesEntity(query))
            }
        }
    )

    override fun getAddress(value: String?) = flow {
        emit(Resource.Loading)

        val result =
            if (value.isNullOrBlank()) {
                Resource.Empty
            } else {
                when (val data = address.geoCoder(value)) {
                    is Result.Success -> Resource.Success(data.data.toAddressModel())
                    is Result.Error -> Resource.Error(error = data.error)
                    is Result.Empty -> Resource.Empty
                }
            }
        emit(result)
    }
}