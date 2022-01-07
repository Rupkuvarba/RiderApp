package com.app.eho.ui.modules.navigatedrawer.drawer.slideshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.app.eho.databinding.FragmentSlideshowBinding
import com.app.eho.ui.base.BaseFragment

class SlideshowFragment : BaseFragment() {

    lateinit var viewModel: SlideshowViewModel
    lateinit var _binding: FragmentSlideshowBinding

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this)[SlideshowViewModel::class.java]
        _binding = FragmentSlideshowBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }
}