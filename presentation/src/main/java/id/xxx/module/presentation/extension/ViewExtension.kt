package id.xxx.module.presentation.extension

import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.forEach
import com.google.android.material.behavior.HideBottomViewOnScrollBehavior
import com.google.android.material.bottomnavigation.BottomNavigationView

fun BottomNavigationView.disableMenuChecked() {
    menu.forEach { menu ->
        menu.isEnabled = !menu.isChecked
    }
}

fun BottomNavigationView.show(isShow: Boolean) {
    val layoutParam = layoutParams as CoordinatorLayout.LayoutParams
    val behavior = layoutParam.behavior
    if (behavior is HideBottomViewOnScrollBehavior) behavior.slideUp(this)
    layoutParam.behavior =
        if (isShow) {
            behavior ?: HideBottomViewOnScrollBehavior<BottomNavigationView>()
        } else {
            null
        }
}