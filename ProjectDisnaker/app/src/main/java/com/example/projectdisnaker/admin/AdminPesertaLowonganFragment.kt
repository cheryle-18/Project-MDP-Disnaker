package com.example.projectdisnaker.admin

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectdisnaker.R
import com.example.projectdisnaker.api.ApiConfiguration
import com.example.projectdisnaker.api.LowonganItem
import com.example.projectdisnaker.api.PesertaPendaftaranItem
import com.example.projectdisnaker.api.PesertaPendaftaranResponse
import com.example.projectdisnaker.databinding.FragmentAdminPesertaLowonganBinding
import com.example.projectdisnaker.perusahaan.PerusahaanDetailLowonganFragment
import com.example.projectdisnaker.perusahaan.PerusahaanLihatPesertaFragment
import com.example.projectdisnaker.rv.RVPesertaPendaftaranAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminPesertaLowonganFragment : Fragment() {
    private lateinit var binding: FragmentAdminPesertaLowonganBinding
    private var listPendaftar:MutableList<PesertaPendaftaranItem?> = arrayListOf()
    private lateinit var pesertaAdapter: RVPesertaPendaftaranAdapter
    private lateinit var lowongan: LowonganItem

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAdminPesertaLowonganBinding.inflate(inflater, container, false)
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

        binding.tvBlmDaftarAdmin.visibility = View.GONE
        binding.rvPendaftarLowAdmin.visibility = View.GONE

        lowongan = requireArguments().getParcelable<LowonganItem>("lowongan")!!

        pesertaAdapter = RVPesertaPendaftaranAdapter(listPendaftar, "lowongan", requireContext()){
                idx ->
            val fragment = AdminDetailPesertaFragment()
            val bundle = Bundle()
            bundle.putParcelable("lowongan", lowongan)
            bundle.putParcelable("peserta", listPendaftar.get(idx)!!)
            bundle.putString("type", "lowongan")
            fragment.arguments = bundle
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_admin, fragment).commit()
        }
        binding.rvPendaftarLowAdmin.adapter = pesertaAdapter
        binding.rvPendaftarLowAdmin.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        fetchPendaftaran()
        pesertaAdapter.notifyDataSetChanged()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                val fragment = AdminDetailLowonganFragment()
                val bundle = Bundle()
                bundle.putParcelable("lowongan", lowongan)
                fragment.arguments = bundle
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_admin, fragment).commit()
            }
        }
        return true
    }

    private fun fetchPendaftaran(){
        var client = ApiConfiguration.getApiService().getPendaftaranLowongan(lowongan.lowonganId!!)
        client.enqueue(object: Callback<PesertaPendaftaranResponse> {
            override fun onResponse(call: Call<PesertaPendaftaranResponse>, response: Response<PesertaPendaftaranResponse>){
                if(response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody!=null){
                        binding.avLoading.visibility = View.GONE

                        listPendaftar.clear()
                        listPendaftar.addAll(responseBody.pendaftaran!!.toMutableList())
                        if(listPendaftar.size>0){
                            binding.rvPendaftarLowAdmin.visibility = View.VISIBLE
                        }
                        else{
                            binding.tvBlmDaftarAdmin.visibility = View.VISIBLE
                        }
                        pesertaAdapter.notifyDataSetChanged()
                    }
                }
                else{
                    Log.e("Admin Peserta Low Frag", "${response.message()}")
                    Toast.makeText(requireActivity(), "${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<PesertaPendaftaranResponse>, t: Throwable) {
                Log.e("Admin Peserta Low Frag", "${t.message}")
                Toast.makeText(requireActivity(), "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}