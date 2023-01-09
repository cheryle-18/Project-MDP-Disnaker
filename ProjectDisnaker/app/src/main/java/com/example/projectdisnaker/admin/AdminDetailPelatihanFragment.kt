package com.example.projectdisnaker.admin

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectdisnaker.R
import com.example.projectdisnaker.api.*
import com.example.projectdisnaker.databinding.FragmentAdminDetailPelatihanBinding
import com.example.projectdisnaker.rv.RVSyaratAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminDetailPelatihanFragment : Fragment() {
    private lateinit var binding: FragmentAdminDetailPelatihanBinding
    private lateinit var pelatihan: PelatihanItem
    private var syaratPelatihan: ArrayList<String> = ArrayList()
    private lateinit var syaratAdapter: RVSyaratAdapter
    private var peluangPelatihan: ArrayList<String> = ArrayList()
    private lateinit var peluangAdapter: RVSyaratAdapter

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

        for(peluang in pelatihan.peluang!!){
            peluangPelatihan.add(peluang!!.nama!!)
        }
        peluangAdapter = RVSyaratAdapter(peluangPelatihan, requireContext())
        binding.rvPeluangKerjaAdmin.adapter = peluangAdapter
        binding.rvPeluangKerjaAdmin.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)


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

        binding.btnHapusPelatihan.setOnClickListener {

            var client = ApiConfiguration.getApiService().getPesertaPelatihan(pelatihan.pelatihanId!!)
            client.enqueue(object: Callback<PesertaPendaftaranResponse> {
                override fun onResponse(call: Call<PesertaPendaftaranResponse>, response: Response<PesertaPendaftaranResponse>) {
                    if(response.isSuccessful){
                        val responseBody = response.body()
                        if(responseBody!=null){
                            var pendaftar = responseBody.pendaftaran
                            if(pendaftar!!.isEmpty()){
                                val dialogBinding = layoutInflater.inflate(R.layout.confirm_dialog, null)
                                val dialog = Dialog(requireContext())
                                dialog.setContentView(dialogBinding)
                                dialog.setCancelable(true)
                                dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                                dialog.show()

                                val btnConfirm = dialogBinding.findViewById<Button>(R.id.btnConfirmDialog)
                                val btnCancel = dialogBinding.findViewById<Button>(R.id.btnCancelDialog)
                                val tvDialog = dialogBinding.findViewById<TextView>(R.id.tvDialogConfirm)

                                tvDialog.setText("Hapus pelatihan ini?")
                                btnCancel.setText("Tidak")
                                btnConfirm.setText("Ya")

                                btnCancel.background.setTint(Color.rgb(27, 94, 32))
                                btnConfirm.background.setTint(Color.RED)

                                btnCancel.setOnClickListener {
                                    dialog.dismiss()
                                }
                                btnConfirm.setOnClickListener {
                                    dialog.dismiss()
                                    var client = ApiConfiguration.getApiService().deletePelatihan(pelatihan.pelatihanId!!)
                                    client.enqueue(object: Callback<PelatihanResponse> {
                                        override fun onResponse(call: Call<PelatihanResponse>, response: Response<PelatihanResponse>) {
                                            if(response.isSuccessful){
                                                val responseBody = response.body()
                                                if(responseBody!=null){
                                                    val dialogBinding = layoutInflater.inflate(R.layout.success_dialog, null)
                                                    val dialog = Dialog(requireContext())
                                                    dialog.setContentView(dialogBinding)
                                                    dialog.setCancelable(true)
                                                    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                                                    dialog.show()

                                                    val btnOk = dialogBinding.findViewById<Button>(R.id.btOkDialog)
                                                    val tvDialog = dialogBinding.findViewById<TextView>(R.id.tvDialog)
                                                    tvDialog.setText("Berhasil menghapus pelatihan!")

                                                    btnOk.setOnClickListener {
                                                        dialog.dismiss()

                                                    }
                                                    val fragment = AdminPelatihanFragment()
                                                    requireActivity().supportFragmentManager.beginTransaction()
                                                        .replace(R.id.fragment_container_admin, fragment).commit()
                                                }
                                                else{
                                                    println("${response.message()}")
                                                }
                                            }
                                            else{
                                                Log.d("Error Frag Admin",response.toString())
                                            }
                                        }

                                        override fun onFailure(call: Call<PelatihanResponse>, t: Throwable) {
                                            Log.d("Error Frag Admin", "${t.message}")
                                        }
                                    })
                                }

                            }
                            else{
                                Toast.makeText(requireContext(),"Tidak dapat menghapus pelatihan ini karena terdapat pendaftaran!",Toast.LENGTH_SHORT).show()
                            }
                        }
                        else{
                            println("${response.message()}")
                        }
                    }
                    else{
                        Log.d("Error Frag Admin",response.toString())
                    }
                }

                override fun onFailure(call: Call<PesertaPendaftaranResponse>, t: Throwable) {
                    Log.d("Error Frag Admin", "${t.message}")
                }
            })
        }

        requireActivity().onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    val fragment = AdminPelatihanFragment()
                    val bundle = Bundle()
                    bundle.putParcelable("pelatihan", pelatihan)
                    fragment.arguments = bundle
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_admin, fragment).commit()
                }
            })
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