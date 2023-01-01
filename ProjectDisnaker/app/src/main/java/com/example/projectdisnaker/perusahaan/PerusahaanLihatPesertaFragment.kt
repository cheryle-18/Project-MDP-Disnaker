package com.example.projectdisnaker.perusahaan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.projectdisnaker.R
import com.example.projectdisnaker.api.PesertaPendaftaranItem
import com.example.projectdisnaker.databinding.FragmentPerusahaanLihatPesertaBinding

class PerusahaanLihatPesertaFragment : Fragment() {
    private lateinit var binding: FragmentPerusahaanLihatPesertaBinding
    private lateinit var peserta: PesertaPendaftaranItem
    private var lowonganId:Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPerusahaanLihatPesertaBinding.inflate(inflater, container, false)
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

        lowonganId = requireArguments().getInt("lowongan_id")!!
        peserta = requireArguments().getParcelable<PesertaPendaftaranItem>("peserta")!!

        binding.tvNamaPesertaDetail.setText(peserta.nama)
        binding.tvUsiaDetail.setText("${peserta.usia} tahun")
        binding.tvTglLahirDetail.setText(peserta.tglLahir)
        binding.tvPendidikanDetail.setText(peserta.pendidikan)
        binding.tvJurusanDetail.setText(peserta.jurusan)
        binding.tvNilaiDetail.setText(peserta.nilai.toString())
        binding.tvEmailDetail.setText(peserta.email)
        binding.tvTelpDetail.setText(peserta.telp)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                val fragment = PerusahaanLihatPendaftaranFragment()
                val bundle = Bundle()
                bundle.putInt("lowongan_id", lowonganId)
                fragment.arguments = bundle
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_perusahaan, fragment).commit()
            }
        }
        return true
    }
}