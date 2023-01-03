package com.example.projectdisnaker.peserta

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
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
import com.example.projectdisnaker.api.PelatihanItem
import com.example.projectdisnaker.databinding.FragmentDetailPelatihanBinding
import com.example.projectdisnaker.rv.RVSyaratAdapter

class DetailPelatihanFragment : Fragment() {
    private lateinit var binding: FragmentDetailPelatihanBinding
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
        binding = FragmentDetailPelatihanBinding.inflate(inflater, container, false)
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
        binding.linearLayout4.bringToFront()

        pelatihan = requireArguments().getParcelable<PelatihanItem>("pelatihan")!!
        binding.tvNamaDetail.setText(pelatihan.nama)
        binding.tvKategoriDetail.setText(pelatihan.kategori)
        binding.tvKuotaDetail.setText(pelatihan.kuota.toString())
        binding.tvDurasiDetail.setText(pelatihan.durasi.toString())
        binding.tvMinPendidikan.setText(pelatihan.pendidikan)
        binding.tvKeterangan.setText(pelatihan.keterangan)

        for(syarat in pelatihan.syarat!!){
            syaratPelatihan.add(syarat!!.deskripsi!!)
        }

        for(peluang in pelatihan.peluang!!){
            peluangPelatihan.add(peluang!!.nama!!)
        }
        syaratAdapter = RVSyaratAdapter(syaratPelatihan, requireContext())
        binding.rvSyaratPelatihan.adapter = syaratAdapter
        binding.rvSyaratPelatihan.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        peluangAdapter = RVSyaratAdapter(peluangPelatihan, requireContext())
        binding.rvPeluangKerja.adapter = peluangAdapter
        binding.rvPeluangKerja.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)


        binding.btnDaftarPelatihan.setOnClickListener {
            val dialogBinding = layoutInflater.inflate(R.layout.confirm_dialog, null)
            val dialog = Dialog(requireContext())
            dialog.setContentView(dialogBinding)
            dialog.setCancelable(true)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()

            val btnConfirm = dialogBinding.findViewById<Button>(R.id.btnConfirmDialog)
            val btnCancel = dialogBinding.findViewById<Button>(R.id.btnCancelDialog)
            val tvDialog = dialogBinding.findViewById<TextView>(R.id.tvDialogConfirm)

            tvDialog.setText("Apakah anda yakin ingin mendaftar pelatihan?")
            btnCancel.setText("Tidak")
            btnConfirm.setText("Ya")

            btnCancel.setOnClickListener {
                dialog.dismiss()
            }
            btnConfirm.setOnClickListener {
                dialog.dismiss()
//                if(pendaftaran.status_pendaftaran==0){
//                    if(binding.tvTglWawancaraDet.text.toString() == "-"){
//                        Toast.makeText(requireContext(), "Harap Masukkan Tanggal Wawancara!", Toast.LENGTH_SHORT).show()
//                    }
//                }
//                else{
////                var client = ApiConfiguration.getApiService().tutupLowongan(lowonganId)
////                client.enqueue(object: Callback<LowonganResponse> {
////                    override fun onResponse(call: Call<LowonganResponse>, response: Response<LowonganResponse>){
////                        if(response.isSuccessful){
////                            val responseBody = response.body()
////                            if(responseBody!=null){
////                                fetchCurrentLowongan()
////                            }
////                        }
////                        else{
////                            Log.e("", "${response.message()}")
////                            Toast.makeText(requireActivity(), "${response.message()}", Toast.LENGTH_SHORT).show()
////                        }
////                    }
////                    override fun onFailure(call: Call<LowonganResponse>, t: Throwable) {
////                        Log.e("", "${t.message}")
////                        Toast.makeText(requireActivity(), "${t.message}", Toast.LENGTH_SHORT).show()
////                    }
////                })
//                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                val fragment = PelatihanFragment()
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment).commit()
            }
        }
        return true
    }
}