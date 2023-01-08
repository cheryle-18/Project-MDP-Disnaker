package com.example.projectdisnaker.peserta

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectdisnaker.R
import com.example.projectdisnaker.api.ApiConfiguration
import com.example.projectdisnaker.api.RiwayatItem
import com.example.projectdisnaker.api.RiwayatResponse
import com.example.projectdisnaker.api.UserResponseItem
import com.example.projectdisnaker.databinding.FragmentRiwayatPekerjaanBinding
import com.example.projectdisnaker.rv.RVRiwayatAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RiwayatPekerjaanFragment : Fragment() {
    private lateinit var binding: FragmentRiwayatPekerjaanBinding
    private var listRiwayat: MutableList<RiwayatItem?> = arrayListOf()
    private lateinit var riwayatAdapter: RVRiwayatAdapter
    private lateinit var user: UserResponseItem

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRiwayatPekerjaanBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //action bar
        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.setTitle("Profil")
        actionBar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)

        requireActivity().onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    val fragment = ProfileFragment()
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment).commit()
                }
            })

        binding.rvRiwayatPekerjaan.visibility = View.GONE
        binding.tvBlmKerja.visibility = View.GONE

        user = (activity as HomeActivity).user
        binding.tvNamaProfile.setText(user.nama)
        binding.tvUsernameProfile.setText(user.username)

        var nama = user.nama!!.trim().split("\\s+".toRegex()).toTypedArray()
        var initials = ""
        for(n in nama){
            initials += n.substring(0, 1)
        }
        binding.tvInisial.setText(initials)

        riwayatAdapter = RVRiwayatAdapter(listRiwayat, requireContext())
        binding.rvRiwayatPekerjaan.adapter = riwayatAdapter
        binding.rvRiwayatPekerjaan.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        fetchRiwayat()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                val fragment = ProfileFragment()
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment).commit()
            }
        }
        return true
    }

    private fun fetchRiwayat(){
        var client = ApiConfiguration.getApiService().getRiwayatPekerjaan(user.pesertaId!!)
        client.enqueue(object: Callback<RiwayatResponse> {
            override fun onResponse(call: Call<RiwayatResponse>, response: Response<RiwayatResponse>){
                if(response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody!=null){
                        binding.avLoading.visibility = View.GONE
                        if(responseBody.riwayat!!.size > 0){
                            binding.rvRiwayatPekerjaan.visibility = View.VISIBLE

                            listRiwayat.clear()
                            listRiwayat.addAll(responseBody.riwayat!!.toMutableList())
                            riwayatAdapter.notifyDataSetChanged()
                        }
                        else{
                            binding.tvBlmKerja.visibility = View.VISIBLE
                            binding.tvBlmKerja.setText(responseBody.message)
                        }
                    }
                }
                else{
                    Log.e("Riwayat Pekerjaan Frag", "${response.message()}")
                }
            }
            override fun onFailure(call: Call<RiwayatResponse>, t: Throwable) {
                Log.e("Riwayat Pekerjaan Frag", "${t.message}")
                Toast.makeText(requireActivity(), "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}