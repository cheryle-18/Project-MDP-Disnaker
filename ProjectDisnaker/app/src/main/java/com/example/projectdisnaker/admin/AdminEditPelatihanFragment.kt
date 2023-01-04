package com.example.projectdisnaker.admin

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectdisnaker.R
import com.example.projectdisnaker.api.*
import com.example.projectdisnaker.databinding.FragmentAdminEditPelatihanBinding
import com.example.projectdisnaker.rv.RVTambahSyaratAdapter
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
        binding.etKuotaPelatihan.setText(pelatihan.kuota.toString())
        binding.etDurasiPelatihan.setText(pelatihan.durasi.toString())

        if(pelatihan.status == 0){
            binding.rbTdkAktif.isChecked = true
            binding.rbAktif.isChecked = false
        }
        else{
            binding.rbAktif.isChecked = true
            binding.rbTdkAktif.isChecked = false
        }


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                val fragment = AdminDetailPelatihanFragment()
                val bundle = Bundle()
                bundle.putParcelable("pelatihan", pelatihan)
                fragment.arguments = bundle
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_admin, fragment).commit()
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