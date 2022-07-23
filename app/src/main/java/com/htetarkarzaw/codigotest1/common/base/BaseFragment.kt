package com.htetarkarzaw.codigotest1.common.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.htetarkarzaw.codigotest1.common.utils.InflateFragment

abstract class BaseFragment<VB : ViewBinding>(private val inflate: InflateFragment<VB>) :
    Fragment() {

    private var _binding: VB? = null
    val binding get() = _binding!!
    lateinit var mContext: Context
    abstract fun observe()
    abstract fun initUi()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        observe()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}