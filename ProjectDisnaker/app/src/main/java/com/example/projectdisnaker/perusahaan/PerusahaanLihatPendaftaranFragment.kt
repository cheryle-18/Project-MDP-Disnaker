package com.example.projectdisnaker.perusahaan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.projectdisnaker.R
import com.example.projectdisnaker.api.LowonganItem
import com.example.projectdisnaker.api.UserResponseItem
import com.example.projectdisnaker.databinding.FragmentPerusahaanLihatPendaftaranBinding

class PerusahaanLihatPendaftaranFragment : Fragment() {
    private lateinit var binding: FragmentPerusahaanLihatPendaftaranBinding
    private var listPendaftar:ArrayList<UserResponseItem> = ArrayList()
    private lateinit var currLowongan: LowonganItem

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPerusahaanLihatPendaftaranBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currLowongan = requireArguments().getParcelable<LowonganItem>("lowongan")!!

        binding.ivBackPendaftaranLow.setOnClickListener {
            val fragment = PerusahaanDetailLowonganFragment()
            val bundle = Bundle()
            bundle.putInt("lowongan_id", currLowongan.lowonganId!!)
            fragment.arguments = bundle
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_perusahaan, fragment).commit()
        }
    }
}