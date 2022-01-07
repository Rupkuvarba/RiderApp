package com.app.eho.ui.modules.navigatedrawer.drawer.share

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.app.eho.databinding.FragmentShareBinding
import com.app.eho.ui.base.BaseFragment

class ShareFragment : BaseFragment() {

    lateinit var viewModel: ShareViewModel
    lateinit var _binding: FragmentShareBinding

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this)[ShareViewModel::class.java]
        _binding = FragmentShareBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }
}
