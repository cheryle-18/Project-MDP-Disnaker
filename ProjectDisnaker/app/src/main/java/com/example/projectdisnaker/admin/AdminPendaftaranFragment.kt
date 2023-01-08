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
import com.example.projectdisnaker.api.*
import com.example.projectdisnaker.databinding.FragmentAdminPendaftaranBinding
import com.example.projectdisnaker.rv.RVPendaftaranAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminPendaftaranFragment : Fragment() {
    private lateinit var binding: FragmentAdminPendaftaranBinding
    private lateinit var adapterPendaftaran : RVPendaftaranAdapter
    private lateinit var listPendaftaran : List<PendaftaranPelatihanItem>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAdminPendaftaranBinding.inflate(inflater, container, false)
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

        binding.rvPendaftaranAdmin.visibility = View.GONE
        binding.tvBlmAdaPendaftaran.visibility = View.GONE

        initData()
    }

    fun initData(){
        //retrofit call
        var client = ApiConfiguration.getApiService().getAllPendaftaranPelatihan()
        client.enqueue(object: Callback<PendaftaranResponse> {
            override fun onResponse(call: Call<PendaftaranResponse>,response: Response<PendaftaranResponse>) {
                if(response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody!=null){
                        binding.avLoading.visibility = View.GONE
                        if(responseBody.pendaftaran!=null){
                            if(responseBody.pendaftaran.size > 0){
                                binding.rvPendaftaranAdmin.visibility = View.VISIBLE
                                listPendaftaran = responseBody.pendaftaran!!
                                initRV()

                                if(arguments?.getInt("idx") != null){
                                    if(arguments?.getInt("idx") != -1){
                                        lihatDetail(arguments?.getInt("idx")!!)
                                    }
                                }
                            }
                            else{
                                binding.tvBlmAdaPendaftaran.visibility = View.VISIBLE
                            }
                        }
                    }
                    else{
                        println("${response.message()}")
                    }
                }
                else{
                    Log.d("Error Frag Admin",response.toString())
                }
            }

            override fun onFailure(call: Call<PendaftaranResponse>, t: Throwable) {
                Log.d("Error Frag Admin", "${t.message}")
            }
        })
    }


    fun initRV(){
        adapterPendaftaran = RVPendaftaranAdapter(requireActivity(), listPendaftaran, R.layout.pendaftaran_list_item)
        adapterPendaftaran.onClickListener = object:RVPendaftaranAdapter.OnClickListener{
            override fun onClick(idx: Int) {
                lihatDetail(idx)
            }
        }
        binding.rvPendaftaranAdmin.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvPendaftaranAdmin.adapter = adapterPendaftaran
    }

    fun lihatDetail(idx:Int){
        val adminDetailPendaftaranFragment = AdminDetailPendaftaranFragment()
        var bundle = Bundle()
        bundle.putParcelable("pendaftaran", listPendaftaran.get(idx)!!)
        bundle.putInt("idx", idx)
        adminDetailPendaftaranFragment.arguments = bundle

        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_admin, adminDetailPendaftaranFragment)
            .commitNow()
    }
}