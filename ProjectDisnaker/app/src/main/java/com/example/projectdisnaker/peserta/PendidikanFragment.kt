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
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projectdisnaker.R
import com.example.projectdisnaker.api.*
import com.example.projectdisnaker.databinding.FragmentPendidikanBinding
import com.example.projectdisnaker.perusahaan.PerusahaanDetailLowonganFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PendidikanFragment : Fragment() {
    private lateinit var binding: FragmentPendidikanBinding
    private lateinit var user: UserResponseItem
    private lateinit var peserta: PesertaItem
    private var listPendidikan: ArrayList<String> = ArrayList()
    private lateinit var spinnerAdapter: ArrayAdapter<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPendidikanBinding.inflate(inflater, container, false)
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

        binding.llPendidikan.visibility = View.GONE

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

        spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, listPendidikan)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerPendidikan.adapter = spinnerAdapter
        
        binding.btnEditPendidikan.setOnClickListener { 
            var pendidikan = binding.spinnerPendidikan.selectedItem.toString()
            var jurusan = binding.etJurusan.text.toString()
            var nilai = binding.etNilai.text.toString()
            
            if(pendidikan!="" && jurusan!="" && nilai!=""){
                var edited = PendidikanItem(pendidikan, jurusan, nilai.toInt())

                var client = ApiConfiguration.getApiService().updatePendidikan(user.pesertaId!!, edited)
                client.enqueue(object: Callback<PendidikanResponse> {
                    override fun onResponse(call: Call<PendidikanResponse>, response: Response<PendidikanResponse>){
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
                                tvDialog.setText("Berhasil mengubah riwayat pendidikan.")

                                btnOk.setOnClickListener {
                                    dialog.dismiss()
                                    fetchPeserta()
                                }
                            }
                        }
                        else{
                            Log.e("Pendidikan Fragment", "${response.message()}")
                        }
                    }
                    override fun onFailure(call: Call<PendidikanResponse>, t: Throwable) {
                        Log.e("Pendidikan Fragment", "${t.message}")
                    }
                })
            }
            else{
                Toast.makeText(requireActivity(), "Harap isi semua data", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                if(binding.spinnerPendidikan.selectedItem.toString()!=peserta.pendidikan
                    || binding.etJurusan.text.toString()!=peserta.jurusan
                    || binding.etNilai.text.toString().toInt()!=peserta.nilai){
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

    private fun fetchPendidikan(){
        var client = ApiConfiguration.getApiService().getPendidikan()
        client.enqueue(object: Callback<PendidikanResponse> {
            override fun onResponse(call: Call<PendidikanResponse>, response: Response<PendidikanResponse>){
                if(response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody!=null){
                        listPendidikan.clear()
                        var temp = responseBody.pendidikan!!.toMutableList()
                        for(t in temp){
                            listPendidikan.add(t!!.nama!!)
                        }
                        spinnerAdapter.notifyDataSetChanged()

                        binding.spinnerPendidikan.setSelection(spinnerAdapter.getPosition(peserta.pendidikan))

                        binding.avLoading.visibility = View.GONE
                        binding.llPendidikan.visibility = View.VISIBLE
                    }
                }
                else{
                    Log.e("Pendidikan Fragment", "${response.message()}")
                }
            }
            override fun onFailure(call: Call<PendidikanResponse>, t: Throwable) {
                Log.e("Pendidikan Fragment", "${t.message}")
            }
        })
    }

    private fun fetchPeserta(){
        var client = ApiConfiguration.getApiService().getPeserta(user.pesertaId!!)
        client.enqueue(object: Callback<PesertaResponse> {
            override fun onResponse(call: Call<PesertaResponse>, response: Response<PesertaResponse>){
                if(response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody!=null){
                        peserta = responseBody.peserta!![0]!!

                        binding.etJurusan.setText(peserta.jurusan)
                        binding.etNilai.setText(peserta.nilai.toString())

                        fetchPendidikan()
                    }
                }
                else{
                    Log.e("Pendidikan Fragment", "${response.message()}")
                }
            }
            override fun onFailure(call: Call<PesertaResponse>, t: Throwable) {
                Log.e("Pendidikan Fragment", "${t.message}")
            }
        })
    }
}