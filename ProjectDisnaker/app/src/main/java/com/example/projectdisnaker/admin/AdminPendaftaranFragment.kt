package com.example.projectdisnaker.admin

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectdisnaker.R
import com.example.projectdisnaker.api.*
import com.example.projectdisnaker.databinding.FragmentAdminPendaftaranBinding
import com.example.projectdisnaker.rv.RVPendaftaranAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminPendaftaranFragment : Fragment() {
    private lateinit var binding: FragmentAdminPendaftaranBinding
    private lateinit var adapterPendaftaran : RVPendaftaranAdapter
    private lateinit var listPelatihan : List<PendaftaranPelatihanItem>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAdminPendaftaranBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
    }

    fun initData(){
        //retrofit call
        var client = ApiConfiguration.getApiService().getPendaftaranPelatihan()
        client.enqueue(object: Callback<PendaftaranResponse> {
            override fun onResponse(call: Call<PendaftaranResponse>,response: Response<PendaftaranResponse>) {
                if(response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody!=null){
                        listPelatihan = responseBody.pelatihan!!
                        initRV()
                    }
                    else{
                        println("${response.message()}")
                    }
                }
                else{
                    Log.d("Error Frag Admin",response.toString())
                }
            }

            override fun onFailure(call: Call<PendaftaranResponse>, t: Throwable) {
                Log.d("Error Frag Admin", "${t.message}")
            }
        })
    }


    fun initRV(){
        adapterPendaftaran = RVPendaftaranAdapter(requireActivity(), listPelatihan, R.layout.pendaftaran_list_item)
        binding.rvPendaftaranAdmin.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvPendaftaranAdmin.adapter = adapterPendaftaran
    }
}