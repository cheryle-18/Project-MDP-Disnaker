package com.example.projectdisnaker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.projectdisnaker.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val bundle = Bundle()

        //set initial fragment
        val frag = PelatihanFragment()
        frag.arguments = bundle
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, frag).commit()

        binding.bottomNav.setOnItemSelectedListener {
            if(it.itemId==R.id.menu_pelatihan){
                val fragment = PelatihanFragment()
                fragment.arguments = bundle
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
            }
            else if(it.itemId==R.id.menu_profile){
                val fragment = ProfileFragment()
                fragment.arguments = bundle
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
            }
            return@setOnItemSelectedListener true
        }
    }
}