package com.example.projectdisnaker.peserta

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.app.DatePickerDialog
import android.widget.DatePicker
import com.example.projectdisnaker.R
import com.example.projectdisnaker.api.UserResponseItem
import com.example.projectdisnaker.databinding.FragmentProfileBinding
import java.text.SimpleDateFormat
import java.util.*

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var user: UserResponseItem
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

        binding.etEmailProfile.setText(user.email)
        binding.etTelpProfile.setText(user.telp)
        binding.etKtpProfile.setText(user.nik)
        binding.etTglLahirProfile.setText(user.tglLahir)

        var tglLahir = user.tglLahir!!.split("/")
        Toast.makeText(requireContext(), "${tglLahir[1]}", Toast.LENGTH_SHORT).show()
        cal.set(tglLahir[2].toInt(), tglLahir[1].toInt()-1, tglLahir[0].toInt())

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
            binding.etEmailProfile.setText(user.email)
            binding.etTelpProfile.setText(user.telp)
            binding.etKtpProfile.setText(user.nik)
            binding.etTglLahirProfile.setText(user.tglLahir)
            if ( binding.etEmailProfile.text.isBlank() &&
                binding.etTelpProfile.text.isBlank() &&
                binding.etKtpProfile.text.isBlank() &&
                binding.etTglLahirProfile.text.isBlank()){
                Toast.makeText(requireActivity(), "Error", Toast.LENGTH_SHORT).show()
            }
            else{

            }
        }
    }
}