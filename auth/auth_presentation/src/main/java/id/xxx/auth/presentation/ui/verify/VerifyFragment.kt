package id.xxx.auth.presentation.ui.verify

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import id.xxx.auth.presentation.R
import id.xxx.auth.presentation.databinding.FragmentVerifyBinding
import id.xxx.auth.presentation.ui.AuthActivity.Companion.getAuthDestination
import id.xxx.auth.presentation.ui.AuthEmailViewModel
import id.xxx.module.model.sealed.Resource
import id.xxx.module.model.sealed.Resource.Companion.whenNoReturn
import id.xxx.module.presentation.base.ktx.startActivity
import id.xxx.module.view.binding.ktx.viewBinding
import org.koin.android.ext.android.inject

class VerifyFragment : Fragment(R.layout.fragment_verify) {

    private val binding by viewBinding<FragmentVerifyBinding>()

    private val viewModel by inject<AuthEmailViewModel>()

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
//            interactor.signOut()
//            findNavController().navigate(R.id.move_to_fragment_welcome)
        }
    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return when (item.itemId) {
//            android.R.id.home -> {
//                interactor.signOut();true
//            }
//            else -> super.onOptionsItemSelected(item)
//        }
//    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val clazzName = requireActivity().intent.getAuthDestination()

        viewModel.sendVerify().observe(viewLifecycleOwner) {
            it.whenNoReturn(
                blockSuccess = { data ->
                    if (data == "is email verified") {
                        if (clazzName != null) {
                            requireActivity().startActivity(clazzName)
                                .apply { requireActivity().finish() }
                        } else {
                            requireActivity().setResult(Activity.RESULT_OK)
                        }
                    } else {
                        Toast.makeText(requireContext(), data, LENGTH_SHORT).show()
                    }
                }
            )
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner, onBackPressedCallback
        )

        binding.btnConfirmVerifyEmail.setOnClickListener {
            viewModel.isVerify().observe(viewLifecycleOwner) { isVerify ->
                if (isVerify is Resource.Success && isVerify.data) {
                    if (clazzName != null) {
                        requireActivity().startActivity(clazzName)
                            .apply { requireActivity().finish() }
                    } else {
                        requireActivity().setResult(Activity.RESULT_OK)
                    }
                } else {
                    Toast.makeText(requireContext(), "email not yet verify", LENGTH_SHORT).show()
                }
            }
        }
    }
}