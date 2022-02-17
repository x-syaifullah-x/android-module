package id.xxx.map.box

import androidx.room.Database
import androidx.room.RoomDatabase
import id.xxx.map.box.search.data.source.local.dao.ISearchDao
import id.xxx.map.box.search.data.source.local.entity.PlacesEntity

@Database(
    entities = [
        PlacesEntity::class
    ],
    exportSchema = false,
    version = 1,
)
abstract class MapBoxDatabase : RoomDatabase(), ISearchDao