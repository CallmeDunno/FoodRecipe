package com.example.btl_cnpm.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController

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