package id.xxx.module.presentation.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.delegate.viewBinding
import id.xxx.module.utils.DataUtils
import id.xxx.module.view.binding.databinding.FragmentMainBinding
import org.junit.Assert

class CallBindingInOnCreateFragment : ViewBindingFragment() {

    override val binding by viewBinding<FragmentMainBinding> {
        Log.i(CallBindingInOnCreateFragment::class.java.simpleName, "$this")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.tvHello.text = DataUtils.TEXT_HELLO_WORD

        Assert.assertEquals(DataUtils.TEXT_HELLO_WORD, binding.tvHello.text)
    }
}