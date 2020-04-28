package com.example.imagegallery.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.imagegallery.R
import com.example.imagegallery.databinding.FragmentMainEntryBinding
import com.example.imagegallery.utils.Utils

public class MainEntryFragment : Fragment() {

    lateinit var binding: FragmentMainEntryBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        inflater.inflate(R.layout.fragment_main_entry, container, false)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_entry, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.practice.setOnClickListener {
            startActivity(Utils.IntentUtils.buildPracticeFragmentIntent(context))
        }
        binding.gallery.setOnClickListener {
            startActivity(Utils.IntentUtils.buildImageListFragmentIntent(context))
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(): Fragment {
            return MainEntryFragment()
        }
    }
}