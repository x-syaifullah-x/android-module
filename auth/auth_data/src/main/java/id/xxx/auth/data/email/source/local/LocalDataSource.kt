package id.xxx.auth.data.email.source.local

import id.xxx.auth.data.email.source.local.dao.UserDao
import id.xxx.auth.data.email.source.local.entity.UserEntity
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow

class LocalDataSource(private val dao: UserDao) {

    suspend fun getUser(email: String) = dao.getUser(email)

    suspend fun signIn(userEntity: UserEntity): Long {
        dao.getAllUser().map {
            dao.setIsActive(it.email ?: "", false)
        }
        return dao.insert(userEntity)
    }

    suspend fun signUp(userEntity: UserEntity): Long {
        dao.getAllUser().map {
            dao.setIsActive(it.email ?: "", false)
        }
        return dao.insert(userEntity)
    }

    suspend fun setVerify(isVerify: Boolean): Boolean {
        val user = dao.currentUser().firstOrNull()
        return user?.run { dao.update(user.id, isVerify) != 0 } ?: false
    }

    suspend fun remove(email: String) = dao.delete(email)

    fun signIn(email: String) = flow {
        dao.setIsActive(email, true)
        emitAll(dao.getUserAsFlow(email))
    }

    fun currentUser() = dao.currentUser()

    suspend fun signOut(): Boolean {
        val currentUser = dao.currentUser().firstOrNull()
        return currentUser?.run {
            dao.setIsActive(email ?: "", false) != 0
        } ?: false
    }

    fun onSignOut() = dao.isActive()
}