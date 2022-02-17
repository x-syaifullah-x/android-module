package id.xxx.map.box.search.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.xxx.map.box.search.data.source.local.entity.PlacesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PlacesDao {

    @Query("SELECT * FROM PlacesEntity WHERE name LIKE '%' || :query || '%' OR address LIKE '%' || :query || '%' ORDER BY id DESC")
    fun getPlaces(query: String): Flow<List<PlacesEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: List<PlacesEntity>): List<Long>

    @Query("SELECT * FROM PlacesEntity WHERE `query`=:query")
    fun isQueryNotEmpty(query: String?): Boolean
}