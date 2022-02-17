@file:JvmName("ActionBarKtx")

package id.xxx.module.presentation.base.ktx

import androidx.appcompat.app.ActionBar

fun ActionBar?.show(isShow: Boolean) = this?.apply { if (isShow) show() else hide() }

fun ActionBar?.setHomeButton(
    isHomeButtonEnabled: Boolean = false,
    isDisplayHomeAsUpEnabled: Boolean = false
) = this?.apply {
    setHomeButtonEnabled(isHomeButtonEnabled)
    setDisplayHomeAsUpEnabled(isDisplayHomeAsUpEnabled)
}