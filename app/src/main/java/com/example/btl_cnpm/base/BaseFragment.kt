package com.example.btl_cnpm.base

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.btl_cnpm.R

abstract class BaseFragment<VB: ViewDataBinding> : Fragment() {
    protected lateinit var binding: VB
    abstract val layoutId: Int

    private val navController: NavController by lazy {
        findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initAction()
    }

    open fun initView(){}

    open fun initAction(){}

    fun notify(msg: String){
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }

    fun showDialogFail(message: String) {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.food_recipe_dialog_sign_in_failure)
        val txtSignUpFail = dialog.findViewById<TextView>(R.id.tvContentError)
        txtSignUpFail.text = message
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

    fun popBackStack() {
        try {
            if (!navController.popBackStack()) {
                val activity = requireActivity()
                if (activity is FragmentActivity) {
                    activity.onBackPressed()
                }
            }
        } catch (e: Exception) {
            val activity = requireActivity()
            if (activity is FragmentActivity) {
                activity.onBackPressed()
            }
        }
    }
}