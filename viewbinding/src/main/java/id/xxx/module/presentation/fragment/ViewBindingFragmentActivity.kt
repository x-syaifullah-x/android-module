package id.xxx.module.presentation.fragment

import android.os.Bundle
import androidx.viewbinding.ViewBinding

abstract class ViewBindingFragmentActivity : BaseFragmentActivity() {

    protected abstract val binding: ViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
    }
}