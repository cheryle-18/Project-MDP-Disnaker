package com.example.projectdisnaker.rv

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectdisnaker.R
import com.example.projectdisnaker.api.RiwayatItem

class RVRiwayatAdapter(
    private val listRiwayat: MutableList<RiwayatItem?>,
    val context: Context
) : RecyclerView.Adapter<RVRiwayatAdapter.ListViewHolder>() {
    inner class ListViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val tvNamaRiwayat: TextView = view.findViewById(R.id.tvNamaRiwayat)
        val tvPerusahaanRiwayat: TextView = view.findViewById(R.id.tvPerusahaanRiwayat)
        val tvKategoriRiwayat: TextView = view.findViewById(R.id.tvKategoriRiwayat)
        val tvTanggalRiwayat: TextView = view.findViewById(R.id.tvTanggalRiwayat)

        init{
            view.setOnClickListener{
                val riwayat = listRiwayat[adapterPosition]
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val item = LayoutInflater.from(context).inflate(R.layout.riwayat_list_item, parent, false)
        return ListViewHolder(item)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val riwayat = listRiwayat[position]
        holder.tvNamaRiwayat.setText(riwayat!!.nama)
        holder.tvKategoriRiwayat.setText(riwayat!!.kategori)
        holder.tvTanggalRiwayat.setText(riwayat!!.tanggal)
        if(riwayat!!.perusahaan==null){
            holder.tvPerusahaanRiwayat.visibility = View.GONE
        }
        else{
            holder.tvPerusahaanRiwayat.setText(riwayat!!.perusahaan)
        }
    }

    override fun getItemCount(): Int {
        return listRiwayat.size
    }
}