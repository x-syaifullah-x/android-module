package id.xxx.auth

import android.app.Application
import id.xxx.auth.di.Auth
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {

    override fun onCreate() {
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@App)
            modules(Auth.modules)
        }

        super.onCreate()
    }

}