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
import com.example.projectdisnaker.api.ApiConfiguration
import com.example.projectdisnaker.api.PelatihanItem
import com.example.projectdisnaker.api.PelatihanResponse
import com.example.projectdisnaker.api.UserResponse
import com.example.projectdisnaker.databinding.FragmentAdminPelatihanBinding
import com.example.projectdisnaker.rv.RVPelatihanAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminPelatihanFragment : Fragment() {
    private lateinit var binding: FragmentAdminPelatihanBinding
    private lateinit var adapterPelatihan : RVPelatihanAdapter
    private lateinit var listPelatihan : List<PelatihanItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listPelatihan = listOf()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAdminPelatihanBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        binding.btnTambahPelatihan.setOnClickListener {
            val fragment = AdminTambahPelatihanFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment_container_admin, fragment).commit()
        }
    }

    fun initData(){
       //retrofit call
        var client = ApiConfiguration.getApiService().getPelatihan()
        client.enqueue(object: Callback<PelatihanResponse> {

            override fun onResponse(call: Call<PelatihanResponse>, response: Response<PelatihanResponse>) {
                if(response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody!=null){
                        listPelatihan = responseBody.pelatihan!!
                        initRV()
                        Toast.makeText(activity,responseBody.toString(), Toast.LENGTH_SHORT).show()
                    }
                    else{
                        println("${response.message()}")
                        Toast.makeText(activity,response.message(), Toast.LENGTH_SHORT).show()
                    }
                }
                else{
                    Log.d("Error Frag Admin",response.toString())
                    Toast.makeText(activity,response.toString(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<PelatihanResponse>, t: Throwable) {
                Log.d("Error Frag Admin", "${t.message}")
                Toast.makeText(activity,t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }


    fun initRV(){
        adapterPelatihan = RVPelatihanAdapter(requireActivity(), listPelatihan, R.layout.pelatihan_list_item)
        binding.rvPelatihanAdmin.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvPelatihanAdmin.adapter = adapterPelatihan
    }
}