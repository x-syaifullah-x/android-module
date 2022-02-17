package id.xxx.module.model.mapper

interface IMapper<Input, Output> {
    fun map(input: Input): Output
}