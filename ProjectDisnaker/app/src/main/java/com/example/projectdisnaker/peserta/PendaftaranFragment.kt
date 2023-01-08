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
import com.example.projectdisnaker.api.*
import com.example.projectdisnaker.databinding.FragmentPendaftaranBinding
import com.example.projectdisnaker.rv.RVStatPendaftaranAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PendaftaranFragment : Fragment() {
    private lateinit var binding: FragmentPendaftaranBinding
    private var listStatus: MutableList<StatusItem?> = arrayListOf()
    private lateinit var statusAdapter: RVStatPendaftaranAdapter
    private lateinit var user: UserResponseItem

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPendaftaranBinding.inflate(inflater, container, false)
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

        binding.tvBlmDaftarStatus.visibility = View.GONE
        binding.rvPendaftaran.visibility = View.GONE

        statusAdapter = RVStatPendaftaranAdapter(listStatus, requireContext()){
            idx, pelatihanId, stat ->
            val fragment = DetailPendaftaranFragment()
            val bundle = Bundle()
            bundle.putInt("pelatihan_id", pelatihanId)
            bundle.putParcelable("status", stat)
            fragment.arguments = bundle
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment).commit()
        }
        binding.rvPendaftaran.adapter = statusAdapter
        binding.rvPendaftaran.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        fetchStatus()
    }

    private fun fetchStatus(){
        var client = ApiConfiguration.getApiService().getPesertaPendaftaran(user.pesertaId!!)
        client.enqueue(object: Callback<StatusPendaftaranResponse> {
            override fun onResponse(call: Call<StatusPendaftaranResponse>, response: Response<StatusPendaftaranResponse>){
                if(response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody!=null){
                        binding.avLoading.visibility = View.GONE
                        if(responseBody.pendaftaran!!.size > 0){
                            binding.rvPendaftaran.visibility = View.VISIBLE

                            listStatus.clear()
                            listStatus.addAll(responseBody.pendaftaran!!.toMutableList())
                            statusAdapter.notifyDataSetChanged()
                        }
                        else{
                            binding.tvBlmDaftarStatus.visibility = View.VISIBLE
                            binding.tvBlmDaftarStatus.setText(responseBody.message)
                        }
                    }
                }
                else{
                    Log.e("Pendaftaran Frag", "${response.message()}")
                }
            }
            override fun onFailure(call: Call<StatusPendaftaranResponse>, t: Throwable) {
                Log.e("Pendaftaran Frag", "${t.message}")
                Toast.makeText(requireActivity(), "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}