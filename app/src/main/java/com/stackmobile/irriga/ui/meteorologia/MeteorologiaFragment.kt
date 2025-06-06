package com.stackmobile.irriga.ui.meteorologia

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.stackmobile.irriga.databinding.FragmentMeteorologiaBinding

class MeteorologiaFragment : Fragment() {

    private lateinit var binding: FragmentMeteorologiaBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
      binding = FragmentMeteorologiaBinding.inflate(inflater, container, false)
        return binding.root
    }

}