package com.example.projectdisnaker.admin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectdisnaker.R
import com.example.projectdisnaker.api.*
import com.example.projectdisnaker.databinding.FragmentAdminTambahPelatihanBinding
import com.example.projectdisnaker.perusahaan.PerusahaanActivity
import com.example.projectdisnaker.peserta.HomeActivity
import com.example.projectdisnaker.rv.RVPelatihanAdapter
import com.example.projectdisnaker.rv.RVSyaratAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminTambahPelatihanFragment : Fragment() {
    private lateinit var binding: FragmentAdminTambahPelatihanBinding
    private lateinit var listKategori : List<KategoriItem>
    private lateinit var adapterSyarat : RVSyaratAdapter
    private lateinit var listSyarat : ArrayList<String>


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
        loadKategori()
        initRV()
        binding.ivBackTambahPel.setOnClickListener {
            val fragment = AdminPelatihanFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment_container_admin, fragment).commit()
        }
        binding.btnTambahSyaratPelatihan.setOnClickListener {
//            add to syarat list
            listSyarat.add(binding.etSyaratPelatihan.text.toString())
            adapterSyarat.notifyDataSetChanged()
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
        adapterSyarat = RVSyaratAdapter(listSyarat,requireContext())
        binding.rvSyaratPelatihanAdmin.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvSyaratPelatihanAdmin.adapter = adapterSyarat
    }
}