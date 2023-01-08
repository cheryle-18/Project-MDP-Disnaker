package com.example.projectdisnaker.admin

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
import com.example.projectdisnaker.api.ApiConfiguration
import com.example.projectdisnaker.api.PerusahaanItem
import com.example.projectdisnaker.api.PerusahaanResponse
import com.example.projectdisnaker.databinding.FragmentAdminTambahPerusahaanBinding
import com.example.projectdisnaker.perusahaan.PerusahaanLowonganFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminTambahPerusahaanFragment : Fragment() {
    private lateinit var binding: FragmentAdminTambahPerusahaanBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAdminTambahPerusahaanBinding.inflate(inflater, container, false)
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

        binding.btnTambahPerus.setOnClickListener {
            var nama = binding.etNamaPerusahaan.text.toString()
            var alamat = binding.etAlamatPerusahaan.text.toString()
            var email = binding.etEmailPerusahaan.text.toString()
            var telp = binding.etTelpPerusahaan.text.toString()
            var username = binding.etUsernamePerusahaan.text.toString()
            var password = binding.etPasswordPerusahaan.text.toString()
            var confirm = binding.etConfirmPerusahaan.text.toString()

            if(nama!="" && alamat!="" && email!="" && telp!="" && username!="" && password!="" && confirm!=""){
                if(password==confirm){
                    var newPerus = PerusahaanItem(telp, email, alamat, nama, username, password)

                    var client = ApiConfiguration.getApiService().insertPerusahaan(newPerus)
                    client.enqueue(object: Callback<PerusahaanResponse> {
                        override fun onResponse(call: Call<PerusahaanResponse>, response: Response<PerusahaanResponse>){
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
                                    tvDialog.setText("Berhasil menambah perusahaan.")

                                    btnOk.setOnClickListener {
                                        dialog.dismiss()
                                        val fragment = AdminPerusahaanFragment()
                                        requireActivity().supportFragmentManager.beginTransaction()
                                            .replace(R.id.fragment_container_admin, fragment).commit()
                                    }
                                }
                            }
                            else{
                                Log.e("", "${response.message()}")
                                Toast.makeText(requireActivity(), "${response.message()}", Toast.LENGTH_SHORT).show()
                            }
                        }
                        override fun onFailure(call: Call<PerusahaanResponse>, t: Throwable) {
                            Log.e("", "${t.message}")
                            Toast.makeText(requireActivity(), "${t.message}", Toast.LENGTH_SHORT).show()
                        }
                    })
                }
                else{
                    Toast.makeText(requireActivity(), "Password harus sama dengan konfirmasi password!", Toast.LENGTH_SHORT).show()
                }
            }
            else{
                Toast.makeText(requireActivity(), "Harap isi semua data", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                if(binding.etNamaPerusahaan.text.toString()!="" || binding.etAlamatPerusahaan.text.toString()!=""
                    || binding.etEmailPerusahaan.text.toString()!="" || binding.etTelpPerusahaan.text.toString()!=""
                    || binding.etUsernamePerusahaan.text.toString()!="" || binding.etPasswordPerusahaan.text.toString()!=""
                    || binding.etConfirmPerusahaan.text.toString()!=""){
                    val dialogBinding = layoutInflater.inflate(R.layout.confirm_dialog, null)
                    val dialog = Dialog(requireContext())
                    dialog.setContentView(dialogBinding)
                    dialog.setCancelable(true)
                    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    dialog.show()

                    val btnKembali = dialogBinding.findViewById<Button>(R.id.btnConfirmDialog)
                    val btnKeluar = dialogBinding.findViewById<Button>(R.id.btnCancelDialog)
                    val tvDialog = dialogBinding.findViewById<TextView>(R.id.tvDialogConfirm)
                    tvDialog.setText("Keluar tanpa menyimpan perusahaan?")

                    btnKembali.setOnClickListener {
                        dialog.dismiss()
                    }
                    btnKeluar.setOnClickListener {
                        dialog.dismiss()
                        val fragment = AdminPerusahaanFragment()
                        requireActivity().supportFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container_admin, fragment).commit()
                    }
                }
                else{
                    val fragment = AdminPerusahaanFragment()
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_admin, fragment).commit()
                }
            }
        }
        return true
    }
}