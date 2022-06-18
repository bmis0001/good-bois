package com.reece.goodbois.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.reece.goodbois.R
import com.reece.goodbois.databinding.FragmentDoggieSearchBinding


class DoggieSearchFragment : Fragment() {

    private lateinit var binding: FragmentDoggieSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentDoggieSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

}