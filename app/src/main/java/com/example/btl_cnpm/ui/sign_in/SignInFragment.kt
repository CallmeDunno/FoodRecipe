package com.example.btl_cnpm.ui.sign_in

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Patterns
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import android.widget.Button
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
                                showDialogSignInFail()
                            }
                        }
                    }
                }

            }
            tvForgetPasswordSignIn.setOnClickListener {
                requireView().findNavController().navigate(R.id.forgotPasswordFragment)
            }
        }
    }

    private fun showDialogSignInFail() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.food_recipe_dialog_sign_in_failure)
        dialog.setCanceledOnTouchOutside(false)
        val window = dialog.window ?: return
        window.setGravity(Gravity.CENTER)
        window.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialog.findViewById<Button>(R.id.btnTryAgainDialog).setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }
}