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
import com.example.projectdisnaker.api.*
import com.example.projectdisnaker.databinding.FragmentRiwayatPelatihanBinding
import com.example.projectdisnaker.perusahaan.PerusahaanProfileFragment
import com.example.projectdisnaker.rv.RVRiwayatAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RiwayatPelatihanFragment : Fragment() {
    private lateinit var binding: FragmentRiwayatPelatihanBinding
    private var listRiwayat: MutableList<RiwayatItem?> = arrayListOf()
    private lateinit var riwayatAdapter: RVRiwayatAdapter
    private lateinit var user: UserResponseItem

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRiwayatPelatihanBinding.inflate(inflater, container, false)
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
        binding.llInitial.bringToFront()

        requireActivity().onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    val fragment = ProfileFragment()
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment).commit()
                }
            })

        binding.rvRiwayatPelatihan.visibility = View.GONE
        binding.tvBlmPelatihan.visibility = View.GONE

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
        binding.rvRiwayatPelatihan.adapter = riwayatAdapter
        binding.rvRiwayatPelatihan.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

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
        var client = ApiConfiguration.getApiService().getRiwayatPelatihan(user.pesertaId!!)
        client.enqueue(object: Callback<RiwayatResponse> {
            override fun onResponse(call: Call<RiwayatResponse>, response: Response<RiwayatResponse>){
                if(response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody!=null){
                        binding.avLoading.visibility = View.GONE
                        if(responseBody.riwayat!!.size > 0){
                            binding.rvRiwayatPelatihan.visibility = View.VISIBLE

                            listRiwayat.clear()
                            listRiwayat.addAll(responseBody.riwayat!!.toMutableList())
                            riwayatAdapter.notifyDataSetChanged()
                        }
                        else{
                            binding.tvBlmPelatihan.visibility = View.VISIBLE
                            binding.tvBlmPelatihan.setText(responseBody.message)
                        }
                    }
                }
                else{
                    Log.e("Riwayat Pelatihan Frag", "${response.message()}")
                }
            }
            override fun onFailure(call: Call<RiwayatResponse>, t: Throwable) {
                Log.e("Riwayat Pelatihan Frag", "${t.message}")
                Toast.makeText(requireActivity(), "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}