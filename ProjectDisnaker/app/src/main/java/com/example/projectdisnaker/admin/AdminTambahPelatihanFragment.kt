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
import com.example.projectdisnaker.R
import com.example.projectdisnaker.api.ApiConfiguration
import com.example.projectdisnaker.api.KategoriItem
import com.example.projectdisnaker.api.KategoriResponse
import com.example.projectdisnaker.api.UserResponse
import com.example.projectdisnaker.databinding.FragmentAdminTambahPelatihanBinding
import com.example.projectdisnaker.perusahaan.PerusahaanActivity
import com.example.projectdisnaker.peserta.HomeActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminTambahPelatihanFragment : Fragment() {
    private lateinit var binding: FragmentAdminTambahPelatihanBinding
    private lateinit var listKategori : List<KategoriItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listKategori = listOf()
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
        binding.ivBackTambahPel.setOnClickListener {
            val fragment = AdminPelatihanFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment_container_admin, fragment).commit()
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
}