package com.example.projectdisnaker.admin

import android.app.DatePickerDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.ColorSpace.Rgb
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.projectdisnaker.R
import com.example.projectdisnaker.api.ApiConfiguration
import com.example.projectdisnaker.api.LowonganResponse
import com.example.projectdisnaker.api.PelatihanItem
import com.example.projectdisnaker.api.PendaftaranPelatihanItem
import com.example.projectdisnaker.databinding.FragmentAdminDetailPendaftaranBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class AdminDetailPendaftaranFragment : Fragment() {
    private lateinit var binding: FragmentAdminDetailPendaftaranBinding
    private var cal = Calendar.getInstance()
    private lateinit var sdf: SimpleDateFormat
    private var status_pendaftaran = arrayOf("Pendaftaran Awal", "Wawancara", "Pelatihan", "Selesai", "Ditolak")
    private var status_kelulusan = arrayOf("Menunggu","Diterima", "Ditolak")
    lateinit var pendaftaran:PendaftaranPelatihanItem

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

        pendaftaran = requireArguments().getParcelable<PendaftaranPelatihanItem>("pendaftaran")!!

        //action bar
        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.setTitle("Pendaftaran")
        actionBar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)

        binding.tvTglWawancaraDet.setText("-") //awalya kosong
        checkStatusVisible()

        binding.tvTglDaftarDet.setText(pendaftaran.tgl_pendaftaran.toString())
        binding.tvTahapDaftarDet.setText(status_pendaftaran[pendaftaran.status_pendaftaran!!])
        binding.tvStatusLulusDet.setText(status_kelulusan[pendaftaran.status_kelulusan!!])

        //pelatihan
        binding.tvNamaPelatihanDet.setText(pendaftaran.pelatihan!!.nama)
        binding.tvNamaKategoriDet.setText(pendaftaran.kategori)
        binding.tvKuotaTotalDet.setText(pendaftaran.pelatihan!!.kuota!!.toString() + " peserta")
        binding.tvKuotaSisaDet.setText(pendaftaran.sisa_kuota!!.toString() + " peserta")
        binding.tvDurasiDet.setText(pendaftaran.pelatihan!!.durasi!!.toString() + " hari")
        binding.tvMinPendDet.setText(pendaftaran.pelatihan!!.pendidikan!!)

        //peserta
        binding.tvNamaPesertaDet.setText(pendaftaran.peserta!!.nama!!)
        binding.tvUsiaDet.setText(pendaftaran.peserta!!.usia!!.toString() + " tahun")
        binding.tvPendDet.setText(pendaftaran.peserta!!.pendidikan!!)
        binding.tvJurusanDet.setText(pendaftaran.peserta!!.jurusan!!)
        binding.tvNilaiDet.setText(pendaftaran.peserta!!.nilai!!.toString())

        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
