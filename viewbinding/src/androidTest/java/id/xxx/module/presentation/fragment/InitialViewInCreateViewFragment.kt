package id.xxx.module.presentation.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.delegate.viewBinding
import id.xxx.module.utils.DataUtils
import id.xxx.module.view.binding.databinding.FragmentMainBinding
import org.junit.Assert

class InitialViewInCreateViewFragment : ViewBindingFragment() {

    override val binding by viewBinding<FragmentMainBinding> {
        Log.i(InitialViewInCreateViewFragment::class.java.simpleName, "$this")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvHello.text = DataUtils.TEXT_HELLO_WORD

        Assert.assertEquals(DataUtils.TEXT_HELLO_WORD, binding.tvHello.text)
    }
}