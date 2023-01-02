package com.example.projectdisnaker.peserta

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.projectdisnaker.R
import com.example.projectdisnaker.api.UserResponseItem
import com.example.projectdisnaker.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    lateinit var user: UserResponseItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        user = intent.getParcelableExtra<UserResponseItem>("user")!!

        val bundle = Bundle()
        bundle.putParcelable("user", user)

        //set initial fragment
        val frag = PelatihanFragment()
        frag.arguments = bundle
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, frag).commit()

        binding.bottomNav.setOnItemSelectedListener {
            if(it.itemId== R.id.menu_pelatihan){
                val fragment = PelatihanFragment()
                fragment.arguments = bundle
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
            }
            else if(it.itemId== R.id.menu_kerja){
                val fragment = LowonganFragment()
                fragment.arguments = bundle
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
            }
            else if(it.itemId== R.id.menu_pendaftaran_admin){
                val fragment = PendaftaranFragment()
                fragment.arguments = bundle
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
            }
            else if(it.itemId== R.id.menu_profile){
                val fragment = ProfileFragment()
                fragment.arguments = bundle
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
            }
            return@setOnItemSelectedListener true
        }
    }
}