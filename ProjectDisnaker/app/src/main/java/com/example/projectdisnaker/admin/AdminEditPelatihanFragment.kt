package com.example.projectdisnaker.admin

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectdisnaker.R
import com.example.projectdisnaker.api.*
import com.example.projectdisnaker.databinding.FragmentAdminEditPelatihanBinding
import com.example.projectdisnaker.peserta.ProfileFragment
import com.example.projectdisnaker.rv.RVTambahSyaratAdapter
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminEditPelatihanFragment : Fragment() {
    private lateinit var binding: FragmentAdminEditPelatihanBinding
    private lateinit var pelatihan: PelatihanItem
    private lateinit var listKategori : List<KategoriItem>
    private lateinit var listKategoriString : ArrayList<String>
    private lateinit var listPendidikan : List<PendidikanItem>
    private lateinit var listPendidikanString : ArrayList<String>
    private lateinit var adapterSyarat : RVTambahSyaratAdapter
    private lateinit var listSyarat : ArrayList<String>
    private var mode = "add"
    private var idxEdit = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listKategori = listOf()
        listKategoriString = ArrayList()
        listPendidikan = listOf()
        listPendidikanString = ArrayList()
        listSyarat = arrayListOf()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAdminEditPelatihanBinding.inflate(inflater, container, false)
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
        pelatihan = requireArguments().getParcelable<PelatihanItem>("pelatihan")!!

        for (i in pelatihan.syarat!!.indices){
            listSyarat.add(pelatihan.syarat!![i].deskripsi!!)
        }

        loadKategori()
        loadPendidikan()
        initRV()


        binding.etNamaPelatihanEdit.setText(pelatihan.nama)
        binding.etKuotaPelatihanEdit.setText(pelatihan.kuota.toString())
        binding.etDurasiPelatihanEdit.setText(pelatihan.durasi.toString())
        binding.etKeteranganPelatihanEdit.setText(pelatihan.keterangan)

        if(pelatihan.status == 0){
            binding.rbTdkAktif.isChecked = true
            binding.rbAktif.isChecked = false
        }
        else{
            binding.rbAktif.isChecked = true
            binding.rbTdkAktif.isChecked = false
        }

        binding.btnTambahSyaratPelatihanEdit.setOnClickListener {

            var syarat = binding.etSyaratPelatihan.text.toString()
            if(syarat!=""){
                if(mode=="add"){
                    listSyarat.add(syarat)
                    adapterSyarat.notifyDataSetChanged()
                }
                else if(mode=="edit" && idxEdit!=-1){
                    listSyarat.set(idxEdit, syarat)
                    adapterSyarat.notifyDataSetChanged()
                }
                binding.etSyaratPelatihan.setText("")
                mode = "add"
                idxEdit = -1
                binding.btnTambahSyaratPelatihanEdit.setImageResource(R.drawable.ic_baseline_add_24)
            }
        }

        binding.btnEditPel.setOnClickListener {
            var kategori = binding.spinnerKategoriEdit.selectedItem.toString()
            var kuotaku = binding.etKuotaPelatihanEdit.text.toString()
            var durasiku = binding.etDurasiPelatihanEdit.text.toString()
            var min = binding.spinnerPendidikanEdit.selectedItem.toString()
            var ket = binding.etKeteranganPelatihanEdit.text.toString()
            var nama = binding.etNamaPelatihanEdit.text.toString()

            if(kategori!="" && min!="" && nama!="" && kuotaku!="" && durasiku!=""){
                var kuota = kuotaku.toInt()
                var durasi = durasiku.toInt()
                if(kuota >0 && durasi >0){
                    var syaratArr = ArrayList<PelatihanSyaratItem>()
                    for(s in listSyarat){
                        syaratArr.add(PelatihanSyaratItem(s))
                    }

                    pelatihan.nama = nama
                    pelatihan.kategori = kategori
                    pelatihan.kuota = kuota
                    pelatihan.durasi = durasi
                    pelatihan.pendidikan = min

                    if(binding.rbAktif.isChecked){
                        pelatihan.status = 1
                    }
                    else{
                        pelatihan.status = 0
                    }
                    //send to db
                    pelatihan.syarat = syaratArr
                    var client = ApiConfiguration.getApiService()
                        .editPelatihan(pelatihan)
                    client.enqueue(object: Callback<PelatihanResponse> {
                        override fun onResponse(call: Call<PelatihanResponse>, response: Response<PelatihanResponse>) {
                            if(response.isSuccessful){
                                val responseBody = response.body()
                                if(responseBody!=null){
                                    if(responseBody.message == "-2"){
                                        Toast.makeText(requireContext(),"Tidak dapat mengaktifkan pelatihan dengan kuota yang sudah penuh!",Toast.LENGTH_SHORT).show()
                                    }
                                    else{
                                        val dialogBinding = layoutInflater.inflate(R.layout.success_dialog, null)
                                        val dialog = Dialog(requireContext())
                                        dialog.setContentView(dialogBinding)
                                        dialog.setCancelable(true)
                                        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                                        dialog.show()

                                        val btnOk = dialogBinding.findViewById<Button>(R.id.btOkDialog)
                                        val tvDialog = dialogBinding.findViewById<TextView>(R.id.tvDialog)
                                        tvDialog.setText("Berhasil edit pelatihan.")

                                        pelatihan = responseBody.pelatihan?.get(0)!!

                                        btnOk.setOnClickListener {
                                            dialog.dismiss()
                                            val fragment = AdminDetailPelatihanFragment()
                                            val bundle = Bundle()
                                            bundle.putParcelable("pelatihan", pelatihan)
                                            fragment.arguments = bundle
                                            requireActivity().supportFragmentManager.beginTransaction()
                                                .replace(R.id.fragment_container_admin, fragment).commit()
                                        }
                                    }


                                }
                                else{
                                    Log.e("edit pelatihan Frag", "${response.message()}")
                                }
                            }
                        }

                        override fun onFailure(call: Call<PelatihanResponse>, t: Throwable) {
                            Log.e("edit pelatihan Frag", "${t.message}")
                        }
                    })
                }

                else{
                    Toast.makeText(requireContext(),"Kuota atau durasi harus > 0!", Toast.LENGTH_SHORT).show()
                }
            }
            else{
                Toast.makeText(requireContext(),"Semua field harus diisi!",Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
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
                    val fragment = AdminDetailPelatihanFragment()
                    val bundle = Bundle()
                    bundle.putParcelable("pelatihan", pelatihan)
                    fragment.arguments = bundle
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_admin, fragment).commit()
                }
            }
        }
        return true
    }


    fun initSpinner(){
        val adapter: ArrayAdapter<String> = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            listKategoriString
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerKategoriEdit.adapter = adapter
        val spinnerPosition = adapter.getPosition(pelatihan.kategori)
        binding.spinnerKategoriEdit.setSelection(spinnerPosition)

        val adapterPendidikan: ArrayAdapter<String> = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            listPendidikanString
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerPendidikanEdit.adapter = adapterPendidikan
        val spinnerPosition2 = adapter.getPosition(pelatihan.pendidikan)
        binding.spinnerPendidikanEdit.setSelection(spinnerPosition2)
    }

    fun loadKategori(){
        //fetch from db
        var client = ApiConfiguration.getApiService().getKategori()
        client.enqueue(object: Callback<KategoriResponse> {
            override fun onResponse(call: Call<KategoriResponse>, response: Response<KategoriResponse>) {
                if(response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody!=null){
                        listKategori = responseBody.kategori!!
                        for (i in listKategori.indices){
                            listKategoriString.add(listKategori[i]!!.nama!!)
                        }
                        initSpinner()
                    }
                    else{
                        println("${response.message()}")
                    }
                }
                else{
                    Log.d("Error kat pel",response.toString())
                }
            }

            override fun onFailure(call: Call<KategoriResponse>, t: Throwable) {
                Log.d("Error kat pel", "${t.message}")
            }
        })
    }
    fun loadPendidikan(){
        //fetch from db
        var client = ApiConfiguration.getApiService().getPendidikan()
        client.enqueue(object: Callback<PendidikanResponse> {
            override fun onResponse(call: Call<PendidikanResponse>, response: Response<PendidikanResponse>) {
                if(response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody!=null){
                        listPendidikan = responseBody.pendidikan!!
                        for (i in listPendidikan.indices){
                            listPendidikanString.add(listPendidikan[i]!!.nama!!)
                        }
                        initSpinner()
                    }
                    else{
                        println("${response.message()}")
                    }
                }
                else{
                    Log.d("Error pendidikan",response.toString())
                }
            }

            override fun onFailure(call: Call<PendidikanResponse>, t: Throwable) {
                Log.d("Error pendidikan", "${t.message}")
            }
        })
    }

    fun initRV(){
        adapterSyarat = RVTambahSyaratAdapter(listSyarat,requireContext()){
                idx, mode ->
            if(mode=="edit"){
                var syarat = listSyarat.get(idx)
                binding.etSyaratPelatihan.setText(syarat)
                this.mode = "edit"
                idxEdit = idx
                binding.btnTambahSyaratPelatihanEdit.setImageResource(R.drawable.ic_baseline_edit_24)
            }
            else if(mode=="delete"){
                listSyarat.removeAt(idx)
                adapterSyarat.notifyDataSetChanged()
            }
        }
        binding.rvSyaratPelatihanEdit.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvSyaratPelatihanEdit.adapter = adapterSyarat
    }

}