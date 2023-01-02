package com.example.projectdisnaker.admin

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import com.example.projectdisnaker.R
import com.example.projectdisnaker.databinding.FragmentAdminDetailPendaftaranBinding
import java.text.SimpleDateFormat
import java.util.*

class AdminDetailPendaftaranFragment : Fragment() {
    private lateinit var binding: FragmentAdminDetailPendaftaranBinding
    private var cal = Calendar.getInstance()
    private lateinit var sdf: SimpleDateFormat

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAdminDetailPendaftaranBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //action bar
        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.setTitle("Pendaftaran")
        actionBar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)

        binding.tvTglWawancaraDet.setText("-") //awalya kosong

        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                sdf = SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH)
                binding.tvTglWawancaraDet.setText(sdf.format(cal.getTime()))
            }
        }

        binding.ivCalendarWawancara.setOnClickListener { //kl sdh wawancara di visible false
            var datePicker = DatePickerDialog(requireContext(), R.style.DialogTheme, dateSetListener,
                cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH))
            datePicker.datePicker.minDate = cal.timeInMillis
            datePicker.show()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                val fragment = AdminPendaftaranFragment()
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_admin, fragment).commit()
            }
        }
        return true
    }
}