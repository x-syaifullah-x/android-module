package id.xxx.auth.data.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import id.xxx.auth.data.email.source.remote.RemoteDataSource
import id.xxx.module.domain.model.results.Results.Success
import kotlinx.coroutines.flow.first
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class Worker(
    context: Context, workerParam: WorkerParameters
) : CoroutineWorker(context, workerParam), KoinComponent {

    companion object {

        const val DATA_EXTRA_EMAIL = "DATA_EXTRA_EMAIL"
        const val DATA_EXTRA_PASS = "DATA_EXTRA_PASS"

        fun putData(email: String, password: String): Data {
            return Data.Builder()
                .putString(DATA_EXTRA_EMAIL, email)
                .putString(DATA_EXTRA_PASS, password)
                .build()
        }
    }

    private val remoteDataSource by inject<RemoteDataSource>()

    override suspend fun doWork(): Result {
        val email = inputData.getString(DATA_EXTRA_EMAIL) ?: return Result.failure()
        val pass = inputData.getString(DATA_EXTRA_PASS) ?: return Result.failure()
        val result = remoteDataSource.signIn(email, pass).first() is Success

        return if (result) Result.success() else Result.failure()
    }
}