package com.example.projectdisnaker.perusahaan

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.example.projectdisnaker.R
import com.example.projectdisnaker.api.*
import com.example.projectdisnaker.databinding.FragmentPerusahaanPasswordBinding
import com.example.projectdisnaker.peserta.HomeActivity
import com.example.projectdisnaker.peserta.ProfileFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PerusahaanPasswordFragment : Fragment() {
    private lateinit var binding: FragmentPerusahaanPasswordBinding
    private lateinit var user: UserResponseItem
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPerusahaanPasswordBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //action bar
        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.setTitle("Profil")
        actionBar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)
        user = (activity as PerusahaanActivity).user

        requireActivity().onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    val fragment = PerusahaanProfileFragment()
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_perusahaan, fragment).commit()
                }
            })

        binding.btnUbahPassPerus.setOnClickListener{
            var passbaru =binding.etPassBaruPerus.text.toString()
            var confpass =binding.etConfBaruPerus.text.toString()
            var passlama =binding.etPassLamaPerus.text.toString()

            if (binding.etPassBaruPerus.text.toString().isNotBlank()&&
                binding.etConfBaruPerus.text.toString().isNotBlank()&&
                binding.etPassLamaPerus.text.toString().isNotBlank()){
                if (passbaru==confpass){
                    var edited = PasswordItem(passbaru, passlama)

                    var client = ApiConfiguration.getApiService().updatePasswordPerusahaan(user.apiKey!!, edited)
                    client.enqueue(object: Callback<PerusahaanResponse> {
                        override fun onResponse(call: Call<PerusahaanResponse>, response: Response<PerusahaanResponse>) {
                            if(response.isSuccessful){
                                val responseBody = response.body()
                                if(responseBody!=null){
                                    if(responseBody.status==1){
                                        val dialogBinding = layoutInflater.inflate(R.layout.success_dialog, null)
                                        val dialog = Dialog(requireContext())
                                        dialog.setContentView(dialogBinding)
                                        dialog.setCancelable(true)
                                        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                                        dialog.show()

                                        val btnOk = dialogBinding.findViewById<Button>(R.id.btOkDialog)
                                        val tvDialog = dialogBinding.findViewById<TextView>(R.id.tvDialog)
                                        tvDialog.setText("Berhasil mengubah password.")

                                        btnOk.setOnClickListener {
                                            dialog.dismiss()
                                            val fragment = PerusahaanProfileFragment()
                                            requireActivity().supportFragmentManager.beginTransaction()
                                                .replace(R.id.fragment_container_perusahaan, fragment).commit()
                                        }
                                    }
                                    else{
                                        Toast.makeText(requireActivity(), "${responseBody.message}",
                                            Toast.LENGTH_SHORT).show()
                                    }
                                }
                                else{
                                    Log.e("Edit passperu Frag", "${response.message()}")
                                }
                            }
                        }

                        override fun onFailure(call: Call<PerusahaanResponse>, t: Throwable) {
                            Log.e("Edit passperu Frag", "${t.message}")
                        }
                    })
                }
                else{
                    Toast.makeText(requireActivity(), "Password harus sama dengan konfirmasi password", Toast.LENGTH_SHORT).show()
                }
            }
            else{
                Toast.makeText(requireActivity(), "Pastikan semua data terisi", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                if(binding.etPassBaruPerus.text.toString()!="" || binding.etConfBaruPerus.text.toString()!=""
                    || binding.etPassLamaPerus.text.toString()!=""){
                    val dialogBinding = layoutInflater.inflate(R.layout.confirm_dialog, null)
                    val dialog = Dialog(requireContext())
                    dialog.setContentView(dialogBinding)
                    dialog.setCancelable(true)
                    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    dialog.show()

                    val btnKembali = dialogBinding.findViewById<Button>(R.id.btnConfirmDialog)
                    val btnKeluar = dialogBinding.findViewById<Button>(R.id.btnCancelDialog)
                    val tvDialog = dialogBinding.findViewById<TextView>(R.id.tvDialogConfirm)
                    tvDialog.setText("Keluar tanpa menyimpan perubahan?")

                    btnKembali.setOnClickListener {
                        dialog.dismiss()
                    }
                    btnKeluar.setOnClickListener {
                        dialog.dismiss()
                        val fragment = PerusahaanProfileFragment()
                        requireActivity().supportFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container_perusahaan, fragment).commit()
                    }
                }
                else{
                    val fragment = PerusahaanProfileFragment()
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_perusahaan, fragment).commit()
                }
            }
        }
        return true
    }
}