package com.reece.goodbois.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.reece.goodbois.R
import com.reece.goodbois.databinding.FragmentDoggieDetailBinding


class DoggieDetailFragment : Fragment() {

    private lateinit var binding: FragmentDoggieDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentDoggieDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

}