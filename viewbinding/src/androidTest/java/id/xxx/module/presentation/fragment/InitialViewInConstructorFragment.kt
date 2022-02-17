package id.xxx.module.presentation.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import id.xxx.module.presentation.Utils
import id.xxx.module.view.binding.R
import id.xxx.module.view.binding.databinding.FragmentMainBinding
import id.xxx.module.view.binding.ktx.viewBinding

class InitialViewInConstructorFragment : Fragment(R.layout.fragment_main) {

    private val binding by viewBinding<FragmentMainBinding> {
        Log.i(InitialViewInConstructorFragment::class.java.simpleName, "$this")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvHello.text = Utils.TEXT_HELLO_WORD
    }
}