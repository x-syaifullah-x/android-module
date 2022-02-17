package id.xxx.module.presentation.base.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder

abstract class BaseService : Service() {

    override fun onBind(intent: Intent): IBinder = ServiceBinder()

    inner class ServiceBinder : Binder() {
        fun getService() = this@BaseService
    }
}