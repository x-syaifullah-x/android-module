package id.xxx.module.domain.model.mapper

interface IMapper<Input, Output> {
    fun map(input: Input): Output
}