package com.example.projectdisnaker.perusahaan

import android.app.ActionBar
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectdisnaker.R
import com.example.projectdisnaker.api.ApiConfiguration
import com.example.projectdisnaker.api.LowonganItem
import com.example.projectdisnaker.api.LowonganResponse
import com.example.projectdisnaker.databinding.FragmentPerusahaanDetailLowonganBinding
import com.example.projectdisnaker.rv.RVSyaratAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PerusahaanDetailLowonganFragment : Fragment() {
    private lateinit var binding: FragmentPerusahaanDetailLowonganBinding
    private lateinit var lowongan: LowonganItem
    private var lowonganId: Int = -1
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

        //action bar
        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.setTitle("Lowongan")
        actionBar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)

        syaratAdapter = RVSyaratAdapter(syaratLowongan, requireContext())
        binding.rvSyaratLowonganPerus.adapter = syaratAdapter
        binding.rvSyaratLowonganPerus.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

//        lowongan = requireArguments().getParcelable<LowonganItem>("lowongan")!!
        lowonganId = requireArguments().getInt("lowongan_id")
        fetchCurrentLowongan()

        binding.layoutLihatPendaftaran.setOnClickListener {
            val fragment = PerusahaanLihatPendaftaranFragment()
            val bundle = Bundle()
            bundle.putInt("lowongan_id", lowonganId!!)
            fragment.arguments = bundle
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_perusahaan, fragment).commit()
        }

        binding.layoutTutupLowongan.setOnClickListener {
            val dialogBinding = layoutInflater.inflate(R.layout.confirm_dialog, null)
            val dialog = Dialog(requireContext())
            dialog.setContentView(dialogBinding)
            dialog.setCancelable(true)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()

            val btnConfirm = dialogBinding.findViewById<Button>(R.id.btnConfirmDialog)
            val btnCancel = dialogBinding.findViewById<Button>(R.id.btnCancelDialog)
            val tvDialog = dialogBinding.findViewById<TextView>(R.id.tvDialogConfirm)
            if(binding.tvTutupLowongan.text.toString()=="Tutup Lowongan"){
                btnConfirm.setText("Tutup")
                btnCancel.setText("Batal")
                tvDialog.setText("Tutup lowongan?")
            }
            else{ //reopen lowongan
                btnConfirm.setText("Buka")
                btnCancel.setText("Batal")
                tvDialog.setText("Buka lowongan?")
            }

            btnCancel.setOnClickListener {
                dialog.dismiss()
            }
            btnConfirm.setOnClickListener {
                dialog.dismiss()
                var client = ApiConfiguration.getApiService().tutupLowongan(lowonganId)
                client.enqueue(object: Callback<LowonganResponse> {
                    override fun onResponse(call: Call<LowonganResponse>, response: Response<LowonganResponse>){
                        if(response.isSuccessful){
                            val responseBody = response.body()
                            if(responseBody!=null){
                                fetchCurrentLowongan()
                            }
                        }
                        else{
                            Log.e("", "${response.message()}")
                            Toast.makeText(requireActivity(), "${response.message()}", Toast.LENGTH_SHORT).show()
                        }
                    }
                    override fun onFailure(call: Call<LowonganResponse>, t: Throwable) {
                        Log.e("", "${t.message}")
                        Toast.makeText(requireActivity(), "${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }

        binding.layoutUbahLowongan.setOnClickListener {
            val fragment = PerusahaanEditFragment()
            val bundle = Bundle()
            bundle.putParcelable("lowongan", lowongan)
            fragment.arguments = bundle
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_perusahaan, fragment).commit()
        }

        binding.layoutHapusLowngan.setOnClickListener {
            val dialogBinding = layoutInflater.inflate(R.layout.confirm_dialog, null)
            val dialog = Dialog(requireContext())
            dialog.setContentView(dialogBinding)
            dialog.setCancelable(true)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()

            val btnConfirm = dialogBinding.findViewById<Button>(R.id.btnConfirmDialog)
            val btnCancel = dialogBinding.findViewById<Button>(R.id.btnCancelDialog)
            val tvDialog = dialogBinding.findViewById<TextView>(R.id.tvDialogConfirm)
            btnConfirm.setText("Hapus")
            btnCancel.setText("Batal")
            tvDialog.setText("Hapus lowongan? Tindakan ini tidak bisa dibatalkan.")

            btnCancel.setOnClickListener {
                dialog.dismiss()
            }
            btnConfirm.setOnClickListener {
                dialog.dismiss()
                var client = ApiConfiguration.getApiService().deleteLowongan(lowonganId)
                client.enqueue(object: Callback<LowonganResponse> {
                    override fun onResponse(call: Call<LowonganResponse>, response: Response<LowonganResponse>){
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
                                tvDialog.setText("Berhasil menghapus lowongan.")

                                btnOk.setOnClickListener {
                                    dialog.dismiss()
                                    val fragment = PerusahaanLowonganFragment()
                                    requireActivity().supportFragmentManager.beginTransaction()
                                        .replace(R.id.fragment_container_perusahaan, fragment).commit()
                                }
                            }
                        }
                        else{
                            Log.e("", "${response.message()}")
                            Toast.makeText(requireActivity(), "${response.message()}", Toast.LENGTH_SHORT).show()
                        }
                    }
                    override fun onFailure(call: Call<LowonganResponse>, t: Throwable) {
                        Log.e("", "${t.message}")
                        Toast.makeText(requireActivity(), "${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                val fragment = PerusahaanLowonganFragment()
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_perusahaan, fragment).commit()
            }
        }
        return true
    }

    private fun fillDetails(){
        binding.tvNamaLowongan.setText(lowongan.nama)
        binding.tvNamaPerusahaanLow.setText(lowongan.perusahaan)
        binding.tvKategoriLow.setText(lowongan.kategori)
        binding.tvKuotaLow.setText("${lowongan.kuota} peserta")
        binding.tvKeteranganLowongan.setText(lowongan.keterangan)

        var status = lowongan.status
        if(status==0){
            binding.tvStatusLow.setText("Ditutup")
            binding.tvStatusLow.setTextColor(resources.getColor(R.color.red))

            if(lowongan.kuota!!-lowongan.pendaftaran!! <= 0){
                binding.layoutTutupLowongan.visibility = View.GONE
            }
            else{
                binding.tvTutupLowongan.setText("Buka Lowongan")
            }
        }
        else{
            binding.tvStatusLow.setText("Aktif")
            binding.tvStatusLow.setTextColor(resources.getColor(R.color.green_900))
            binding.tvTutupLowongan.setText("Tutup Lowongan")
        }

        syaratLowongan.clear()
        for(syarat in lowongan.syarat!!){
            syaratLowongan.add(syarat!!.deskripsi!!)
        }
        syaratAdapter.notifyDataSetChanged()
    }

    private fun fetchCurrentLowongan(){
        var client = ApiConfiguration.getApiService().getLowongan(lowonganId)
        client.enqueue(object: Callback<LowonganResponse> {
            override fun onResponse(call: Call<LowonganResponse>, response: Response<LowonganResponse>){
                if(response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody!=null){
                        lowongan = responseBody.currLowongan!!
                        fillDetails()
                    }
                }
                else{
                    Log.e("", "${response.message()}")
                    Toast.makeText(requireActivity(), "${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<LowonganResponse>, t: Throwable) {
                Log.e("", "${t.message}")
                Toast.makeText(requireActivity(), "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}