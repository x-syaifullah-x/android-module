package id.xxx.module.presentation.activity

import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import id.xxx.module.presentation.ktx.fragmentStackIsEmpty

abstract class BaseActivity : AppCompatActivity() {

    @CallSuper
    override fun onBackPressed() {
        if (fragmentStackIsEmpty()) {
            finishAfterTransition()
        } else {
            super.onBackPressed()
        }
    }
}