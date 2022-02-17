package id.xxx.auth.data.messaging

import android.content.Intent
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import id.xxx.auth.domain.email.usecase.AuthEmailInteractor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MessagingService : FirebaseMessagingService() {

    private val iInteractor by inject<AuthEmailInteractor>()

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("TAG", "Refreshed token: $token")
    }

    override fun handleIntent(intent: Intent) {
        super.handleIntent(intent)
        CoroutineScope(Dispatchers.IO).launch {
            iInteractor.signOut().drop(1).firstOrNull()
        }
    }
}