package com.example.projectdisnaker.admin

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectdisnaker.R
import com.example.projectdisnaker.api.ApiConfiguration
import com.example.projectdisnaker.api.UserResponse
import com.example.projectdisnaker.api.UserResponseItem
import com.example.projectdisnaker.databinding.FragmentAdminPesertaBinding
import com.example.projectdisnaker.perusahaan.PerusahaanLihatPesertaFragment
import com.example.projectdisnaker.rv.RVPesertaAdapter
import com.example.projectdisnaker.rv.RVPesertaPendaftaranAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminPesertaFragment : Fragment() {
    private lateinit var binding: FragmentAdminPesertaBinding
    private lateinit var listPeserta: List<UserResponseItem>
    private lateinit var pesertaAdapter: RVPesertaAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAdminPesertaBinding.inflate(inflater, container, false)
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

        requireActivity().onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().moveTaskToBack(true)
                }
            })

        binding.rvPesertaAdmin.visibility = View.GONE

        initData()
    }

    fun initData(){
        //retrofit call
        var client = ApiConfiguration.getApiService().getAllPeserta()
        client.enqueue(object: Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if(response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody!=null){
                        binding.avLoading.visibility = View.GONE
                        binding.rvPesertaAdmin.visibility = View.VISIBLE

                        listPeserta = (responseBody.userResponse as List<UserResponseItem>?)!!
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

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.d("Error Frag Admin", "${t.message}")
            }
        })
    }

    fun initRV(){
        pesertaAdapter = RVPesertaAdapter(listPeserta, requireContext()){
                idx ->
            val fragment = AdminDetailPesertaFragment()
            val bundle = Bundle()
//            bundle.putInt("lowongan_id", lowonganId)
            bundle.putParcelable("user", listPeserta.get(idx)!!)
            fragment.arguments = bundle
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_admin, fragment).commit()
        }
        binding.rvPesertaAdmin.adapter = pesertaAdapter
        binding.rvPesertaAdmin.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        pesertaAdapter.notifyDataSetChanged()
    }
}