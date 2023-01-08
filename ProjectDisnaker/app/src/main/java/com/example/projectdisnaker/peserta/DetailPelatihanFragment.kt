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
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectdisnaker.R
import com.example.projectdisnaker.admin.AdminPelatihanFragment
import com.example.projectdisnaker.api.ApiConfiguration
import com.example.projectdisnaker.api.PelatihanItem
import com.example.projectdisnaker.api.PelatihanResponse
import com.example.projectdisnaker.api.UserResponseItem
import com.example.projectdisnaker.databinding.FragmentDetailPelatihanBinding
import com.example.projectdisnaker.rv.RVSyaratAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailPelatihanFragment : Fragment() {
    private lateinit var binding: FragmentDetailPelatihanBinding
    private lateinit var pelatihan: PelatihanItem
    private var syaratPelatihan: ArrayList<String> = ArrayList()
    private lateinit var syaratAdapter: RVSyaratAdapter
    private var peluangPelatihan: ArrayList<String> = ArrayList()
    private lateinit var peluangAdapter: RVSyaratAdapter
    private lateinit var user: UserResponseItem


    fun setDisable(text:String){
        binding.btnDaftarPelatihan.isEnabled = false
        binding.btnDaftarPelatihan.setText(text)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailPelatihanBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //action bar
        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.setTitle("Pelatihan")
        actionBar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)
        binding.linearLayout4.bringToFront()

        requireActivity().onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    val fragment = PelatihanFragment()
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment).commit()
                }
            })

        pelatihan = requireArguments().getParcelable<PelatihanItem>("pelatihan")!!
        user = (activity as HomeActivity).user
        binding.tvNamaDetail.setText(pelatihan.nama)
        binding.tvKategoriDetail.setText(pelatihan.kategori)
        binding.tvKuotaDetail.setText(pelatihan.kuota.toString())
        binding.tvDurasiDetail.setText(pelatihan.durasi.toString())
        binding.tvMinPendidikan.setText(pelatihan.pendidikan)
        binding.tvKeterangan.setText(pelatihan.keterangan)

        for(syarat in pelatihan.syarat!!){
            syaratPelatihan.add(syarat!!.deskripsi!!)
        }
        syaratAdapter = RVSyaratAdapter(syaratPelatihan, requireContext())
        binding.rvSyaratPelatihan.adapter = syaratAdapter
        binding.rvSyaratPelatihan.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        for(peluang in pelatihan.peluang!!){
            peluangPelatihan.add(peluang!!.nama!!)
        }
        peluangAdapter = RVSyaratAdapter(peluangPelatihan, requireContext())
        binding.rvPeluangKerja.adapter = peluangAdapter
        binding.rvPeluangKerja.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        if(pelatihan.stat!! == 2){
            setDisable("Anda telah mendaftar")
        }
        else if(pelatihan.stat!! == 1){
            setDisable("Menunggu konfirmasi pendaftaran")
        }

        binding.btnDaftarPelatihan.setOnClickListener {
            val dialogBinding = layoutInflater.inflate(R.layout.confirm_dialog, null)
            val dialog = Dialog(requireContext())
            dialog.setContentView(dialogBinding)
            dialog.setCancelable(true)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()

            val btnConfirm = dialogBinding.findViewById<Button>(R.id.btnConfirmDialog)
            val btnCancel = dialogBinding.findViewById<Button>(R.id.btnCancelDialog)
            val tvDialog = dialogBinding.findViewById<TextView>(R.id.tvDialogConfirm)

            tvDialog.setText("Apakah anda yakin ingin mendaftar pelatihan?")
            btnCancel.setText("Tidak")
            btnConfirm.setText("Ya")

            btnCancel.setOnClickListener {
                dialog.dismiss()
            }
            btnConfirm.setOnClickListener {
                dialog.dismiss()
                var client = ApiConfiguration.getApiService().daftarPelatihan(user.apiKey!!,pelatihan.pelatihanId!!)
                client.enqueue(object: Callback<PelatihanResponse> {
                    override fun onResponse(call: Call<PelatihanResponse>, response: Response<PelatihanResponse>){
                        if(response.isSuccessful){
                            val responseBody = response.body()
                            if(responseBody!=null){
                                if(responseBody.message == "1"){
                                    val dialogBinding = layoutInflater.inflate(R.layout.success_dialog, null)
                                    val dialog = Dialog(requireContext())
                                    dialog.setContentView(dialogBinding)
                                    dialog.setCancelable(true)
                                    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                                    dialog.show()

                                    val btnOk = dialogBinding.findViewById<Button>(R.id.btOkDialog)
                                    val tvDialog = dialogBinding.findViewById<TextView>(R.id.tvDialog)
                                    tvDialog.setText("Berhasil mendaftar pelatihan")

                                    btnOk.setOnClickListener {
                                        dialog.dismiss()
                                        setDisable("Menunggu konfirmasi pendaftaran")
//                                        val fragment = PelatihanFragment()
//                                        requireActivity().supportFragmentManager.beginTransaction()
//                                            .replace(R.id.fragment_container, fragment).commit()

                                    }
                                }
                                else{
                                    Toast.makeText(requireContext(),responseBody.message,Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                        else{
                            Log.e("", "${response.message()}")
                            Toast.makeText(requireActivity(), "${response.message()}", Toast.LENGTH_SHORT).show()
                        }
                    }
                    override fun onFailure(call: Call<PelatihanResponse>, t: Throwable) {
                        Log.e("", "${t.message}")
                        Toast.makeText(requireActivity(), "${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                val fragment = PelatihanFragment()
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment).commit()
            }
        }
        return true
    }
}