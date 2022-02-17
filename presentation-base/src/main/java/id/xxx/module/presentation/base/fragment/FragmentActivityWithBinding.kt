package id.xxx.module.presentation.base.fragment

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.viewbinding.ViewBinding

abstract class FragmentActivityWithBinding<ActivityViewBinding : ViewBinding> : FragmentActivity() {

    protected abstract val binding: ActivityViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}