package id.xxx.module.presentation.base.reciver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent

abstract class BaseReceiver : BroadcastReceiver() {

    protected abstract fun onReceive(context: ContextWrapper, intent: Intent?)

    override fun onReceive(context: Context, intent: Intent?) {
        onReceive(context.applicationContext as ContextWrapper, intent)
    }
}