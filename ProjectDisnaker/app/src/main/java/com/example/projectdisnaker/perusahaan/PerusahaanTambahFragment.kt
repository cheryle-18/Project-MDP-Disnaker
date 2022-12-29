package com.example.projectdisnaker.perusahaan

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.PopupMenu
import android.widget.Toast
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

        user = (activity as PerusahaanActivity).user

        //spinner kategori
        spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, listKategori)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerKategoriLow.adapter = spinnerAdapter
        fetchKategori()

        //rv syarat
        syaratAdapter = RVTambahSyaratAdapter(listSyarat, requireContext()){
            idx ->
            val popup = PopupMenu(context, view)
            popup.setOnMenuItemClickListener {
                if(it.itemId == R.id.menu_ubah){
                    var syarat = listSyarat.get(idx)
                    binding.etSyaratLowongan.setText(syarat)
                    mode = "edit"
                    idxEdit = idx
                    binding.btnTambahSyaratLow.setImageResource(R.drawable.ic_baseline_edit_24)
                }
                else if(it.itemId == R.id.menu_hapus){
                    listSyarat.removeAt(idx)
                    syaratAdapter.notifyDataSetChanged()
                }
                return@setOnMenuItemClickListener true
            }
            val inflater = popup.menuInflater
            inflater.inflate(R.menu.syarat_popup_menu, popup.menu)
            popup.show()
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
                var syaratArr = ArrayList<SyaratItem>()
                for(s in listSyarat){
                    syaratArr.add(SyaratItem(s))
                }

                var lowongan = LowonganItem(keterangan, nama, kuota.toInt(), null,
                    null, null, syaratArr)

                var client = ApiConfiguration.getApiService()
                    .insertLowongan(kategori, user.perusahaanId!!, lowongan)
                client.enqueue(object: Callback<LowonganResponse> {
                    override fun onResponse(call: Call<LowonganResponse>, response: Response<LowonganResponse>) {
                        if(response.isSuccessful){
                            val responseBody = response.body()
                            if(responseBody!=null){
                                Toast.makeText(requireActivity(), "Berhasil tambah lowongan", Toast.LENGTH_SHORT).show()
                                val fragment = PerusahaanLowonganFragment()
                                requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment_container_perusahaan, fragment).commit()
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
        }
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