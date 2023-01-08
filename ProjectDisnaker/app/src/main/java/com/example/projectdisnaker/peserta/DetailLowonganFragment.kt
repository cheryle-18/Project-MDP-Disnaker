package com.example.projectdisnaker.peserta

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
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectdisnaker.R
import com.example.projectdisnaker.api.ApiConfiguration
import com.example.projectdisnaker.api.LowonganItem
import com.example.projectdisnaker.api.LowonganResponse
import com.example.projectdisnaker.api.UserResponseItem
import com.example.projectdisnaker.databinding.FragmentDetailLowonganBinding
import com.example.projectdisnaker.perusahaan.PerusahaanActivity
import com.example.projectdisnaker.perusahaan.PerusahaanLowonganFragment
import com.example.projectdisnaker.rv.RVSyaratAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailLowonganFragment : Fragment() {
    private lateinit var binding: FragmentDetailLowonganBinding
    private lateinit var lowongan: LowonganItem
    private var lowonganId: Int = -1
    private var syaratLowongan: ArrayList<String> = ArrayList()
    private lateinit var syaratAdapter: RVSyaratAdapter
    private lateinit var user: UserResponseItem

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailLowonganBinding.inflate(inflater, container, false)
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
        binding.linearLayout4.bringToFront()
        user = (activity as HomeActivity).user

        binding.llDetailLowongan.visibility = View.GONE

        syaratAdapter = RVSyaratAdapter(syaratLowongan, requireContext())
        binding.rvSyaratLowongan.adapter = syaratAdapter
        binding.rvSyaratLowongan.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        lowonganId = requireArguments().getInt("lowongan_id")
        fetchCurrentLowongan()

        binding.btnDaftarLowongan.setOnClickListener {
            val dialogBinding = layoutInflater.inflate(R.layout.confirm_dialog, null)
            val dialog = Dialog(requireContext())
            dialog.setContentView(dialogBinding)
            dialog.setCancelable(true)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()

            val btnConfirm = dialogBinding.findViewById<Button>(R.id.btnCancelDialog)
            val btnCancel = dialogBinding.findViewById<Button>(R.id.btnConfirmDialog)
            val tvDialog = dialogBinding.findViewById<TextView>(R.id.tvDialogConfirm)
            btnConfirm.setText("Daftar")
            btnConfirm.setBackgroundColor(resources.getColor(R.color.green_900))
            btnCancel.setText("Kembali")
            btnCancel.setBackgroundColor(resources.getColor(R.color.green_500))
            tvDialog.setText("Daftar pada lowongan ${lowongan.nama}? Anda tidak dapat membatalkan pendaftaran.")

            btnCancel.setOnClickListener {
                dialog.dismiss()
            }
            btnConfirm.setOnClickListener {
                dialog.dismiss()
                var client = ApiConfiguration.getApiService().daftarLowongan(user.apiKey!!, lowonganId)
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
                                tvDialog.setText("Berhasil mendaftar pada lowongan.")

                                btnOk.setOnClickListener {
                                    dialog.dismiss()
                                    val fragment = LowonganFragment()
                                    requireActivity().supportFragmentManager.beginTransaction()
                                        .replace(R.id.fragment_container, fragment).commit()
                                }
                            }
                        }
                        else{
                            Log.e("Det Low Frag", "${response.message()}")
                            Toast.makeText(requireActivity(), "${response.message()}", Toast.LENGTH_SHORT).show()
                        }
                    }
                    override fun onFailure(call: Call<LowonganResponse>, t: Throwable) {
                        Log.e("Det Low Frag", "${t.message}")
                        Toast.makeText(requireActivity(), "${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                val fragment = LowonganFragment()
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment).commit()
            }
        }
        return true
    }

    private fun fillDetails(){
        binding.tvNamaKerja.setText(lowongan.nama)
        binding.tvNamaPerusahaan.setText(lowongan.perusahaan)
        binding.tvKategoriKerja.setText(lowongan.kategori)
        binding.tvKuotaKerja.setText("${lowongan.kuota!! - lowongan.pendaftaran!!} peserta")
        binding.tvKeterangan.setText(lowongan.keterangan)

        if(lowongan.sdhDaftar==0){
            binding.btnDaftarLowongan.setText("Anda telah mendaftar")
            binding.btnDaftarLowongan.isEnabled = false
        }

        syaratLowongan.clear()
        for(syarat in lowongan.syarat!!){
            syaratLowongan.add(syarat!!.deskripsi!!)
        }
        syaratAdapter.notifyDataSetChanged()
    }

    private fun fetchCurrentLowongan(){
        var client = ApiConfiguration.getApiService().getPesertaDetLow(lowonganId, user.pesertaId!!)
        client.enqueue(object: Callback<LowonganResponse> {
            override fun onResponse(call: Call<LowonganResponse>, response: Response<LowonganResponse>){
                if(response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody!=null){
                        binding.avLoading.visibility = View.GONE
                        binding.llDetailLowongan.visibility = View.VISIBLE

                        lowongan = responseBody.currLowongan!!
                        fillDetails()
                    }
                }
                else{
                    Log.e("Det Low Frag", "${response.message()}")
                    Toast.makeText(requireActivity(), "${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<LowonganResponse>, t: Throwable) {
                Log.e("Det Low Frag", "${t.message}")
                Toast.makeText(requireActivity(), "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}