@file:JvmName("SetResultKtx")

package id.xxx.module.presentation.base.ktx

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.Fragment

inline fun <T : Activity> T
?.setResult(block: Intent.() -> Unit = {}) {
    if (this != null) {
        val intent = Intent()
        block(intent)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}

inline fun <T : Fragment> T
?.setResult(block: Intent.() -> Unit = {}) =
    this?.activity?.setResult(block)