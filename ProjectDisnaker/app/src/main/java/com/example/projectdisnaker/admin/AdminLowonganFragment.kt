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
import com.example.projectdisnaker.api.LowonganItem
import com.example.projectdisnaker.api.LowonganResponse
import com.example.projectdisnaker.databinding.FragmentAdminLowonganBinding
import com.example.projectdisnaker.perusahaan.PerusahaanDetailLowonganFragment
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

        lowonganAdapter = RVLowonganAdapter(listLowongan, requireContext()){
                idx ->
            val fragment = AdminDetailLowonganFragment()
            val bundle = Bundle()
            bundle.putInt("lowongan_id", listLowongan.get(idx)!!.lowonganId!!)
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
                        listLowongan.clear()
                        listLowongan.addAll(responseBody.lowongan!!.toMutableList())
                        lowonganAdapter.notifyDataSetChanged()
                    }
                }
                else{
                    Log.e("Perus Lowongan Fragment", "${response.message()}")
                    Toast.makeText(requireActivity(), "${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<LowonganResponse>, t: Throwable) {
                Log.e("Perus Lowongan Fragment", "${t.message}")
                Toast.makeText(requireActivity(), "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}