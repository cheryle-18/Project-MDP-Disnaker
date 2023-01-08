package com.example.projectdisnaker.peserta

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
import com.example.projectdisnaker.api.UserResponseItem
import com.example.projectdisnaker.databinding.FragmentLowonganBinding
import com.example.projectdisnaker.perusahaan.PerusahaanActivity
import com.example.projectdisnaker.rv.RVLowonganAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LowonganFragment : Fragment() {
    private lateinit var binding: FragmentLowonganBinding
    private var listLowongan: MutableList<LowonganItem?> = arrayListOf()
    private lateinit var lowonganAdapter: RVLowonganAdapter
    private lateinit var user: UserResponseItem

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLowonganBinding.inflate(inflater, container, false)
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

        user = (activity as HomeActivity).user

        binding.rvLowongan.visibility = View.GONE
        binding.tvMsgLowongan.visibility = View.GONE

        lowonganAdapter = RVLowonganAdapter(listLowongan, requireContext()){
                idx ->
            val fragment = DetailLowonganFragment()
            val bundle = Bundle()
            bundle.putInt("lowongan_id", listLowongan.get(idx)!!.lowonganId!!)
            fragment.arguments = bundle
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
        }
        binding.rvLowongan.adapter = lowonganAdapter
        binding.rvLowongan.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        fetchData()
    }

    private fun fetchData(){
        var client = ApiConfiguration.getApiService().getPesertaLowongan(user.pesertaId!!)
        client.enqueue(object: Callback<LowonganResponse> {
            override fun onResponse(call: Call<LowonganResponse>, response: Response<LowonganResponse>){
                if(response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody!=null){
                        binding.avLoading.visibility = View.GONE
                        if(responseBody.lowongan!!.size > 0){
                            binding.rvLowongan.visibility = View.VISIBLE

                            listLowongan.clear()
                            listLowongan.addAll(responseBody.lowongan!!.toMutableList())
                            lowonganAdapter.notifyDataSetChanged()
                        }
                        else{
                            binding.tvMsgLowongan.visibility = View.VISIBLE
                            binding.tvMsgLowongan.setText(responseBody.message)
                            binding.rvLowongan.visibility = View.GONE
                        }
                    }
                }
                else{
                    Log.e("Lowongan Frag", "${response.message()}")
                }
            }
            override fun onFailure(call: Call<LowonganResponse>, t: Throwable) {
                Log.e("Lowongan Frag", "${t.message}")
                Toast.makeText(requireActivity(), "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}