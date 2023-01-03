package com.example.projectdisnaker.peserta

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectdisnaker.R
import com.example.projectdisnaker.api.*
import com.example.projectdisnaker.databinding.FragmentDetailPendaftaranBinding
import com.example.projectdisnaker.rv.RVSyaratAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailPendaftaranFragment : Fragment() {
    private lateinit var binding: FragmentDetailPendaftaranBinding
    private lateinit var pelatihan: PelatihanItem
    private var syaratPelatihan: ArrayList<String> = ArrayList()
    private lateinit var syaratAdapter: RVSyaratAdapter
    private var peluangPelatihan: ArrayList<String> = ArrayList()
    private lateinit var peluangAdapter: RVSyaratAdapter

    private var pelatihanId = -1
    private lateinit var status: StatusItem
    private var status_pendaftaran = arrayOf("Pendaftaran Awal", "Wawancara", "Pelatihan", "Selesai", "Ditolak")
    private var status_kelulusan = arrayOf("Menunggu","Diterima", "Ditolak")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailPendaftaranBinding.inflate(inflater, container, false)
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
        binding.linearLayout4.bringToFront()

        pelatihanId = requireArguments().getInt("pelatihan_id")
        status = requireArguments().getParcelable<StatusItem>("status")!!

        syaratAdapter = RVSyaratAdapter(syaratPelatihan, requireContext())
        binding.rvSyaratPelatihan.adapter = syaratAdapter
        binding.rvSyaratPelatihan.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        peluangAdapter = RVSyaratAdapter(peluangPelatihan, requireContext())
        binding.rvPeluangKerja.adapter = peluangAdapter
        binding.rvPeluangKerja.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        fetchPelatihan()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                val fragment = PendaftaranFragment()
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment).commit()
            }
        }
        return true
    }

    private fun fillDetails(){
        binding.tvStatPendaftaran.setText(status_pendaftaran[status.statusPendaftaran!!])
        binding.tvTglDaftar.setText(status.tglPendaftaran)
        binding.tvTglWawancara.setText(status.tglWawancara)
        binding.tvStatKelulusan.setText(status_kelulusan[status.statusKelulusan!!])
        if(status.statusKelulusan==1){
            binding.tvStatKelulusan.setTextColor(resources.getColor(R.color.green_900))
        }
        else if(status.statusKelulusan==2){
            binding.tvStatKelulusan.setTextColor(resources.getColor(R.color.red))
        }

        binding.tvNamaDetail.setText(pelatihan.nama)
        binding.tvKategoriDetail.setText(pelatihan.kategori)
        binding.tvKuotaDetail.setText(pelatihan.kuota.toString())
        binding.tvDurasiDetail.setText(pelatihan.durasi.toString())
        binding.tvMinPendidikan.setText(pelatihan.pendidikan)
        binding.tvKeterangan.setText(pelatihan.keterangan)
    }

    private fun fetchPelatihan(){
        var client = ApiConfiguration.getApiService().getOnePelatihan(pelatihanId)
        client.enqueue(object: Callback<PelatihanResponse> {
            override fun onResponse(call: Call<PelatihanResponse>, response: Response<PelatihanResponse>){
                if(response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody!=null){
                        if(responseBody.pelatihan!!.size > 0){
                            pelatihan = responseBody.pelatihan!![0]
                            fillDetails()

                            syaratPelatihan.clear()
                            for(s in pelatihan.syarat!!){
                                syaratPelatihan.add(s.deskripsi!!)
                            }
                            syaratAdapter.notifyDataSetChanged()

                            peluangPelatihan.clear()
                            for(p in pelatihan.peluang!!){
                                peluangPelatihan.add(p.nama!!)
                            }
                            peluangAdapter.notifyDataSetChanged()
                        }
                        else{
                        }
                    }
                }
                else{
                    Log.e("Det Pendaftaran Frag", "${response.message()}")
                }
            }
            override fun onFailure(call: Call<PelatihanResponse>, t: Throwable) {
                Log.e("Det Pendaftaran Frag", "${t.message}")
                Toast.makeText(requireActivity(), "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}