package id.xxx.auth.data

import android.app.Application
import id.xxx.auth.data.di.AutData
import id.xxx.auth.domain.di.AuthDomain
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {

    override fun onCreate() {
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@App)
            modules(AuthDomain.module, AutData.module)
        }
        super.onCreate()
    }
}