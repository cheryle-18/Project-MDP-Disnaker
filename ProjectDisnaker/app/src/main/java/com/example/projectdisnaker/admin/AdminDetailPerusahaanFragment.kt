package com.example.projectdisnaker.admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectdisnaker.R
import com.example.projectdisnaker.api.LowonganItem
import com.example.projectdisnaker.api.UserResponseItem
import com.example.projectdisnaker.databinding.FragmentAdminDetailPerusahaanBinding
import com.example.projectdisnaker.rv.RVLowonganAdapter
import com.example.projectdisnaker.rv.RVPerusahaanAdapter

class AdminDetailPerusahaanFragment : Fragment() {
    private lateinit var binding: FragmentAdminDetailPerusahaanBinding
    private lateinit var perusahaan: UserResponseItem
    private lateinit var lowonganAdapter: RVLowonganAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAdminDetailPerusahaanBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //action bar
        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.setTitle("Perusahaan")
        actionBar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)

        requireActivity().onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    val fragment = AdminPerusahaanFragment()
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_admin, fragment).commit()
                }
            })

        perusahaan = arguments?.getParcelable<UserResponseItem>("perusahaan")!!

        binding.tvUsernamePerusAdmin.setText(perusahaan.username!!)
        binding.tvEmailDetAdmin.setText(perusahaan.email!!)
        binding.tvAlamatPerusAdmin.setText(perusahaan.alamat!!)
        binding.tvTelpDetAdmin.setText(perusahaan.telp!!)
        initRV()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                val fragment = AdminPerusahaanFragment()
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_admin, fragment).commit()
            }
        }
        return true
    }

    fun initRV(){
        lowonganAdapter = RVLowonganAdapter(perusahaan.listLowongan!!.toMutableList(), requireContext()){
                idx ->
            val fragment = AdminDetailLowonganFragment()
            val bundle = Bundle()
            bundle.putParcelable("lowongan", perusahaan.listLowongan!!.get(idx)!!)
            bundle.putParcelable("perusahaan", perusahaan!!)
            bundle.putBoolean("isDaftar", true)
            fragment.arguments = bundle
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_admin, fragment).commit()
        }
        binding.rvLowonganPerus.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvLowonganPerus.adapter = lowonganAdapter
    }
}