package com.example.projectdisnaker.admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.projectdisnaker.R
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
            if(it.itemId== R.id.menu_pelatihan_admin){
                val fragment = AdminPelatihanFragment()
                fragment.arguments = bundle
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container_admin, fragment).commit()
            }
            else if(it.itemId== R.id.menu_lowongan_admin){
                val fragment = AdminLowonganFragment()
                fragment.arguments = bundle
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container_admin, fragment).commit()
            }
            else if(it.itemId== R.id.menu_pendaftaran_admin){
                val fragment = AdminPendaftaranFragment()
                fragment.arguments = bundle
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container_admin, fragment).commit()
            }
            else if(it.itemId== R.id.menu_peserta){
                val fragment = AdminPesertaFragment()
                fragment.arguments = bundle
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container_admin, fragment).commit()
            }
            else if(it.itemId== R.id.menu_perusahaan){
                val fragment = AdminPerusahaanFragment()
                fragment.arguments = bundle
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container_admin, fragment).commit()
            }

            return@setOnItemSelectedListener true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.opt_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_logout->{
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}