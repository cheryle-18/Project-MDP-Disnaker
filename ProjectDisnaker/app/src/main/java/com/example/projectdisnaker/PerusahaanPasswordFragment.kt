package com.example.projectdisnaker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.projectdisnaker.databinding.FragmentPerusahaanPasswordBinding

class PerusahaanPasswordFragment : Fragment() {
    private lateinit var binding: FragmentPerusahaanPasswordBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPerusahaanPasswordBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivBackPassPerusahaan.setOnClickListener {
            val fragment = PerusahaanProfileFragment()
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment_container_perusahaan, fragment).commit()
        }
    }
}