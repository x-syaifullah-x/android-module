package id.xxx.module.presentation.base.reciver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

abstract class BaseBroadcastReceiver : BroadcastReceiver() {

    protected abstract fun onReceives(context: Context, intent: Intent)

    final override fun onReceive(context: Context, intent: Intent?) {
        if (intent != null) {
            onReceives(context, intent)
        }
    }
}