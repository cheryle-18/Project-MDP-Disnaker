package com.example.projectdisnaker.perusahaan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projectdisnaker.R
import com.example.projectdisnaker.api.UserResponseItem
import com.example.projectdisnaker.databinding.FragmentPerusahaanProfileBinding

class PerusahaanProfileFragment : Fragment() {
    private lateinit var binding: FragmentPerusahaanProfileBinding
    private lateinit var user: UserResponseItem
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

        //action bar
        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.setTitle("Disnaker")
        actionBar?.setDisplayHomeAsUpEnabled(false)
        setHasOptionsMenu(false)
        user = (activity as PerusahaanActivity).user

        binding.layoutUbahPassPerusahaan.setOnClickListener {
            val fragment = PerusahaanPasswordFragment()
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment_container_perusahaan, fragment).commit()
        }

        binding.btnEditPerusahaan.setOnClickListener{
            if (binding.etAlamatPerusahaanProfile.text.toString().isBlank()&&
                    binding.etTelpPerusahaanProfile.text.toString().isBlank()&&
                    binding.etEmailPerusahaanProfile.text.toString().isBlank()){
                Toast.makeText(requireActivity(), "Error", Toast.LENGTH_SHORT).show()
            }
            else{

            }
        }
        binding.layoutLogoutPerusahaan.setOnClickListener {
            requireActivity().finish()
        }
    }
}