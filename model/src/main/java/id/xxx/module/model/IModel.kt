package id.xxx.module.model

interface IModel<TypeID> {
    val id: TypeID?

    override fun equals(other: Any?): Boolean
}