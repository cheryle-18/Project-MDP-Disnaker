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
import androidx.appcompat.app.AppCompatActivity
import com.example.projectdisnaker.R
import com.example.projectdisnaker.api.ApiConfiguration
import com.example.projectdisnaker.api.PerusahaanResponse
import com.example.projectdisnaker.api.PesertaResponse
import com.example.projectdisnaker.api.UserResponseItem
import com.example.projectdisnaker.databinding.FragmentPerusahaanPasswordBinding
import com.example.projectdisnaker.peserta.HomeActivity
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

        binding.btnUbahPassPerus.setOnClickListener{
            var passbaru =binding.etPassBaruPerus.text.toString()
            var confpass =binding.etConfBaruPerus.text.toString()
            var passlama =binding.etPassLamaPerus.text.toString()

            if (binding.etPassBaruPerus.text.toString().isNotBlank()&&
                binding.etConfBaruPerus.text.toString().isNotBlank()&&
                binding.etPassLamaPerus.text.toString().isNotBlank()){
                if (passbaru==confpass){
                    if (passbaru == passlama){
                        Toast.makeText(requireActivity(), "password tidak boleh sama dengan sebelumnya ", Toast.LENGTH_SHORT).show()
                    }
                    else{
//                        if (passlama !=user.password){
//                            Toast.makeText(requireActivity(), "password lama salah", Toast.LENGTH_SHORT).show()
//                            Toast.makeText(requireActivity(), "${user.password}", Toast.LENGTH_SHORT).show()
//                        }
//                        else{

                        var client = ApiConfiguration.getApiService().updatePasswordPerusahaan(user.perusahaanId!!, passbaru)
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
                                        tvDialog.setText("Berhasil mengubah Password.")

                                        btnOk.setOnClickListener {
                                            dialog.dismiss()
                                        }
                                    }
                                    else{
                                        Log.e("Edit passperu Frag", "${response.message()}")
                                        Toast.makeText(requireActivity(), "${response.message()}", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }

                            override fun onFailure(call: Call<PerusahaanResponse>, t: Throwable) {
                                Log.e("Edit passperu Frag", "${t.message}")
                            }
                        })
//                        }
                    }
                }
                else{
                    Toast.makeText(requireActivity(), "password harus sama dengan confirm password", Toast.LENGTH_SHORT).show()
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
                val fragment = PerusahaanProfileFragment()
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_perusahaan, fragment).commit()
            }
        }
        return true
    }
}