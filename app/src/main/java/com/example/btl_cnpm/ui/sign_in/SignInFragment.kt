package com.example.btl_cnpm.ui.sign_in

import android.util.Patterns
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.btl_cnpm.R
import com.example.btl_cnpm.base.BaseFragment
import com.example.btl_cnpm.databinding.FoodRecipeFragmentSigninBinding
import com.example.btl_cnpm.utils.SharedPreferencesManager
import com.example.btl_cnpm.utils.UIState
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SignInFragment : BaseFragment<FoodRecipeFragmentSigninBinding>() {
    override val layoutId = R.layout.food_recipe_fragment_signin

    private val viewModel by viewModels<SignInViewModel>()

    @Inject
    lateinit var sharedPre: SharedPreferencesManager

    override fun initView() {
        super.initView()
    }

    override fun initAction() {
        super.initAction()
        binding.apply {
            tvSignUp.setOnClickListener {
                requireView().findNavController().popBackStack(R.id.signInFragment, true)
                requireView().findNavController().navigate(R.id.signUpFragment)
            }
            btnSignIn.setOnClickListener {
                val username = edtEmailSignIn.text.toString().trim()
                val password = edtPasswordSignIn.text.toString().trim()
                if (username.isEmpty() || password.isEmpty()) {
                    showDialogFail(requireContext().getString(R.string.fill_in_information))
                    return@setOnClickListener
                }
                if (Patterns.EMAIL_ADDRESS.matcher(username).matches()) {
                    viewModel.signIn(username, password).observe(requireActivity()) {
                        when (it) {
                            is UIState.Success -> {
                                if (it.data.isNotEmpty()) {
                                    if (cbRememberMeSignIn.isChecked) sharedPre.putString(
                                        "idUser",
                                        it.data
                                    )
                                    requireView().findNavController()
                                        .popBackStack(R.id.login_navigation, true)
                                    requireView().findNavController().navigate(R.id.home_navigation)
                                }
                            }
                            is UIState.Failure -> {
                                notify(it.message + "")
                                showDialogFail(requireContext().getString(R.string.login_fail))
                            }
                        }
                    }
                } else {
                    edtEmailSignIn.error = requireContext().getString(R.string.email_is_not_valid)
                    return@setOnClickListener
                }

            }
            tvForgetPasswordSignIn.setOnClickListener {
                requireView().findNavController().navigate(R.id.forgotPasswordFragment)
            }
        }
    }
}