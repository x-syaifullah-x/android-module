package id.xxx.module.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import id.xxx.module.presentation.fragment.InitialViewInConstructorFragment
import id.xxx.module.view.binding.R
import id.xxx.module.view.binding.databinding.ActivityMainBinding
import id.xxx.module.view.binding.ktx.viewBinding

class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)

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