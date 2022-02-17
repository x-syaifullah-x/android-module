package id.xxx.auth.data.helper

import android.content.Context
import androidx.work.*

class Network(private val context: Context) {

    private val myConstraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

    fun <T : ListenableWorker> onConnected(workerClass: Class<T>, data: Data) {
        val oneTimeWorkRequest = OneTimeWorkRequest
            .Builder(workerClass)
            .setInputData(data)
            .setConstraints(myConstraints)
            .build()
        WorkManager.getInstance(context).enqueue(oneTimeWorkRequest)
    }
}