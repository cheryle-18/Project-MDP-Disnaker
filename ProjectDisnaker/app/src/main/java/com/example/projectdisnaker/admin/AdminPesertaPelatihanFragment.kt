package com.example.projectdisnaker.admin

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
import com.example.projectdisnaker.api.PelatihanItem
import com.example.projectdisnaker.api.PesertaPendaftaranItem
import com.example.projectdisnaker.api.PesertaPendaftaranResponse
import com.example.projectdisnaker.databinding.FragmentAdminPesertaPelatihanBinding
import com.example.projectdisnaker.perusahaan.PerusahaanLihatPesertaFragment
import com.example.projectdisnaker.rv.RVPesertaPendaftaranAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminPesertaPelatihanFragment : Fragment() {
    private lateinit var binding: FragmentAdminPesertaPelatihanBinding
    private var listPendaftar:MutableList<PesertaPendaftaranItem?> = arrayListOf()
    private lateinit var pesertaAdapter: RVPesertaPendaftaranAdapter
    private lateinit var pelatihan: PelatihanItem

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAdminPesertaPelatihanBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //action bar
        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.setTitle("Pelatihan")
        actionBar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)

        binding.rvPesertaPelatihan.visibility = View.GONE
        binding.tvBlmDaftarPelAdmin.visibility = View.GONE

        pelatihan = requireArguments().getParcelable<PelatihanItem>("pelatihan")!!

        pesertaAdapter = RVPesertaPendaftaranAdapter(listPendaftar, "pelatihan", requireContext()){
                idx ->
            val fragment = AdminDetailPesertaFragment()
            val bundle = Bundle()
            bundle.putParcelable("pelatihan", pelatihan)
            bundle.putParcelable("peserta", listPendaftar.get(idx)!!)
            bundle.putString("type", "pelatihan")
            fragment.arguments = bundle
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_admin, fragment).commit()
        }
        binding.rvPesertaPelatihan.adapter = pesertaAdapter
        binding.rvPesertaPelatihan.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        fetchPendaftaran()
        pesertaAdapter.notifyDataSetChanged()

        requireActivity().onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    val fragment = AdminDetailPelatihanFragment()
                    val bundle = Bundle()
                    bundle.putParcelable("pelatihan", pelatihan)
                    fragment.arguments = bundle
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_admin, fragment).commit()
                }
            })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                val fragment = AdminDetailPelatihanFragment()
                val bundle = Bundle()
                bundle.putParcelable("pelatihan", pelatihan)
                fragment.arguments = bundle
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_admin, fragment).commit()
            }
        }
        return true
    }

    private fun fetchPendaftaran(){
        var client = ApiConfiguration.getApiService().getPesertaPelatihan(pelatihan.pelatihanId!!)
        client.enqueue(object: Callback<PesertaPendaftaranResponse> {
            override fun onResponse(call: Call<PesertaPendaftaranResponse>, response: Response<PesertaPendaftaranResponse>){
                if(response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody!=null){
                        binding.avLoading.visibility = View.GONE

                        listPendaftar.clear()
                        listPendaftar.addAll(responseBody.pendaftaran!!.toMutableList())
                        if(listPendaftar.size>0){
                            binding.rvPesertaPelatihan.visibility = View.VISIBLE
                        }
                        else{
                            binding.tvBlmDaftarPelAdmin.visibility = View.VISIBLE
                        }
                        pesertaAdapter.notifyDataSetChanged()
                    }
                }
                else{
                    Log.e("Admin Peserta Pel Frag", "${response.message()}")
                    Toast.makeText(requireActivity(), "${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<PesertaPendaftaranResponse>, t: Throwable) {
                Log.e("Admin Peserta Pel Frag", "${t.message}")
                Toast.makeText(requireActivity(), "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}