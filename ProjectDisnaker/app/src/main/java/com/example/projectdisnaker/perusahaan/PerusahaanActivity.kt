package com.example.projectdisnaker.perusahaan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.projectdisnaker.R
import com.example.projectdisnaker.admin.AdminPelatihanFragment
import com.example.projectdisnaker.databinding.ActivityPerusahaanBinding

class PerusahaanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPerusahaanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPerusahaanBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val bundle = Bundle()

        //set initial fragment
        val frag = PerusahaanLowonganFragment()
        frag.arguments = bundle
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container_perusahaan, frag).commit()

        binding.bottomNavPerusahaan.setOnItemSelectedListener {
            if(it.itemId== R.id.menu_lowongan_perus){
                val frag = PerusahaanLowonganFragment()
                frag.arguments = bundle
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container_perusahaan, frag).commit()
            }
            else if(it.itemId== R.id.menu_profile_perus){
                val frag = PerusahaanProfileFragment()
                frag.arguments = bundle
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container_perusahaan, frag).commit()
            }

            return@setOnItemSelectedListener true
        }
    }
}