//                Log.d(tag, year.toString())
//                Log.d(tag, monthOfYear.toString())
//                Log.d(tag, dayOfMonth.toString())

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

        binding.btnTolakPendaftaran.setOnClickListener {
            val dialogBinding = layoutInflater.inflate(R.layout.confirm_dialog, null)
            val dialog = Dialog(requireContext())
            dialog.setContentView(dialogBinding)
            dialog.setCancelable(true)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()

            val btnConfirm = dialogBinding.findViewById<Button>(R.id.btnConfirmDialog)
            val btnCancel = dialogBinding.findViewById<Button>(R.id.btnCancelDialog)
            val tvDialog = dialogBinding.findViewById<TextView>(R.id.tvDialogConfirm)

            tvDialog.setText("Konfirmasi Tolak Pendaftaran Pelatihan Ini!")
            btnCancel.setText("Batal")
            btnConfirm.setText("Tolak")

            btnCancel.background.setTint(Color.rgb(27, 94, 32))
            btnConfirm.background.setTint(Color.RED)

            btnCancel.setOnClickListener {
                dialog.dismiss()
            }
            btnConfirm.setOnClickListener {
                dialog.dismiss()
//                var client = ApiConfiguration.getApiService().tutupLowongan(lowonganId)
//                client.enqueue(object: Callback<LowonganResponse> {
//                    override fun onResponse(call: Call<LowonganResponse>, response: Response<LowonganResponse>){
//                        if(response.isSuccessful){
//                            val responseBody = response.body()
//                            if(responseBody!=null){
//                                fetchCurrentLowongan()
//                            }
//                        }
//                        else{
//                            Log.e("", "${response.message()}")
//                            Toast.makeText(requireActivity(), "${response.message()}", Toast.LENGTH_SHORT).show()
//                        }
//                    }
//                    override fun onFailure(call: Call<LowonganResponse>, t: Throwable) {
//                        Log.e("", "${t.message}")
//                        Toast.makeText(requireActivity(), "${t.message}", Toast.LENGTH_SHORT).show()
//                    }
//                })
            }
//            val adminDetailPendaftaranFragment = AdminDetailPendaftaranFragment()
//            var bundle = Bundle()
//            bundle.putParcelable("pendaftaran", pendaftaran)
//            adminDetailPendaftaranFragment.arguments = bundle
//            requireActivity().supportFragmentManager.beginTransaction()
//                .replace(R.id.fragment_container_admin, adminDetailPendaftaranFragment)
//                .commit()
        }

        binding.btnTerimaPendaftaran.setOnClickListener{
            val dialogBinding = layoutInflater.inflate(R.layout.confirm_dialog, null)
            val dialog = Dialog(requireContext())
            dialog.setContentView(dialogBinding)
            dialog.setCancelable(true)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()

            val btnConfirm = dialogBinding.findViewById<Button>(R.id.btnConfirmDialog)
            val btnCancel = dialogBinding.findViewById<Button>(R.id.btnCancelDialog)
            val tvDialog = dialogBinding.findViewById<TextView>(R.id.tvDialogConfirm)

            tvDialog.setText("Konfirmasi Terima Pendaftaran Pelatihan Ini dari status " +
                    "${status_pendaftaran[pendaftaran.status_pendaftaran!!]} menjadi " +
                    "${status_pendaftaran[pendaftaran.status_pendaftaran!!+1]}!")
            btnCancel.setText("Batal")
            btnConfirm.setText("Terima")

            btnCancel.setOnClickListener {
                dialog.dismiss()
            }
            btnConfirm.setOnClickListener {
                dialog.dismiss()
                if(pendaftaran.status_pendaftaran==0){
                    if(binding.tvTglWawancaraDet.text.toString() == "-"){
                        Toast.makeText(requireContext(), "Harap Masukkan Tanggal Wawancara!", Toast.LENGTH_SHORT).show()
                    }
                }
                else{
//                var client = ApiConfiguration.getApiService().tutupLowongan(lowonganId)
//                client.enqueue(object: Callback<LowonganResponse> {
//                    override fun onResponse(call: Call<LowonganResponse>, response: Response<LowonganResponse>){
//                        if(response.isSuccessful){
//                            val responseBody = response.body()
//                            if(responseBody!=null){
//                                fetchCurrentLowongan()
//                            }
//                        }
//                        else{
//                            Log.e("", "${response.message()}")
//                            Toast.makeText(requireActivity(), "${response.message()}", Toast.LENGTH_SHORT).show()
//                        }
//                    }
//                    override fun onFailure(call: Call<LowonganResponse>, t: Throwable) {
//                        Log.e("", "${t.message}")
//                        Toast.makeText(requireActivity(), "${t.message}", Toast.LENGTH_SHORT).show()
//                    }
//                })
                }
            }
        }
    }

    fun checkStatusVisible(){
        if(pendaftaran.status_pendaftaran!!>=1){
            binding.tvTglWawancaraDet.setText(pendaftaran.tgl_wawancara)
            binding.ivCalendarWawancara.isVisible = false
        }

        if(pendaftaran.status_pendaftaran!!>=3 || pendaftaran.status_kelulusan==2){
            binding.btnTolakPendaftaran.isVisible = false
            binding.btnTerimaPendaftaran.isVisible = false
            binding.ivCalendarWawancara.isVisible = false
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