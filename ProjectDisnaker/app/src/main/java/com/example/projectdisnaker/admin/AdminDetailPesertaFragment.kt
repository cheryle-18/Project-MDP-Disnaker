package com.example.projectdisnaker.admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.projectdisnaker.R
import com.example.projectdisnaker.api.LowonganItem
import com.example.projectdisnaker.api.PelatihanItem
import com.example.projectdisnaker.api.PesertaPendaftaranItem
import com.example.projectdisnaker.databinding.FragmentAdminDetailPesertaBinding

class AdminDetailPesertaFragment : Fragment() {
    private lateinit var binding: FragmentAdminDetailPesertaBinding
    private lateinit var peserta: PesertaPendaftaranItem
    private var lowongan: LowonganItem? = null
    private var pelatihan: PelatihanItem? = null
    private lateinit var type: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAdminDetailPesertaBinding.inflate(inflater, container, false)
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

        lowongan = arguments?.getParcelable<LowonganItem>("lowongan")
        pelatihan = arguments?.getParcelable<PelatihanItem>("pelatihan")
        peserta = requireArguments().getParcelable<PesertaPendaftaranItem>("peserta")!!
        type = requireArguments().getString("type")!!

        if(type=="lowongan"){
            binding.tvStatDaftarDet.visibility = View.GONE
            binding.tvStatLulusDet.visibility = View.GONE
        }
        else if(type=="pelatihan"){
            var status = ""
            var statusInt = peserta.status_pendaftaran
            if(statusInt==0)
                status = "Menunggu"
            else if(statusInt==1)
                status = "Wawancara"
            else if(statusInt==2)
                status = "Dalam Pelatihan"
            else if(statusInt==3)
                status = "Selesai"
            else if(statusInt==4)
                status = "Ditolak"

            var status2 = ""
            var statusInt2 = peserta.status_kelulusan
            if(statusInt2==0)
                status2 = "Menunggu"
            else if(statusInt2==1)
                status2 = "Diterima"
            else if(statusInt2==2)
                status2 = "Ditolak"

            binding.tvStatDaftarDet.setText("Status Pelatihan: ${status}")
            binding.tvStatLulusDet.setText("Status Kelulusan: ${status2}")
        }

        binding.tvNamaPesertaDetAdmin.setText(peserta.nama)
        binding.tvUsiaDetAdmin.setText("Usia: ${peserta.usia} tahun")
        binding.tvTglLahirDetAdmin.setText("Tanggal Lahir: ${peserta.tglLahir}")
        binding.tvPendidikanDetAdmin.setText("Pendidikan Terakhir: ${peserta.pendidikan}")
        binding.tvJurusanDetAdmin.setText("Jurusan: ${peserta.nama}")
        binding.tvNilaiDetAdmin.setText("Nilai: ${peserta.nilai}")
        binding.tvEmailDetAdmin.setText("Email: ${peserta.email}")
        binding.tvTelpDetAdmin.setText("Nomor Telepon: ${peserta.telp}")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                if(type=="lowongan"){
                    val fragment = AdminPesertaLowonganFragment()
                    val bundle = Bundle()
                    bundle.putParcelable("lowongan", lowongan)
                    fragment.arguments = bundle
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_admin, fragment).commit()
                }
                else if(type=="pelatihan"){
                    val fragment = AdminPesertaPelatihanFragment()
                    val bundle = Bundle()
                    bundle.putParcelable("pelatihan", pelatihan)
                    fragment.arguments = bundle
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_admin, fragment).commit()
                }
            }
        }
        return true
    }
}