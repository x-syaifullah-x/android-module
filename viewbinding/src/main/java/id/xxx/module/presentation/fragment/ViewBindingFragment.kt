package id.xxx.module.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class ViewBindingFragment(
    @LayoutRes private val contentLayoutId: Int = 0
) : Fragment(contentLayoutId) {

    protected abstract val binding: ViewBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (contentLayoutId == 0) {
            val rootView = binding.root
            if (rootView.background == null) {
                rootView.background = activity?.window?.decorView?.background
            }
            return rootView
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}