package com.app.eho.ui.modules.navigatedrawer.drawer.send

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.app.eho.databinding.FragmentSendBinding
import com.app.eho.ui.base.BaseFragment

class SendFragment : BaseFragment() {

    lateinit var viewModel: SendViewModel
    lateinit var _binding : FragmentSendBinding

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this)[SendViewModel::class.java]
        _binding = FragmentSendBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }


}