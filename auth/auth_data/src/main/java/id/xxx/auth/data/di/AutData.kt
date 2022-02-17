package id.xxx.auth.data.di

import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessaging
import id.xxx.auth.data.db.AuthDatabase
import id.xxx.auth.data.email.repository.AuthEmailRepositoryImpl
import id.xxx.auth.data.email.source.local.LocalDataSource
import id.xxx.auth.data.email.source.remote.RemoteDataSource
import id.xxx.auth.data.helper.Network
import id.xxx.auth.domain.email.repository.AuthEmailRepository
import org.koin.dsl.module

object AutData {

    val module = module {
        single {
            Room.databaseBuilder(
                get(), AuthDatabase::class.java, AuthDatabase.DB_NAME
            ).build()
        }
        single { get<AuthDatabase>().userDao() }
        single { LocalDataSource(get()) }

        single { FirebaseAuth.getInstance() }
        single { FirebaseMessaging.getInstance() }
        single { RemoteDataSource(get(), get()) }

        single { Network(get()) }

        single<AuthEmailRepository> { AuthEmailRepositoryImpl(get(), get(), get()) }
    }
}