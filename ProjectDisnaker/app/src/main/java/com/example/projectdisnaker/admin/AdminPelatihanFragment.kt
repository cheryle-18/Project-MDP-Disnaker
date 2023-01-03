package com.example.projectdisnaker.admin

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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

        //action bar
        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.setTitle("Disnaker")
        actionBar?.setDisplayHomeAsUpEnabled(false)
        setHasOptionsMenu(false)

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
        var client = ApiConfiguration.getApiService().getPelatihan("admin")
        client.enqueue(object: Callback<PelatihanResponse> {

            override fun onResponse(call: Call<PelatihanResponse>, response: Response<PelatihanResponse>) {
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

            override fun onFailure(call: Call<PelatihanResponse>, t: Throwable) {
                Log.d("Error Frag Admin", "${t.message}")
            }
        })
    }


    fun initRV(){
        adapterPelatihan = RVPelatihanAdapter(requireActivity(), listPelatihan, R.layout.pelatihan_list_item){
                idx ->
            val fragment = AdminDetailPelatihanFragment()
            val bundle = Bundle()
            bundle.putParcelable("pelatihan", listPelatihan.get(idx)!!)
            fragment.arguments = bundle
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment_container_admin, fragment).commit()
        }
        binding.rvPelatihanAdmin.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvPelatihanAdmin.adapter = adapterPelatihan
    }
}