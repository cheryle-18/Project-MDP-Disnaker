package com.example.projectdisnaker.perusahaan

import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectdisnaker.R
import com.example.projectdisnaker.api.LowonganItem
import com.example.projectdisnaker.databinding.FragmentPerusahaanDetailLowonganBinding
import com.example.projectdisnaker.rv.RVSyaratAdapter

class PerusahaanDetailLowonganFragment : Fragment() {
    private lateinit var binding: FragmentPerusahaanDetailLowonganBinding
    private lateinit var lowongan: LowonganItem
    private var syaratLowongan: ArrayList<String> = ArrayList()
    private lateinit var syaratAdapter: RVSyaratAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPerusahaanDetailLowonganBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lowongan = requireArguments().getParcelable<LowonganItem>("lowongan")!!

        binding.tvNamaLowongan.setText(lowongan.nama)
        binding.tvNamaPerusahaanLow.setText(lowongan.perusahaan)
        binding.tvKategoriLow.setText(lowongan.kategori)
        binding.tvKuotaLow.setText("${lowongan.kuota} peserta")
        binding.tvKeteranganLowongan.setText(lowongan.keterangan)

        var status = lowongan.status
        if(status==0){
            binding.tvStatusLow.setText("Ditutup")
            binding.tvStatusLow.setTextColor(resources.getColor(R.color.red))
            binding.layoutTutupLowongan.visibility = View.GONE
        }
        else{
            binding.tvStatusLow.setText("Aktif")
            binding.tvStatusLow.setTextColor(resources.getColor(R.color.green_900))
        }

        for(syarat in lowongan.syarat!!){
            syaratLowongan.add(syarat!!.deskripsi!!)
        }
        syaratAdapter = RVSyaratAdapter(syaratLowongan, requireContext())
        binding.rvSyaratLowonganPerus.adapter = syaratAdapter
        binding.rvSyaratLowonganPerus.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding.ivBackDetailLow.setOnClickListener {
            val fragment = PerusahaanLowonganFragment()
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment_container_perusahaan, fragment).commit()
        }

        binding.layoutUbahLowongan.setOnClickListener {
            val fragment = PerusahaanEditFragment()
            val bundle = Bundle()
            bundle.putParcelable("lowongan", lowongan)
            fragment.arguments = bundle
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment_container_perusahaan, fragment).commit()
        }
    }
}