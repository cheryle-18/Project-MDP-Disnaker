package com.example.projectdisnaker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.projectdisnaker.databinding.ActivityAdminBinding

class AdminActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val bundle = Bundle()

        //set initial fragment
        val frag = AdminPelatihanFragment()
        frag.arguments = bundle
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container_admin, frag).commit()

        binding.bottomNavAdmin.setOnItemSelectedListener {
            if(it.itemId==R.id.menu_pelatihan_admin){
                val fragment = AdminPelatihanFragment()
                fragment.arguments = bundle
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container_admin, fragment).commit()
            }
            else if(it.itemId==R.id.menu_kerja_admin){
                val fragment = AdminPelatihanFragment()
                fragment.arguments = bundle
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container_admin, fragment).commit()
            }

            return@setOnItemSelectedListener true
        }
    }
}