package com.example.projectdisnaker.perusahaan

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
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectdisnaker.R
import com.example.projectdisnaker.api.*
import com.example.projectdisnaker.databinding.FragmentPerusahaanTambahBinding
import com.example.projectdisnaker.rv.RVTambahSyaratAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PerusahaanTambahFragment : Fragment() {
    private lateinit var binding: FragmentPerusahaanTambahBinding
    private var listSyarat:ArrayList<String> = ArrayList()
    private var listKategori:ArrayList<KategoriItem> = ArrayList()
    private lateinit var syaratAdapter: RVTambahSyaratAdapter
    private lateinit var spinnerAdapter: ArrayAdapter<KategoriItem>
    private lateinit var user: UserResponseItem

    private var mode = "add"
    private var idxEdit = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPerusahaanTambahBinding.inflate(inflater, container, false)
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

        requireActivity().onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    val fragment = PerusahaanLowonganFragment()
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_perusahaan, fragment).commit()
                }
            })

        user = (activity as PerusahaanActivity).user

        //spinner kategori
        spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, listKategori)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerKategoriLow.adapter = spinnerAdapter
        fetchKategori()

        //rv syarat
        syaratAdapter = RVTambahSyaratAdapter(listSyarat, requireContext()){
            idx, mode ->
            if(mode=="edit"){
                var syarat = listSyarat.get(idx)
                binding.etSyaratLowongan.setText(syarat)
                this.mode = "edit"
                idxEdit = idx
                binding.btnTambahSyaratLow.setImageResource(R.drawable.ic_baseline_edit_24)
            }
            else if(mode=="delete"){
                listSyarat.removeAt(idx)
                syaratAdapter.notifyDataSetChanged()
            }
        }
        binding.rvSyaratLowongan.adapter = syaratAdapter
        binding.rvSyaratLowongan.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding.btnTambahSyaratLow.setOnClickListener {
            var syarat = binding.etSyaratLowongan.text.toString()
            if(syarat!=""){
                if(mode=="add"){
                    listSyarat.add(syarat)
                    syaratAdapter.notifyDataSetChanged()
                }
                else if(mode=="edit" && idxEdit!=-1){
                    listSyarat.set(idxEdit, syarat)
                    syaratAdapter.notifyDataSetChanged()
                }
                binding.etSyaratLowongan.setText("")
                mode = "add"
                idxEdit = -1
                binding.btnTambahSyaratLow.setImageResource(R.drawable.ic_baseline_add_24)
            }
        }

        binding.btnTambahLowongan.setOnClickListener {
            var kategori = binding.spinnerKategoriLow.selectedItem.toString()
            var nama = binding.etNamaLowongan.text.toString()
            var kuota = binding.etKuotaLowongan.text.toString()
            var keterangan = binding.etKeteranganLowongan.text.toString()

            if(kategori!="" && nama!="" && kuota!="" && keterangan!=""){
                if(kuota.toInt() <= 0){
                    Toast.makeText(requireActivity(), "Kuota harus lebih dari 0", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                var syaratArr = ArrayList<SyaratItem>()
                for(s in listSyarat){
                    syaratArr.add(SyaratItem(s))
                }

                var lowongan = LowonganItem(keterangan, nama, kuota.toInt(), null,
                    null, null, null, syaratArr)

                var client = ApiConfiguration.getApiService()
                    .insertLowongan(kategori, user.perusahaanId!!, lowongan)
                client.enqueue(object: Callback<LowonganResponse> {
                    override fun onResponse(call: Call<LowonganResponse>, response: Response<LowonganResponse>) {
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
                                tvDialog.setText("Berhasil menambah lowongan.")

                                btnOk.setOnClickListener {
                                    dialog.dismiss()
                                    val fragment = PerusahaanLowonganFragment()
                                    requireActivity().supportFragmentManager.beginTransaction()
                                        .replace(R.id.fragment_container_perusahaan, fragment).commit()
                                }
                            }
                            else{
                                Log.e("Tambah Low Frag", "${response.message()}")
                            }
                        }
                    }

                    override fun onFailure(call: Call<LowonganResponse>, t: Throwable) {
                        Log.e("Tambah Low Frag", "${t.message}")
                    }
                })
            }
            else{
                Toast.makeText(requireActivity(), "Harap isi semua data", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                if(binding.etNamaLowongan.text.toString()!="" || binding.etKuotaLowongan.text.toString()!=""
                    || binding.etKeteranganLowongan.text.toString()!="" || listSyarat.size>0){
                    val dialogBinding = layoutInflater.inflate(R.layout.confirm_dialog, null)
                    val dialog = Dialog(requireContext())
                    dialog.setContentView(dialogBinding)
                    dialog.setCancelable(true)
                    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    dialog.show()

                    val btnKembali = dialogBinding.findViewById<Button>(R.id.btnConfirmDialog)
                    val btnKeluar = dialogBinding.findViewById<Button>(R.id.btnCancelDialog)
                    val tvDialog = dialogBinding.findViewById<TextView>(R.id.tvDialogConfirm)
                    tvDialog.setText("Keluar tanpa menyimpan perusahaan?")

                    btnKembali.setOnClickListener {
                        dialog.dismiss()
                    }
                    btnKeluar.setOnClickListener {
                        dialog.dismiss()
                        val fragment = PerusahaanLowonganFragment()
                        requireActivity().supportFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container_perusahaan, fragment).commit()
                    }
                }
                else{
                    val fragment = PerusahaanLowonganFragment()
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_perusahaan, fragment).commit()
                }
            }
        }
        return true
    }

    private fun fetchKategori(){
        var client = ApiConfiguration.getApiService().getKategori()
        client.enqueue(object: Callback<KategoriResponse> {
            override fun onResponse(call: Call<KategoriResponse>, response: Response<KategoriResponse>){
                if(response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody!=null){
                        listKategori.clear()
                        listKategori.addAll(responseBody.kategori!!.toMutableList())
                        spinnerAdapter.notifyDataSetChanged()
                    }
                }
                else{
                    Log.e("", "${response.message()}")
                    Toast.makeText(requireActivity(), "${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<KategoriResponse>, t: Throwable) {
                Log.e("", "${t.message}")
                Toast.makeText(requireActivity(), "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}