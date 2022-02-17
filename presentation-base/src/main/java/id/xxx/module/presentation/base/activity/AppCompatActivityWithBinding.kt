package id.xxx.module.presentation.base.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class AppCompatActivityWithBinding<ActivityViewBinding : ViewBinding> : AppCompatActivity() {

    protected abstract val binding: ActivityViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}