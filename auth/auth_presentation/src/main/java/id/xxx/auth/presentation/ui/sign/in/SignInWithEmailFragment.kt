package id.xxx.auth.presentation.ui.sign.`in`

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Toast.LENGTH_SHORT
import android.widget.Toast.makeText
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import id.xxx.auth.presentation.R
import id.xxx.auth.presentation.databinding.FragmentSignInWithEmailBinding
import id.xxx.auth.presentation.helper.InputValidation
import id.xxx.auth.presentation.helper.asFlow
import id.xxx.auth.presentation.ui.AuthActivityAppCompatActivity.Companion.getAuthDestination
import id.xxx.auth.presentation.ui.AuthEmailViewModel
import id.xxx.auth.presentation.ui.Utils
import id.xxx.module.domain.model.resources.Resources.Companion.`when`
import id.xxx.module.view.binding.ktx.viewBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.map
import org.koin.android.ext.android.inject

@ExperimentalCoroutinesApi
class SignInWithEmailFragment : Fragment(R.layout.fragment_sign_in_with_email) {

    private val binding by viewBinding<FragmentSignInWithEmailBinding>()

    private val viewModel by inject<AuthEmailViewModel>()

    private val inputEmail by lazy { binding.inputEmail }

    private val inputPassword by lazy { binding.inputPassword }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.setOnClick {
            handleClick(it)
        }

        inputEmail.asFlow().map { Utils.emailIsValid(it) }.asLiveData()
            .observe(viewLifecycleOwner) {
                inputEmail.error = if (it) null else "Email Not Valid"
                viewModel.setStateSignIn(
                    AuthEmailViewModel.KEY_EMAIL_SIGN_IN,
                    inputEmail.error == null
                )
            }

        inputPassword.asFlow().map { Utils.passwordValidation(it) }.asLiveData()
            .observe(viewLifecycleOwner) {
                inputPassword.error = if (it is InputValidation.NotValid) it.message else null
                viewModel.setStateSignIn(
                    AuthEmailViewModel.KEY_PASSWORD_SIGN_IN,
                    inputPassword.error == null
                )
            }

        viewModel.getStatSignInLiveData()
            .observe(viewLifecycleOwner) { binding.login.isEnabled = it }
    }

    private fun handleClick(view: View) {
        when (view.id) {
            R.id.login -> {
                viewModel.signIn("${inputEmail.text}", "${inputPassword.text}")
                    .observe(viewLifecycleOwner) {
                        it.`when`(
                            loading = {
                                binding.loading.isVisible = true
                                binding.login.isEnabled = false
                            },
                            success = { user ->
                                if (user.isVerify) {
                                    val clazzName = requireActivity().intent.getAuthDestination()
                                    if (clazzName != null) {
                                        requireActivity().startActivity(clazzName) {
                                            putExtra(clazzName, user)
                                        }
                                    } else {
                                        requireActivity().setResult(Activity.RESULT_OK)
                                        requireActivity().finish()
                                    }
                                } else {
                                    findNavController().navigate(R.id.sign_in_move_to_fragment_verify)
                                }
                            },
                            error = { _, throwable ->
                                makeText(
                                    requireContext(),
                                    throwable.localizedMessage,
                                    LENGTH_SHORT
                                ).show()
                                binding.loading.isVisible = false
                                binding.login.isEnabled = true
                            },
                            empty = {
                                binding.loading.isVisible = false
                                binding.login.isEnabled = true
                            }
                        )
                    }
            }
        }
    }
}