package com.example.projectdisnaker.perusahaan

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
import com.example.projectdisnaker.databinding.FragmentPerusahaanLowonganBinding
import com.example.projectdisnaker.rv.RVLowonganAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PerusahaanLowonganFragment : Fragment() {
    private lateinit var binding: FragmentPerusahaanLowonganBinding
    private var listLowongan: MutableList<LowonganItem?> = arrayListOf()
    private lateinit var lowonganAdapter: RVLowonganAdapter
    private lateinit var user: UserResponseItem

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPerusahaanLowonganBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        user = requireArguments().getParcelable<UserResponseItem>("user")!!

        lowonganAdapter = RVLowonganAdapter(listLowongan, requireContext()){
            idx ->
            val fragment = PerusahaanDetailLowonganFragment()
            val bundle = Bundle()
            bundle.putParcelable("lowongan", listLowongan.get(idx)!!)
            bundle.putInt("lowongan_id", listLowongan.get(idx)!!.lowonganId!!)
            fragment.arguments = bundle
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment_container_perusahaan, fragment).commit()
        }
        binding.rvLowonganPerus.adapter = lowonganAdapter
        binding.rvLowonganPerus.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        fetchData()

        binding.btnToTambahLowongan.setOnClickListener {
            val fragment = PerusahaanTambahFragment()
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment_container_perusahaan, fragment).commit()
        }
    }

    private fun fetchData(){
        var client = ApiConfiguration.getApiService().getPerusLowongan(user.perusahaanId!!)
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