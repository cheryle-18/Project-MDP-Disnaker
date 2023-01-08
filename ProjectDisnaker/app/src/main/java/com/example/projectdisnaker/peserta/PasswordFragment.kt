package com.example.projectdisnaker.peserta

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
import com.example.projectdisnaker.databinding.FragmentPasswordBinding
import com.example.projectdisnaker.perusahaan.PerusahaanLowonganFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PasswordFragment : Fragment() {
    private lateinit var binding: FragmentPasswordBinding
    private lateinit var user: UserResponseItem

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPasswordBinding.inflate(inflater, container, false)
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

        requireActivity().onBackPressedDispatcher
        .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val fragment = ProfileFragment()
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment).commit()
            }
        })

        user = (activity as HomeActivity).user

        binding.tvNamaProfile.setText(user.nama)
        binding.tvUsernameProfile.setText(user.username)

        var nama = user.nama!!.trim().split("\\s+".toRegex()).toTypedArray()
        var initials = ""
        for(n in nama){
            initials += n.substring(0, 1)
        }
        binding.tvInisial.setText(initials)


        binding.btnUbahPass.setOnClickListener{
            var passbaru =binding.etPassBaru.text.toString()
            var confpass =binding.etConfBaru.text.toString()
            var passlama =binding.etPassLama.text.toString()

            if (binding.etPassBaru.text.toString().isNotBlank()&&
                    binding.etConfBaru.text.toString().isNotBlank()&&
                binding.etPassLama.text.toString().isNotBlank()){
                if (passbaru==confpass){
                    var edited = PasswordItem(passbaru, passlama)

                    var client = ApiConfiguration.getApiService().updatePasswordPerserta(user.apiKey!!, edited)
                    client.enqueue(object: Callback<PesertaResponse> {
                        override fun onResponse(call: Call<PesertaResponse>, response: Response<PesertaResponse>) {
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

                                        binding.etPassBaru.setText("")
                                        binding.etConfBaru.setText("")
                                        binding.etPassLama.setText("")

                                        btnOk.setOnClickListener {
                                            dialog.dismiss()
                                            val fragment = ProfileFragment()
                                            requireActivity().supportFragmentManager.beginTransaction()
                                                .replace(R.id.fragment_container, fragment).commit()
                                        }
                                    }
                                    else{
                                        Toast.makeText(requireActivity(), "${responseBody.message}",
                                            Toast.LENGTH_SHORT).show()
                                    }
                                }
                                else{
                                    Log.e("Edit PassPeserta Frag", "${response.message()}")
                                }
                            }
                        }

                        override fun onFailure(call: Call<PesertaResponse>, t: Throwable) {
                            Log.e("Edit PassPeserta Frag", "${t.message}")
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
                if(binding.etPassBaru.text.toString()!="" || binding.etConfBaru.text.toString()!=""
                    || binding.etPassLama.text.toString()!=""){
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
                        val fragment = ProfileFragment()
                        requireActivity().supportFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, fragment).commit()
                    }
                }
                else{
                    val fragment = ProfileFragment()
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment).commit()
                }
            }
        }
        return true
    }


}