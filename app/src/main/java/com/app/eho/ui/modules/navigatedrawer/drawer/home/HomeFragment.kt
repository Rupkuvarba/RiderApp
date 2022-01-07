package com.app.eho.ui.modules.navigatedrawer.drawer.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.app.eho.databinding.FragmentHomeBinding
import com.app.eho.ui.base.BaseFragment

class HomeFragment : BaseFragment() {

    lateinit var viewModel: HomeViewModel
    lateinit var _binding : FragmentHomeBinding

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }


}