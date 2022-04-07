package id.xxx.module.presentation.fragment

import androidx.annotation.CallSuper
import androidx.fragment.app.FragmentActivity
import id.xxx.module.presentation.ktx.fragmentStackIsEmpty

abstract class BaseFragmentActivity : FragmentActivity() {

    @CallSuper
    override fun onBackPressed() {
        if (fragmentStackIsEmpty()) {
            finishAfterTransition()
        } else {
            super.onBackPressed()
        }
    }
}