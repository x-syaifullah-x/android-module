package id.xxx.module.presentation.activity

import android.os.Bundle
import androidx.viewbinding.ViewBinding

abstract class ViewBindingActivity : BaseActivity() {

    protected abstract val binding: ViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
    }
}