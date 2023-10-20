package com.example.btl_cnpm.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<VB: ViewDataBinding> : AppCompatActivity() {
    protected lateinit var binding: VB
    abstract val layoutID: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  DataBindingUtil.setContentView(this, layoutID)
        setContentView(binding.root)
        initView()
        initAction()
    }

    open fun initView() {}

    open fun initAction() {}
}