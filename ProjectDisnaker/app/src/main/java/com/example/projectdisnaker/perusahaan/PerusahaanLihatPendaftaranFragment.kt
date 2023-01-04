package com.example.projectdisnaker.perusahaan

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectdisnaker.R
import com.example.projectdisnaker.api.*
import com.example.projectdisnaker.databinding.FragmentPerusahaanLihatPendaftaranBinding
import com.example.projectdisnaker.rv.RVPesertaPendaftaranAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PerusahaanLihatPendaftaranFragment : Fragment() {
    private lateinit var binding: FragmentPerusahaanLihatPendaftaranBinding
    private var listPendaftar:MutableList<PesertaPendaftaranItem?> = arrayListOf()
    private lateinit var pesertaAdapter: RVPesertaPendaftaranAdapter
    private var lowonganId: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPerusahaanLihatPendaftaranBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //action bar
        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.setTitle("Lowongan")
        actionBar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)

        binding.rvPendaftarLow.visibility = View.GONE

        lowonganId = requireArguments().getInt("lowongan_id")!!

        pesertaAdapter = RVPesertaPendaftaranAdapter(listPendaftar, "lowongan", requireContext()){
            idx ->
            val fragment = PerusahaanLihatPesertaFragment()
            val bundle = Bundle()
            bundle.putInt("lowongan_id", lowonganId)
            bundle.putParcelable("peserta", listPendaftar.get(idx)!!)
            fragment.arguments = bundle
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_perusahaan, fragment).commit()
        }
        binding.rvPendaftarLow.adapter = pesertaAdapter
        binding.rvPendaftarLow.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        fetchPendaftaran()
        pesertaAdapter.notifyDataSetChanged()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                val fragment = PerusahaanDetailLowonganFragment()
                val bundle = Bundle()
                bundle.putInt("lowongan_id", lowonganId)
                fragment.arguments = bundle
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_perusahaan, fragment).commit()
            }
        }
        return true
    }

    private fun fetchPendaftaran(){
        var client = ApiConfiguration.getApiService().getPendaftaranLowongan(lowonganId)
        client.enqueue(object: Callback<PesertaPendaftaranResponse> {
            override fun onResponse(call: Call<PesertaPendaftaranResponse>, response: Response<PesertaPendaftaranResponse>){
                if(response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody!=null){
                        binding.avLoading.visibility = View.GONE
                        binding.rvPendaftarLow.visibility = View.VISIBLE

                        listPendaftar.clear()
                        listPendaftar.addAll(responseBody.pendaftaran!!.toMutableList())
                        if(listPendaftar.size>0){
                            binding.tvBlmDaftar.visibility = View.GONE
                        }
                        pesertaAdapter.notifyDataSetChanged()
                    }
                }
                else{
                    Log.e("Perus Pendaftaran Frag", "${response.message()}")
                    Toast.makeText(requireActivity(), "${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<PesertaPendaftaranResponse>, t: Throwable) {
                Log.e("Perus Pendaftaran Frag", "${t.message}")
                Toast.makeText(requireActivity(), "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}