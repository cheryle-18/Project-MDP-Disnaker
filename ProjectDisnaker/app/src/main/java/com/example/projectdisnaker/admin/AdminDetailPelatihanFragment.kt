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
import com.example.projectdisnaker.api.PelatihanItem
import com.example.projectdisnaker.databinding.FragmentAdminDetailPelatihanBinding
import com.example.projectdisnaker.rv.RVSyaratAdapter

class AdminDetailPelatihanFragment : Fragment() {
    private lateinit var binding: FragmentAdminDetailPelatihanBinding
    private lateinit var pelatihan: PelatihanItem
    private var syaratPelatihan: ArrayList<String> = ArrayList()
    private lateinit var syaratAdapter: RVSyaratAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAdminDetailPelatihanBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //action bar
        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.setTitle("Pelatihan")
        actionBar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)

        pelatihan = requireArguments().getParcelable<PelatihanItem>("pelatihan")!!

        binding.tvNamaDetailAdmin.setText(pelatihan.nama)
        binding.tvKategoriDetailAdmin.setText(pelatihan.kategori)
        binding.tvKuotaDetailAdmin.setText("${pelatihan.kuota} peserta")
        binding.tvDurasiDetailAdmin.setText("${pelatihan.durasi} hari")
        binding.tvMinPendidikanAdmin.setText(pelatihan.pendidikan)
        binding.tvStatusPelatihanAdmin.setText("${if(pelatihan.status==0) "Ditutup" else "Aktif"}")
        binding.tvKetPelatihanAdmin.setText(pelatihan.keterangan)

        for(syarat in pelatihan.syarat!!){
            syaratPelatihan.add(syarat!!.deskripsi!!)
        }
        syaratAdapter = RVSyaratAdapter(syaratPelatihan, requireContext())
        binding.rvSyaratPelatihanAdmin.adapter = syaratAdapter
        binding.rvSyaratPelatihanAdmin.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)


        binding.btnEditPelatihan.setOnClickListener {
            val fragment = AdminEditPelatihanFragment()
            val bundle = Bundle()
            bundle.putParcelable("pelatihan", pelatihan)
            fragment.arguments = bundle
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment_container_admin, fragment).commit()
        }

        binding.btnLihatPeserta.setOnClickListener {
            val fragment = AdminPesertaPelatihanFragment()
            val bundle = Bundle()
            bundle.putParcelable("pelatihan", pelatihan)
            fragment.arguments = bundle
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment_container_admin, fragment).commit()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                val fragment = AdminPelatihanFragment()
                val bundle = Bundle()
                bundle.putParcelable("pelatihan", pelatihan)
                fragment.arguments = bundle
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_admin, fragment).commit()
            }
        }
        return true
    }
}