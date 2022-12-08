package com.example.projectdisnaker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.projectdisnaker.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.layoutUbahPassword.setOnClickListener {
            val fragment = PasswordFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
        }

        binding.layoutPendidikan.setOnClickListener {
            val fragment = PendidikanFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
        }

        binding.layoutLogout.setOnClickListener{
            requireActivity().finish()
        }
    }
}