package id.xxx.module.presentation.extension

import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavDestination

fun AppCompatActivity.disableDisplayShowAndHomeAsUp(des: NavDestination, @IdRes vararg id: Int) {
    if (id.contains(des.id)) {
        supportActionBar?.setDisplayShowHomeEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }
}