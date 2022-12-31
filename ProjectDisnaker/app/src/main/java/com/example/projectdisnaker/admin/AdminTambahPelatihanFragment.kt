package com.example.projectdisnaker.admin

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectdisnaker.R
import com.example.projectdisnaker.api.*
import com.example.projectdisnaker.databinding.FragmentAdminTambahPelatihanBinding
import com.example.projectdisnaker.perusahaan.PerusahaanActivity
import com.example.projectdisnaker.perusahaan.PerusahaanLowonganFragment
import com.example.projectdisnaker.peserta.HomeActivity
import com.example.projectdisnaker.rv.RVPelatihanAdapter
import com.example.projectdisnaker.rv.RVSyaratAdapter
import com.example.projectdisnaker.rv.RVTambahSyaratAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminTambahPelatihanFragment : Fragment() {
    private lateinit var binding: FragmentAdminTambahPelatihanBinding
    private lateinit var listKategori : List<KategoriItem>
    private lateinit var adapterSyarat : RVTambahSyaratAdapter
    private lateinit var listSyarat : ArrayList<String>

    private var mode = "add"
    private var idxEdit = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listKategori = listOf()
        listSyarat = arrayListOf()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAdminTambahPelatihanBinding.inflate(inflater, container, false)
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

        loadKategori()
        initRV()
        binding.btnTambahSyaratPelatihan.setOnClickListener {

            var syarat = binding.etSyaratPelatihan.text.toString()
            if(syarat!=""){
                if(mode=="add"){
                    listSyarat.add(syarat)
                    adapterSyarat.notifyDataSetChanged()
                }
                else if(mode=="edit" && idxEdit!=-1){
                    listSyarat.set(idxEdit, syarat)
                    adapterSyarat.notifyDataSetChanged()
                }
                binding.etSyaratPelatihan.setText("")
                mode = "add"
                idxEdit = -1
                binding.btnTambahSyaratPelatihan.setImageResource(R.drawable.ic_baseline_add_24)
            }
        }

        binding.btnTambahPel.setOnClickListener {
            var kategori = binding.spinnerKategoriPel.selectedItem.toString()
            var kuota = binding.etKuotaPelatihan.text.toString()
            var durasi = binding.etDurasiPelatihan.text.toString().toInt()
            var min = binding.spinnerPendidikanPel.selectedItem.toString()

            if(kategori!="" && kuota!="" && durasi>0 && min!=""){
                var syaratArr = ArrayList<SyaratItem>()
                for(s in listSyarat){
                    syaratArr.add(SyaratItem(s))
                }


            }
        }
    }

    fun initSpinner(){
        val adapter: ArrayAdapter<KategoriItem> = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            listKategori
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerKategoriPel.adapter = adapter
    }

    fun loadKategori(){
        //fetch from db
        var client = ApiConfiguration.getApiService().getKategori()
        client.enqueue(object: Callback<KategoriResponse> {
            override fun onResponse(call: Call<KategoriResponse>, response: Response<KategoriResponse>) {
                if(response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody!=null){
                        listKategori = responseBody.kategori!!
                        initSpinner()
                    }
                    else{
                        println("${response.message()}")
                    }
                }
                else{
                    Log.d("Error kat pel",response.toString())
                }
            }

            override fun onFailure(call: Call<KategoriResponse>, t: Throwable) {
                Log.d("Error kat pel", "${t.message}")
            }
        })
    }

    fun initRV(){
        adapterSyarat = RVTambahSyaratAdapter(listSyarat,requireContext()){
                idx, mode ->
            if(mode=="edit"){
                var syarat = listSyarat.get(idx)
                binding.etSyaratPelatihan.setText(syarat)
                this.mode = "edit"
                idxEdit = idx
                binding.btnTambahSyaratPelatihan.setImageResource(R.drawable.ic_baseline_edit_24)
            }
            else if(mode=="delete"){
                listSyarat.removeAt(idx)
                adapterSyarat.notifyDataSetChanged()
            }
        }
        binding.rvSyaratPelatihanAdmin.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvSyaratPelatihanAdmin.adapter = adapterSyarat
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                val fragment = AdminPelatihanFragment()
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_admin, fragment).commit()
            }
        }
        return true
    }
}