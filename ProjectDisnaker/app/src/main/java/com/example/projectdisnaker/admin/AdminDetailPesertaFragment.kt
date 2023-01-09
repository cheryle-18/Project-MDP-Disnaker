package com.example.projectdisnaker.admin

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.example.projectdisnaker.R
import com.example.projectdisnaker.api.LowonganItem
import com.example.projectdisnaker.api.PelatihanItem
import com.example.projectdisnaker.api.PesertaPendaftaranItem
import com.example.projectdisnaker.api.UserResponseItem
import com.example.projectdisnaker.databinding.FragmentAdminDetailPesertaBinding
import com.squareup.picasso.Picasso

class AdminDetailPesertaFragment : Fragment() {
    private lateinit var binding: FragmentAdminDetailPesertaBinding
    private lateinit var peserta: PesertaPendaftaranItem
    private lateinit var user: UserResponseItem
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
        actionBar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)

        lowongan = arguments?.getParcelable<LowonganItem>("lowongan")
        pelatihan = arguments?.getParcelable<PelatihanItem>("pelatihan")

        if(requireArguments().getString("type") != null){
            peserta = requireArguments().getParcelable<PesertaPendaftaranItem>("peserta")!!
            type = requireArguments().getString("type")!!

            if(type=="lowongan"){
                actionBar?.setTitle("Lowongan")

                binding.tvStatDaftarDet.visibility = View.GONE
                binding.divider32.visibility = View.GONE
                binding.tvStatLulusDet.visibility = View.GONE
                binding.layoutTahapPelatihan.visibility = View.GONE
                binding.layoutStatusPelatihan.visibility = View.GONE
            }
            else if(type=="pelatihan"){
                actionBar?.setTitle("Pelatihan")

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

                binding.tvStatDaftarDet.setText(status)
                binding.tvStatLulusDet.setText(status2)
            }

            binding.tvNamaPesertaDetAdmin.setText(peserta.nama)
            binding.tvUsernameDetAdmin.setText(peserta.username)
            binding.tvUsiaDetAdmin.setText("${peserta.usia} tahun")
            binding.tvTglLahirDetAdmin.setText(peserta.tglLahir)
            binding.tvPendidikanDetAdmin.setText(peserta.pendidikan)
            binding.tvJurusanDetAdmin.setText(peserta.nama)
            binding.tvNilaiDetAdmin.setText(if(peserta.nilai!=null) peserta.nilai.toString() else "")
            binding.tvEmailDetAdmin.setText(peserta.email)
            binding.tvTelpDetAdmin.setText(peserta.telp)

            try {
                Picasso.get()
                    .load("http://10.0.2.2:8000/gudang/images/${peserta.ijazah}")
//                    .load("http://192.168.100.7:8000/gudang/images/${peserta.ijazah}")
                    .placeholder(R.drawable.ijazah_template)
                    .into(binding.ivIjazahAdmin)
            }catch (e:Error){
                binding.ivIjazahAdmin.setImageResource(R.drawable.ijazah_template)
                Log.e("ERROR_GET_PICTURE",e.message.toString())
            }
        }
        else{//semua peserta admin
            actionBar?.setTitle("Peserta")

            user = arguments?.getParcelable<UserResponseItem>("user")!!
            binding.layoutTahapPelatihan.visibility = View.GONE
            binding.layoutStatusPelatihan.visibility = View.GONE

            binding.tvNamaPesertaDetAdmin.setText(user.nama)
            binding.tvUsernameDetAdmin.setText(user.username)
            binding.tvUsiaDetAdmin.setText("${user.usia} tahun")
            binding.tvTglLahirDetAdmin.setText(user.tglLahir)
            binding.tvPendidikanDetAdmin.setText(user.pendidikan)
            binding.tvJurusanDetAdmin.setText(user.jurusan)
            binding.tvNilaiDetAdmin.setText(if(user.nilai!=null) user.nilai.toString() else "")
            binding.tvEmailDetAdmin.setText(user.email)
            binding.tvTelpDetAdmin.setText(user.telp)

            try {
                Picasso.get()
                    .load("http://10.0.2.2:8000/gudang/images/${user.ijazah}")
//                    .load("http://192.168.100.7:8000/gudang/images/${user.ijazah}")
                    .placeholder(R.drawable.ijazah_template)
                    .into(binding.ivIjazahAdmin)
            }catch (e:Error){
                binding.ivIjazahAdmin.setImageResource(R.drawable.ijazah_template)
                Log.e("ERROR_GET_PICTURE",e.message.toString())
            }
        }

        requireActivity().onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if(requireArguments().getString("type") == null){
                        val fragment = AdminPesertaFragment()
                        val bundle = Bundle() //daftar peserta
                        fragment.arguments = bundle
                        requireActivity().supportFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container_admin, fragment).commit()
                    }
                    else if(type=="lowongan"){
                        val fragment = AdminPesertaLowonganFragment()
                        val bundle = Bundle()
                        if(arguments?.getParcelable<UserResponseItem>("perusahaan") != null){
                            //kalau kembali ke daftar perusahaan
                            bundle.putParcelable("perusahaan", arguments?.getParcelable("perusahaan")!!)
                        }
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
            })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                if(requireArguments().getString("type") == null){
                    val fragment = AdminPesertaFragment()
                    val bundle = Bundle() //daftar peserta
                    fragment.arguments = bundle
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_admin, fragment).commit()
                }
                else if(type=="lowongan"){
                    val fragment = AdminPesertaLowonganFragment()
                    val bundle = Bundle()
                    if(arguments?.getParcelable<UserResponseItem>("perusahaan") != null){
                        //kalau kembali ke daftar perusahaan
                        bundle.putParcelable("perusahaan", arguments?.getParcelable("perusahaan")!!)
                    }
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