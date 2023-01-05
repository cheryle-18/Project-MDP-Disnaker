package com.example.projectdisnaker.admin

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectdisnaker.R
import com.example.projectdisnaker.api.*
import com.example.projectdisnaker.databinding.FragmentAdminPerusahaanBinding
import com.example.projectdisnaker.rv.RVPerusahaanAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminPerusahaanFragment : Fragment() {
    private lateinit var binding: FragmentAdminPerusahaanBinding
    private lateinit var adapterPerusahaan : RVPerusahaanAdapter
    private lateinit var listPerusahaan : List<UserResponseItem>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAdminPerusahaanBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //action bar
        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.setTitle("Disnaker")
        actionBar?.setDisplayHomeAsUpEnabled(false)
        setHasOptionsMenu(false)

        binding.btnTambahPerusahaan.setOnClickListener {
            val fragment = AdminTambahPerusahaanFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_admin, fragment).commit()
        }
        binding.tvBlmAdaPendaftaran.visibility = View.GONE

        initData()
    }
    fun initData(){
        //retrofit call
        var client = ApiConfiguration.getApiService().getAllPerusahaan()
        client.enqueue(object: Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if(response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody!=null){
                        if(responseBody.userResponse!=null){
                            if(responseBody.userResponse.size > 0){
                                listPerusahaan = (responseBody.userResponse as List<UserResponseItem>?)!!
                                binding.rvPerusahaanAdmin.visibility = View.VISIBLE
                                binding.avLoading.visibility = View.GONE
                                initRV()
                            }
                            else{
                                binding.tvBlmAdaPendaftaran.visibility = View.VISIBLE
                            }
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

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.d("Error Frag Admin", "${t.message}")
            }
        })
    }

    fun initRV(){
        adapterPerusahaan = RVPerusahaanAdapter(requireContext(), listPerusahaan){
            idx ->
            val fragment = AdminDetailPerusahaanFragment()
            val bundle = Bundle()
            bundle.putParcelable("perusahaan", listPerusahaan.get(idx)!!)
            fragment.arguments = bundle
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_admin, fragment).commit()
        }
        binding.rvPerusahaanAdmin.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvPerusahaanAdmin.adapter = adapterPerusahaan
    }
}