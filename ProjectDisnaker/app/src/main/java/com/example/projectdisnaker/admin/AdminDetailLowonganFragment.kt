package com.example.projectdisnaker.admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectdisnaker.R
import com.example.projectdisnaker.api.LowonganItem
import com.example.projectdisnaker.databinding.FragmentAdminDetailLowonganBinding
import com.example.projectdisnaker.rv.RVSyaratAdapter

class AdminDetailLowonganFragment : Fragment() {
    private lateinit var binding: FragmentAdminDetailLowonganBinding
    private lateinit var lowongan: LowonganItem
    private var syaratLowongan: ArrayList<String> = ArrayList()
    private lateinit var syaratAdapter: RVSyaratAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAdminDetailLowonganBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lowongan = requireArguments().getParcelable<LowonganItem>("lowongan")!!

        binding.tvNamaLowongan.setText(lowongan.nama)
        binding.tvNamaPerusahaanLow.setText(lowongan.perusahaan)
        binding.tvKategoriLowAdmin.setText("Kategori: ${lowongan.kategori}")
        binding.tvKuotaLowAdmin.setText("Kuota: ${lowongan.kuota}")
        binding.tvStatusLowAdmin.setText("Status: ${if(lowongan.status==0) "Ditutup" else "Aktif"}")
        binding.tvKeteranganLowongan.setText(lowongan.keterangan)

        for(syarat in lowongan.syarat!!){
            syaratLowongan.add(syarat!!.deskripsi!!)
        }
        syaratAdapter = RVSyaratAdapter(syaratLowongan, requireContext())
        binding.rvSyaratLowonganAdmin.adapter = syaratAdapter
        binding.rvSyaratLowonganAdmin.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding.ivBackDetailLow.setOnClickListener {
            val fragment = AdminLowonganFragment()
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment_container_admin, fragment).commit()
        }

        binding.btnLihatPendaftaranAdm.setOnClickListener {

        }
    }
}