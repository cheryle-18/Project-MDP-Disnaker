package com.example.projectdisnaker.perusahaan

import android.app.Dialog
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
import androidx.appcompat.app.AppCompatActivity
import com.example.projectdisnaker.R
import com.example.projectdisnaker.api.*
import com.example.projectdisnaker.databinding.FragmentPerusahaanProfileBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PerusahaanProfileFragment : Fragment() {
    private lateinit var binding: FragmentPerusahaanProfileBinding
    private lateinit var user: UserResponseItem
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
        user = (activity as PerusahaanActivity).user

        binding.etAlamatPerusahaanProfile.setText(user.alamat)
        binding.etTelpPerusahaanProfile.setText(user.telp)
        binding.etEmailPerusahaanProfile.setText(user.email)

        binding.layoutUbahPassPerusahaan.setOnClickListener {
            val fragment = PerusahaanPasswordFragment()
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment_container_perusahaan, fragment).commit()
        }

        binding.btnEditPerusahaan.setOnClickListener{
            if (binding.etAlamatPerusahaanProfile.text.toString().isBlank()&&
                    binding.etTelpPerusahaanProfile.text.toString().isBlank()&&
                    binding.etEmailPerusahaanProfile.text.toString().isBlank()){
                Toast.makeText(requireActivity(), "Error", Toast.LENGTH_SHORT).show()
            }
            else{
                var Alamat = binding.etAlamatPerusahaanProfile.text.toString()
                var Telp = binding.etTelpPerusahaanProfile.text.toString()
                var Email = binding.etEmailPerusahaanProfile.text.toString()

//                var perusahaan = UserResponseItem(null, Telp, null,null,null, null,
//                    Email, null, Alamat,null,null,null,null ,null,null,null,null)

                var client = ApiConfiguration.getApiService()
                    .updatePerusahaan(user.userId!!,Alamat,Telp,Email)
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
                                tvDialog.setText("Berhasil Edit Profil Perusahaan.")

                                btnOk.setOnClickListener {
                                    dialog.dismiss()
                                    val fragment = PerusahaanProfileFragment()
                                    val bundle = Bundle()
                                    fragment.arguments = bundle
                                    requireActivity().supportFragmentManager.beginTransaction()
                                        .replace(R.id.fragment_container_perusahaan, fragment).commit()
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
            requireActivity().finish()
        }
    }
}