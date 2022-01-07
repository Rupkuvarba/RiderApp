package com.app.eho.ui.modules.navigatedrawer.drawer.tools

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.app.eho.databinding.FragmentToolsBinding
import com.app.eho.ui.base.BaseFragment

class ToolsFragment : BaseFragment() {

    lateinit var viewModel: ToolsViewModel
    lateinit var _binding: FragmentToolsBinding

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this)[ToolsViewModel::class.java]
        _binding = FragmentToolsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }
}