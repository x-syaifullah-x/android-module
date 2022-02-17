package id.xxx.map.box.search.domain.usecase

import id.xxx.map.box.search.domain.repository.IRepository

class InteractorImpl(
    private val iRepository: IRepository
) : IInteractor {

    override fun getPlaces(query: String?) = iRepository.getPlaces(query)

    override fun getAddress(value: String?) = iRepository.getAddress(value)

}