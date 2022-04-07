package id.xxx.module.presentation.fragment

import android.os.Bundle
import id.xxx.module.presentation.ktx.viewBinding
import id.xxx.module.view.binding.R
import id.xxx.module.view.binding.databinding.ActivityMainBinding

class MainFragmentActivity : ViewBindingFragmentActivity() {

    override val binding by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().add(
            R.id.container,
            InitialViewInConstructorFragment::class.java,
            null,
            null
        ).commit()
    }
}