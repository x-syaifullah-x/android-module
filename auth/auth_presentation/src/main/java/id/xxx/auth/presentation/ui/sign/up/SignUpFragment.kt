package id.xxx.auth.presentation.ui.sign.up

import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import id.xxx.auth.domain.model.SignUpModel
import id.xxx.auth.presentation.R
import id.xxx.auth.presentation.databinding.FragmentSignUpBinding
import id.xxx.auth.presentation.helper.InputValidation
import id.xxx.auth.presentation.ui.AuthEmailViewModel
import id.xxx.auth.presentation.helper.asFlow
import id.xxx.auth.presentation.ui.Utils
import id.xxx.auth.presentation.ui.Utils.emailIsValid
import id.xxx.auth.presentation.ui.Utils.nameIsValid
import id.xxx.module.model.sealed.Resource
import id.xxx.module.model.sealed.Resource.Companion.whenNoReturn
import id.xxx.module.view.binding.ktx.viewBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.map
import org.koin.android.ext.android.inject

@ExperimentalCoroutinesApi
class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private val binding by viewBinding<FragmentSignUpBinding>()

    private val viewModel by inject<AuthEmailViewModel>()

    private val inputEmail by lazy { binding.inputEmail }
    private val inputPassword by lazy { binding.inputPassword }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.btnSignUp.setOnClickListener {
            viewModel
                .createUser("${inputEmail.text}", "${inputPassword.text}")
                .asLiveData()
                .observe(viewLifecycleOwner, this::statCreateUser)
        }

        val inputName = binding.inputName

        inputName.asFlow().map { nameIsValid(it) }.asLiveData()
            .observe(viewLifecycleOwner, {
                inputName.error = if (it) null else "Field Name Not Valid"
                viewModel.setStateSignUp(
                    AuthEmailViewModel.KEY_NAME_SIGN_UP,
                    inputName.error == null
                )
            })

        inputEmail.asFlow().map { emailIsValid(it) }.asLiveData().observe(viewLifecycleOwner, {
            inputEmail.error = if (it) null else "Field Email Not Valid"
            viewModel.setStateSignUp(AuthEmailViewModel.KEY_EMAIL_SIGN_UP, inputEmail.error == null)
        })

        inputPassword.asFlow().map { Utils.passwordValidation(it) }
            .asLiveData()
            .observe(viewLifecycleOwner, {
                inputPassword.error = if (it is InputValidation.NotValid) it.message else null
                viewModel.setStateSignUp(
                    AuthEmailViewModel.KEY_PASSWORD_SIGN_UP, inputPassword.error == null
                )
            })

        viewModel.getStatSignUpLiveData()
            .observe(viewLifecycleOwner, { binding.btnSignUp.isEnabled = it })
    }

    private fun statCreateUser(resource: Resource<SignUpModel>) {
        resource.whenNoReturn(
            blockLoading = {
                showLoading(true)
            },
            blockSuccess = {
                findNavController().navigate(R.id.move_to_fragment_verify)
                showLoading(false)
            },
            blockError = { _, error ->
                showLoading(false)
                Toast.makeText(requireContext(), error.localizedMessage, LENGTH_SHORT).show()
            },
            blockEmpty = {
                showLoading(false)
            }
        )
    }

    private fun showLoading(isShow: Boolean) {
        binding.btnSignUp.isEnabled = !isShow
        binding.loading.isVisible = isShow
    }
}