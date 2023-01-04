package com.example.projectdisnaker.perusahaan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.projectdisnaker.R
import com.example.projectdisnaker.admin.AdminPelatihanFragment
import com.example.projectdisnaker.api.*
import com.example.projectdisnaker.databinding.ActivityPerusahaanBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PerusahaanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPerusahaanBinding
    lateinit var user: UserResponseItem
    private lateinit var perusahaan: PerusahaanItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPerusahaanBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        user = intent.getParcelableExtra<UserResponseItem>("user")!!

        val bundle = Bundle()
        bundle.putParcelable("user", user)

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