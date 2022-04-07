@file:JvmName("StartActivityKtx")

package id.xxx.module.presentation.base.ktx

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import kotlin.reflect.KClass

inline fun <C : Context, A : Activity> C
?.startActivity(kClazz: KClass<A>, block: Intent.() -> Unit = {}) {
    if (this != null) {
        val intent = Intent(this, kClazz.java)
        block(intent)
        startActivity(intent)
    }
}

inline fun <F : Fragment, A : Activity> F
?.startActivity(
    kClazz: KClass<A>,
    block: Intent.() -> Unit = {}
) = this?.context?.startActivity(kClazz = kClazz, block = block)


inline fun <T : Context> T
?.startActivity(block: Intent.() -> Unit = {}) {
    if (this != null) {
        val intent = Intent()
        block(intent)
        startActivity(intent)
    }
}

inline fun <T : Fragment> T
?.startActivity(block: Intent.() -> Unit = {}) =
    this?.context?.startActivity(block)