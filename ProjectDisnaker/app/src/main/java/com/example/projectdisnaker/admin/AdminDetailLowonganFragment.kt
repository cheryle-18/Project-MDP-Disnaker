package com.example.projectdisnaker.admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectdisnaker.R
import com.example.projectdisnaker.api.LowonganItem
import com.example.projectdisnaker.api.UserResponseItem
import com.example.projectdisnaker.databinding.FragmentAdminDetailLowonganBinding
import com.example.projectdisnaker.peserta.ProfileFragment
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

        //action bar
        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.setTitle("Lowongan")
        actionBar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)

        lowongan = requireArguments().getParcelable<LowonganItem>("lowongan")!!

        binding.tvNamaLowongan.setText(lowongan.nama)
        binding.tvNamaPerusahaanLow.setText(lowongan.perusahaan)
        binding.tvKategoriLowAdmin.setText(lowongan.kategori)
        binding.tvKuotaLowAdmin.setText("${lowongan.kuota} peserta")
        binding.tvStatusLowAdmin.setText("${if(lowongan.status==0) "Ditutup" else "Aktif"}")
        binding.tvKeteranganLowongan.setText(lowongan.keterangan)

        for(syarat in lowongan.syarat!!){
            syaratLowongan.add(syarat!!.deskripsi!!)
        }
        syaratAdapter = RVSyaratAdapter(syaratLowongan, requireContext())
        binding.rvSyaratLowonganAdmin.adapter = syaratAdapter
        binding.rvSyaratLowonganAdmin.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding.btnLihatPendaftaranAdm.setOnClickListener {
            val fragment = AdminPesertaLowonganFragment()
            val bundle = Bundle()
            if(arguments?.getParcelable<UserResponseItem>("perusahaan") != null){
                //untuk daftar perusahaan
                bundle.putParcelable("perusahaan", arguments?.getParcelable("perusahaan")!!)
            }
            bundle.putParcelable("lowongan", lowongan)
            fragment.arguments = bundle
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_admin, fragment).commit()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                if(arguments?.getParcelable<UserResponseItem>("perusahaan") != null){
                    val fragment = AdminDetailPerusahaanFragment()
                    val bundle = Bundle()
                    bundle.putParcelable("perusahaan", arguments?.getParcelable("perusahaan")!!)
                    fragment.arguments = bundle
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_admin, fragment).commit()
                }
                else {
                    val fragment = AdminLowonganFragment()
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_admin, fragment).commit()
                }
            }
        }
        return true
    }
}