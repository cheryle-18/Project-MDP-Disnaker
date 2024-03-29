package com.example.projectdisnaker.admin

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectdisnaker.R
import com.example.projectdisnaker.api.ApiConfiguration
import com.example.projectdisnaker.api.LowonganItem
import com.example.projectdisnaker.api.LowonganResponse
import com.example.projectdisnaker.databinding.FragmentAdminLowonganBinding
import com.example.projectdisnaker.perusahaan.PerusahaanDetailLowonganFragment
import com.example.projectdisnaker.peserta.ProfileFragment
import com.example.projectdisnaker.rv.RVLowonganAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminLowonganFragment : Fragment() {
    private lateinit var binding: FragmentAdminLowonganBinding
    private var listLowongan: MutableList<LowonganItem?> = arrayListOf()
    private lateinit var lowonganAdapter: RVLowonganAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAdminLowonganBinding.inflate(inflater, container, false)
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

        binding.rvLowonganAdmin.visibility = View.GONE

        lowonganAdapter = RVLowonganAdapter(listLowongan, requireContext()){
                idx ->
            val fragment = AdminDetailLowonganFragment()
            val bundle = Bundle()
            bundle.putParcelable("lowongan", listLowongan.get(idx)!!)
            fragment.arguments = bundle
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment_container_admin, fragment).commit()
        }
        binding.rvLowonganAdmin.adapter = lowonganAdapter
        binding.rvLowonganAdmin.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        fetchData()
    }

    private fun fetchData(){
        var client = ApiConfiguration.getApiService().getAllLowongan()
        client.enqueue(object: Callback<LowonganResponse> {
            override fun onResponse(call: Call<LowonganResponse>, response: Response<LowonganResponse>){
                if(response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody!=null){
                        binding.avLoading.visibility = View.GONE
                        binding.rvLowonganAdmin.visibility = View.VISIBLE

                        listLowongan.clear()
                        listLowongan.addAll(responseBody.lowongan!!.toMutableList())
                        lowonganAdapter.notifyDataSetChanged()
                    }
                }
                else{
                    Log.e("Perus Lowongan Fragment", "${response.message()}")
                }
            }
            override fun onFailure(call: Call<LowonganResponse>, t: Throwable) {
                Log.e("Error Perus Lowongan Fragment", "${t.message}")
            }
        })
    }
}