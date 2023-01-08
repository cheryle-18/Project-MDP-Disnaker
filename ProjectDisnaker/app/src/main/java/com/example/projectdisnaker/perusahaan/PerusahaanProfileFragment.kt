package com.example.projectdisnaker.perusahaan

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.example.projectdisnaker.R
import com.example.projectdisnaker.api.*
import com.example.projectdisnaker.databinding.FragmentPerusahaanProfileBinding
import com.example.projectdisnaker.local.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PerusahaanProfileFragment : Fragment() {
    private lateinit var binding: FragmentPerusahaanProfileBinding
    private lateinit var user: UserResponseItem
    private lateinit var perusahaan: PerusahaanItem

    val ioScope = CoroutineScope(Dispatchers.IO)
    lateinit var db: AppDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPerusahaanProfileBinding.inflate(inflater, container, false)
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
        binding.llInitial.bringToFront()

        requireActivity().onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().moveTaskToBack(true)
                }
            })

        user = (activity as PerusahaanActivity).user
        db = AppDatabase.build(requireContext())

        fetchPerusahaan()

        binding.tvNamaPerusahaanProfile.setText(user.nama)
        binding.tvUsernamePerusahaan.setText(user.username)
        binding.llProfilePerus.visibility = View.GONE

        binding.layoutUbahPassPerusahaan.setOnClickListener {
            val fragment = PerusahaanPasswordFragment()
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment_container_perusahaan, fragment).commit()
        }

        binding.btnEditPerusahaan.setOnClickListener{
            if (binding.etAlamatPerusahaanProfile.text.toString().isBlank()&&
                    binding.etTelpPerusahaanProfile.text.toString().isBlank()&&
                    binding.etEmailPerusahaanProfile.text.toString().isBlank()){
                Toast.makeText(requireActivity(), "Harap isi semua data", Toast.LENGTH_SHORT).show()
            }
            else{
                var alamat = binding.etAlamatPerusahaanProfile.text.toString()
                var telp = binding.etTelpPerusahaanProfile.text.toString()
                var email = binding.etEmailPerusahaanProfile.text.toString()

                var edited = PerusahaanItem(telp, email, alamat)

                var client = ApiConfiguration.getApiService().updatePerusahaan(user.apiKey!!, edited)
                client.enqueue(object: Callback<PerusahaanResponse> {
                    override fun onResponse(call: Call<PerusahaanResponse>, response: Response<PerusahaanResponse>) {
                        if(response.isSuccessful){
                            val responseBody = response.body()
                            if(responseBody!=null){
                                val dialogBinding = layoutInflater.inflate(R.layout.success_dialog, null)
                                val dialog = Dialog(requireContext())
                                dialog.setContentView(dialogBinding)
                                dialog.setCancelable(true)
                                dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                                dialog.show()

                                val btnOk = dialogBinding.findViewById<Button>(R.id.btOkDialog)
                                val tvDialog = dialogBinding.findViewById<TextView>(R.id.tvDialog)
                                tvDialog.setText("Berhasil mengubah profil perusahaan.")

                                btnOk.setOnClickListener {
                                    dialog.dismiss()
                                    fetchPerusahaan()
                                }
                            }
                            else{
                                Log.e("Edit perusahaan Frag", "${response.message()}")
                                Toast.makeText(requireActivity(), "${response.message()}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                    override fun onFailure(call: Call<PerusahaanResponse>, t: Throwable) {
                        Log.e("Edit perusahaan Frag", "${t.message}")
                    }
                })
            }
        }
        binding.layoutLogoutPerusahaan.setOnClickListener {
            val dialogBinding = layoutInflater.inflate(R.layout.confirm_dialog, null)
            val dialog = Dialog(requireContext())
            dialog.setContentView(dialogBinding)
            dialog.setCancelable(true)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()

            val btnKembali = dialogBinding.findViewById<Button>(R.id.btnConfirmDialog)
            val btnKeluar = dialogBinding.findViewById<Button>(R.id.btnCancelDialog)
            val tvDialog = dialogBinding.findViewById<TextView>(R.id.tvDialogConfirm)

            tvDialog.setText("Keluar dari akun anda?")
            btnKeluar.setText("Keluar")
            btnKembali.setText("Kembali")

            btnKeluar.setOnClickListener {
                ioScope.launch {
                    db.usersDao.removeToken()
                    requireActivity().runOnUiThread{
                        dialog.dismiss()
                        val resultIntent = Intent()
                        requireActivity().setResult(Activity.RESULT_OK, resultIntent)
                        requireActivity().finish()
                    }
                }
            }
            btnKembali.setOnClickListener {
                dialog.dismiss()
            }
        }
    }

    private fun fillDetails(){
        binding.etAlamatPerusahaanProfile.setText(perusahaan.alamat)
        binding.etTelpPerusahaanProfile.setText(perusahaan.telp)
        binding.etEmailPerusahaanProfile.setText(perusahaan.email)
    }

    private fun fetchPerusahaan(){
        var client = ApiConfiguration.getApiService().getPerusahaan(user.perusahaanId!!)
        client.enqueue(object: Callback<PerusahaanResponse> {
            override fun onResponse(call: Call<PerusahaanResponse>, response: Response<PerusahaanResponse>){
                if(response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody!=null){
                        binding.avLoading.visibility = View.GONE
                        binding.llProfilePerus.visibility = View.VISIBLE

                        perusahaan = responseBody.perusahaan!![0]!!
                        fillDetails()
                    }
                }
                else{
                    Log.e("Perus Profile Fragment", "${response.message()}")
                }
            }
            override fun onFailure(call: Call<PerusahaanResponse>, t: Throwable) {
                Log.e("Perus Profile Fragment", "${t.message}")
            }
        })
    }
}