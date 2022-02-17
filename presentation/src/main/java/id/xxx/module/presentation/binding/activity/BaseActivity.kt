package id.xxx.module.presentation.binding.activity

import android.app.Activity
import android.content.Intent
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import id.xxx.module.presentation.base.activity.AppCompatActivityWithBinding

abstract class BaseActivity<ActivityViewBinding : ViewBinding> :
    AppCompatActivityWithBinding<ActivityViewBinding>() {

    private fun getChildFragment(): List<Fragment> {
        val fragment = supportFragmentManager.fragments.firstOrNull()
        return fragment?.childFragmentManager?.fragments ?: listOf()
    }

    /**
     * Forwarding onActivityResult to fragment in NavHostFragment
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        if (resultCode == Activity.RESULT_OK) {
            getChildFragment().forEach { it.onActivityResult(requestCode, resultCode, intent) }
        }
    }

    /**
     * Forward onOptionsItemSelected to fragment
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        getChildFragment().forEach { it.onOptionsItemSelected(item) }
        return super.onOptionsItemSelected(item)
    }

    /*
    ┬───
    │ GC Root: Global variable in native code
    │
    ├─ android.app.Activity$1 instance
    │    Leaking: UNKNOWN
    │    Retaining 2.1 MB in 36660 objects
    │    Library leak match: instance field android.app.Activity$1#this$0
    │    Anonymous subclass of android.app.IRequestFinishCallback$Stub
    │    this$0 instance of id.movie.catalogue.presentation.ui.home.MainActivity with mDestroyed = true
    │    ↓ Activity$1.this$0
    │                 ~~~~~~
    ╰→ id.movie.catalogue.presentation.ui.home.MainActivity instance
    ​     Leaking: YES (ObjectWatcher was watching this because id.movie.catalogue.presentation.ui.home.MainActivity
    ​     received Activity#onDestroy() callback and Activity#mDestroyed is true)
    ​     Retaining 2.1 MB in 36659 objects
    ​     key = 4fd45154-7480-470e-83cd-cee30b0ada8c
    ​     watchDurationMillis = 5309
    ​     retainedDurationMillis = 281
    ​     mApplication instance of id.movie.catalogue.App
    ​     mBase instance of androidx.appcompat.view.ContextThemeWrapper
             Handle Leaking
    */
    protected open fun isOnBackPressedFinishAfterTransition(): Boolean = true

    override fun onBackPressed() {
        if (isOnBackPressedFinishAfterTransition()) {
            finishAfterTransition()
        } else {
            super.onBackPressed()
        }
    }
}