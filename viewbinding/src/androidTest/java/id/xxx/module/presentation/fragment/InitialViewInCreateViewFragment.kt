package id.xxx.module.presentation.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import id.xxx.module.presentation.Utils
import id.xxx.module.view.binding.databinding.FragmentMainBinding
import id.xxx.module.view.binding.ktx.viewBinding

class InitialViewInCreateViewFragment : Fragment() {

    private val binding by viewBinding<FragmentMainBinding> {
        Log.i(InitialViewInCreateViewFragment::class.java.simpleName, "$this")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvHello.text = Utils.TEXT_HELLO_WORD
    }
}