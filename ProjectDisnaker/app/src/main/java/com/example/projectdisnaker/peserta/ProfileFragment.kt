package com.example.projectdisnaker.peserta

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.app.DatePickerDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import com.example.projectdisnaker.R
import com.example.projectdisnaker.api.*
import com.example.projectdisnaker.databinding.FragmentProfileBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var user: UserResponseItem
    private lateinit var peserta: PesertaItem
    private var cal = Calendar.getInstance()
    private lateinit var sdf: SimpleDateFormat

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)
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

        user = (activity as HomeActivity).user
        binding.tvNamaProfile.setText(user.nama)
        binding.tvUsernameProfile.setText(user.username)

        var nama = user.nama!!.trim().split("\\s+".toRegex()).toTypedArray()
        var initials = ""
        for(n in nama){
            initials += n.substring(0, 1)
        }
        binding.tvInisial.setText(initials)

        fetchPeserta()

        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
                binding.etTglLahirProfile.setText(sdf.format(cal.getTime()))
            }
        }

        binding.ivCalendarProfile.setOnClickListener {
            DatePickerDialog(requireContext(), R.style.DialogTheme, dateSetListener,
                cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        binding.layoutUbahPassword.setOnClickListener {
            val fragment = PasswordFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment).commit()
        }

        binding.layoutPendidikan.setOnClickListener {
            val fragment = PendidikanFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment).commit()
        }

        binding.layoutPelatihan.setOnClickListener {
            val fragment = RiwayatPelatihanFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment).commit()
        }

        binding.layoutPekerjaan.setOnClickListener {
            val fragment = RiwayatPekerjaanFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment).commit()
        }

        binding.layoutTidakKerja.setOnClickListener {
            //dialog
        }

        binding.layoutLogout.setOnClickListener{
            requireActivity().finish()
        }

        binding.btnEditProfile.setOnClickListener {
            if ( binding.etEmailProfile.text.isBlank() &&
                binding.etTelpProfile.text.isBlank() &&
                binding.etKtpProfile.text.isBlank() &&
                binding.etTglLahirProfile.text.isBlank()){
                Toast.makeText(requireActivity(), "Pastikan semua data diri terisi", Toast.LENGTH_SHORT).show()
            }
            else{
                var email = binding.etEmailProfile.text.toString()
                var telp = binding.etTelpProfile.text.toString()
                var nik = binding.etKtpProfile.text.toString()

                sdf = SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH)
                var tglLahir = sdf.format(cal.getTime())

                var edited = PesertaItem(nik, telp, email, tglLahir)

                var client = ApiConfiguration.getApiService().updatePeserta(user.pesertaId!!, edited)
                client.enqueue(object: Callback<PesertaResponse> {
                    override fun onResponse(call: Call<PesertaResponse>, response: Response<PesertaResponse>) {
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
                                tvDialog.setText("Berhasil mengubah profil.")

                                btnOk.setOnClickListener {
                                    dialog.dismiss()
                                    fetchPeserta()
                                }
                            }
                            else{
                                Log.e("Edit Profile Frag", "${response.message()}")
                                Toast.makeText(requireActivity(), "${response.message()}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                    override fun onFailure(call: Call<PesertaResponse>, t: Throwable) {
                        Log.e("Edit Profile Frag", "${t.message}")
                    }
                })
            }
        }
    }

    private fun fillDetails(){
        binding.etEmailProfile.setText(peserta.email)
        binding.etTelpProfile.setText(peserta.telp)
        binding.etKtpProfile.setText(peserta.nik)
        binding.etTglLahirProfile.setText(peserta.tglLahir)

        var tglLahir = user.tglLahir!!.split("/")
        cal.set(tglLahir[2].toInt(), tglLahir[1].toInt()-1, tglLahir[0].toInt())
    }

    private fun fetchPeserta(){
        var client = ApiConfiguration.getApiService().getPeserta(user.pesertaId!!)
        client.enqueue(object: Callback<PesertaResponse> {
            override fun onResponse(call: Call<PesertaResponse>, response: Response<PesertaResponse>){
                if(response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody!=null){
                        peserta = responseBody.peserta!![0]!!
                        fillDetails()
                    }
                }
                else{
                    Log.e("Profile Fragment", "${response.message()}")
                }
            }
            override fun onFailure(call: Call<PesertaResponse>, t: Throwable) {
                Log.e("Profile Fragment", "${t.message}")
            }
        })
    }
}