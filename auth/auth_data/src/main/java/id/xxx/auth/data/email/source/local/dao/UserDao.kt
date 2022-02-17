package id.xxx.auth.data.email.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.xxx.auth.data.email.source.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun getUser(): Flow<UserEntity?>

    @Query("SELECT * FROM user")
    fun getAllUser(): List<UserEntity>

    @Query("SELECT * FROM user WHERE email=:email")
    suspend fun getUser(email: String): UserEntity

    @Query("SELECT * FROM user WHERE email=:email")
    fun getUserAsFlow(email: String): Flow<UserEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userEntity: UserEntity): Long

    /* if is success return 1 else return 0 */
    @Query("UPDATE user set isEmailVerify=:isVerify WHERE id=:id")
    suspend fun update(id: String, isVerify: Boolean): Int

    /* if is success return 1 else return 0 */
    @Query("DELETE FROM user WHERE email=:email")
    suspend fun delete(email: String): Int

    @Query("SELECT * FROM user WHERE isActive=1")
    fun currentUser(): Flow<UserEntity?>

    /* if is success return 1 else return 0 */
    @Query("UPDATE user set isActive=:isActive WHERE email=:email")
    suspend fun setIsActive(email: String, isActive: Boolean): Int

    @Query("SELECT * FROM user WHERE isActive=1")
    fun isActive(): Boolean
}