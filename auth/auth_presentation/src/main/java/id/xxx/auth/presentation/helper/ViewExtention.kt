package id.xxx.auth.presentation.helper

import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn

@OptIn(ExperimentalCoroutinesApi::class)
fun TextView.asFlow() = callbackFlow {
    addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable) {
            trySend(s.toString())
        }
    })
    awaitClose()
}.flowOn(Dispatchers.IO)
