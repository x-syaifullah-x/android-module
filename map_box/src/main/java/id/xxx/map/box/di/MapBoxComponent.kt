package id.xxx.map.box.di

import androidx.room.Room
import id.xxx.map.box.MapBoxDatabase
import id.xxx.map.box.search.di.SearchModule
import org.koin.core.module.Module
import org.koin.dsl.module

object MapBoxComponent {

    private const val DATABASE_NAME = "id.xxx.map.box"

    private val databaseModule = module {
        single {
            Room.databaseBuilder(get(), MapBoxDatabase::class.java, DATABASE_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
        }
        single { get<MapBoxDatabase>().placesDao() }
    }

    fun getComponent(): List<Module> {
        return mutableListOf(databaseModule).apply { addAll(SearchModule.modules) }
    }
}