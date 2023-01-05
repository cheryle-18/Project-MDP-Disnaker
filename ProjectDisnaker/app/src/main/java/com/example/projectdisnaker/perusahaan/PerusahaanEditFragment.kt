package com.example.projectdisnaker.perusahaan

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectdisnaker.R
import com.example.projectdisnaker.api.*
import com.example.projectdisnaker.databinding.FragmentPerusahaanEditBinding
import com.example.projectdisnaker.rv.RVTambahSyaratAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PerusahaanEditFragment : Fragment() {
    private lateinit var binding: FragmentPerusahaanEditBinding
    private var listSyarat:ArrayList<String> = ArrayList()
    private var listKategori:ArrayList<String> = ArrayList()
    private lateinit var syaratAdapter: RVTambahSyaratAdapter
    private lateinit var spinnerAdapter: ArrayAdapter<String>
    private lateinit var currLowongan: LowonganItem
    private lateinit var user: UserResponseItem

    private var mode = "add"
    private var idxEdit = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPerusahaanEditBinding.inflate(inflater, container, false)
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
        user = (activity as PerusahaanActivity).user

        binding.llUbahLowongan.visibility = View.GONE

        //spinner kategori
        spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, listKategori)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerKategoriLowEdit.adapter = spinnerAdapter
        fetchKategori()

        //rv syarat
        syaratAdapter = RVTambahSyaratAdapter(listSyarat, requireContext()){
                idx, mode ->
            if(mode=="edit"){
                var syarat = listSyarat.get(idx)
                binding.etSyaratLowEdit.setText(syarat)
                this.mode = "edit"
                idxEdit = idx
                binding.btnTambahSyaratLowEdit.setImageResource(R.drawable.ic_baseline_edit_24)
            }
            else if(mode=="delete"){
                listSyarat.removeAt(idx)
                syaratAdapter.notifyDataSetChanged()
            }
        }
        binding.rvSyaratLowEdit.adapter = syaratAdapter
        binding.rvSyaratLowEdit.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        //get lowongan & set to et
        currLowongan = requireArguments().getParcelable<LowonganItem>("lowongan")!!
        binding.etNamaLowEdit.setText(currLowongan.nama)
        binding.etKuotaLowEdit.setText(currLowongan.kuota.toString())
        binding.etKeteranganLowEdit.setText(currLowongan.keterangan.toString())
        binding.spinnerKategoriLowEdit.setSelection(spinnerAdapter.getPosition(currLowongan.kategori))
        for(s in currLowongan.syarat!!){
            listSyarat.add(s!!.deskripsi!!)
        }
        syaratAdapter.notifyDataSetChanged()

        binding.btnTambahSyaratLowEdit.setOnClickListener {
            var syarat = binding.etSyaratLowEdit.text.toString()
            if(syarat!=""){
                if(mode=="add"){
                    listSyarat.add(syarat)
                    syaratAdapter.notifyDataSetChanged()
                }
                else if(mode=="edit" && idxEdit!=-1){
                    listSyarat.set(idxEdit, syarat)
                    syaratAdapter.notifyDataSetChanged()
                }
                binding.etSyaratLowEdit.setText("")
                mode = "add"
                idxEdit = -1
                binding.btnTambahSyaratLowEdit.setImageResource(R.drawable.ic_baseline_add_24)
            }
        }

        binding.btnUbahLowongan.setOnClickListener {
            var kategori = binding.spinnerKategoriLowEdit.selectedItem.toString()
            var nama = binding.etNamaLowEdit.text.toString()
            var kuota = binding.etKuotaLowEdit.text.toString()
            var keterangan = binding.etKeteranganLowEdit.text.toString()

            if(kategori!="" && nama!="" && kuota!="" && keterangan!=""){
                if(kuota.toInt()<=0){
                    Toast.makeText(requireActivity(), "Kuota harus lebih dari 0", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                var syaratArr: ArrayList<SyaratItem> = ArrayList()
                for(s in listSyarat){
                    syaratArr.add(SyaratItem(s))
                }

                var lowongan = LowonganItem(keterangan, nama, kuota.toInt(), null,
                    null, null, null, syaratArr)

                var client = ApiConfiguration.getApiService()
                    .updateLowongan(currLowongan.lowonganId!!, kategori, lowongan)
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
                                tvDialog.setText("Berhasil mengubah lowongan.")

                                btnOk.setOnClickListener {
                                    dialog.dismiss()
                                    val fragment = PerusahaanDetailLowonganFragment()
                                    val bundle = Bundle()
                                    bundle.putInt("lowongan_id", currLowongan.lowonganId!!)
                                    fragment.arguments = bundle
                                    requireActivity().supportFragmentManager.beginTransaction()
                                        .replace(R.id.fragment_container_perusahaan, fragment).commit()
                                }
                            }
                            else{
                                Log.e("Edit Low Frag", "${response.message()}")
                                Toast.makeText(requireActivity(), "${response.message()}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                    override fun onFailure(call: Call<LowonganResponse>, t: Throwable) {
                        Log.e("Edit Low Frag", "${t.message}")
                    }
                })
            }
            else{
                Toast.makeText(requireActivity(), "Lengkapi semua data lowongan", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                val dialogBinding = layoutInflater.inflate(R.layout.confirm_dialog, null)
                val dialog = Dialog(requireContext())
                dialog.setContentView(dialogBinding)
                dialog.setCancelable(true)
                dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.show()

                val btnKembali = dialogBinding.findViewById<Button>(R.id.btnConfirmDialog)
                val btnKeluar = dialogBinding.findViewById<Button>(R.id.btnCancelDialog)
                val tvDialog = dialogBinding.findViewById<TextView>(R.id.tvDialogConfirm)
                tvDialog.setText("Keluar tanpa menyimpan perubahan?")

                btnKembali.setOnClickListener {
                    dialog.dismiss()
                }
                btnKeluar.setOnClickListener {
                    dialog.dismiss()
                    val fragment = PerusahaanDetailLowonganFragment()
                    val bundle = Bundle()
                    bundle.putInt("lowongan_id", currLowongan.lowonganId!!)
                    fragment.arguments = bundle
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
                        binding.avLoading.visibility = View.GONE
                        binding.llUbahLowongan.visibility = View.VISIBLE

                        listKategori.clear()
                        var temp = responseBody.kategori!!.toMutableList()
                        for(t in temp){
                            listKategori.add(t.nama!!)
                        }
                        spinnerAdapter.notifyDataSetChanged()

                        if(currLowongan!=null){
                            binding.spinnerKategoriLowEdit.setSelection(spinnerAdapter.getPosition(currLowongan.kategori))
                        }
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