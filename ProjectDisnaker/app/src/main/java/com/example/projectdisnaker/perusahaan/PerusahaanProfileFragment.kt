package com.example.projectdisnaker.perusahaan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.projectdisnaker.R
import com.example.projectdisnaker.databinding.FragmentPerusahaanProfileBinding

class PerusahaanProfileFragment : Fragment() {
    private lateinit var binding: FragmentPerusahaanProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPerusahaanProfileBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.layoutUbahPassPerusahaan.setOnClickListener {
            val fragment = PerusahaanPasswordFragment()
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment_container_perusahaan, fragment).commit()
        }

        binding.layoutLogoutPerusahaan.setOnClickListener {
            requireActivity().finish()
        }
    }
}