package com.example.projectdisnaker.peserta

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectdisnaker.R
import com.example.projectdisnaker.api.ApiConfiguration
import com.example.projectdisnaker.api.PelatihanItem
import com.example.projectdisnaker.api.PelatihanResponse
import com.example.projectdisnaker.databinding.FragmentPelatihanBinding
import com.example.projectdisnaker.rv.RVPelatihanAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PelatihanFragment : Fragment() {
    private lateinit var binding: FragmentPelatihanBinding
    private lateinit var adapterPelatihan : RVPelatihanAdapter
    private lateinit var listPelatihan : List<PelatihanItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listPelatihan = listOf()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPelatihanBinding.inflate(inflater, container, false)
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

        binding.rvPelatihan.visibility = View.GONE

        initData()

        binding.btnSearch.setOnClickListener {
//            val fragment = DetailPelatihanFragment()
//            val bundle = Bundle()
//            fragment.arguments = bundle
//            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
            val search = binding.etSearch.text.toString()
            initData(search)
        }
    }

    fun initData(search:String = ""){
        //retrofit call
        var client = ApiConfiguration.getApiService().getPelatihanWithQuery(search,"peserta")
        client.enqueue(object: Callback<PelatihanResponse> {

            override fun onResponse(call: Call<PelatihanResponse>, response: Response<PelatihanResponse>) {
                if(response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody!=null){
                        binding.avLoading.visibility = View.GONE
                        binding.rvPelatihan.visibility = View.VISIBLE

                        listPelatihan = responseBody.pelatihan!!
                        initRV()
                    }
                    else{
                        println("${response.message()}")
                    }
                }
                else{
                    Log.d("Error Frag pelatihan",response.toString())
                }
            }

            override fun onFailure(call: Call<PelatihanResponse>, t: Throwable) {
                Log.d("Error Frag peserta", "${t.message}")
            }
        })
    }

    fun initRV(){
        adapterPelatihan = RVPelatihanAdapter(requireActivity(), listPelatihan, R.layout.pelatihan_list_item){
                idx ->
            val fragment = DetailPelatihanFragment()
            val bundle = Bundle()
            bundle.putParcelable("pelatihan", listPelatihan.get(idx)!!)
            fragment.arguments = bundle
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
        }
        binding.rvPelatihan.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvPelatihan.adapter = adapterPelatihan
    }
}