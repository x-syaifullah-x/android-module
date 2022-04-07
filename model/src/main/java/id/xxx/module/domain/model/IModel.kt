package id.xxx.module.domain.model

interface IModel<TypeID> {

    val id: TypeID?

    override fun equals(other: Any?): Boolean
}