package com.example.projectdisnaker.rv

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectdisnaker.R
import com.example.projectdisnaker.api.LowonganItem

class RVLowonganAdapter(
    private val listLowongan: MutableList<LowonganItem?>,
    val context: Context,
    private val onClick: (idx:Int)->Unit
) : RecyclerView.Adapter<RVLowonganAdapter.ListViewHolder>() {
    inner class ListViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val tvNamaPekerjaan: TextView = view.findViewById(R.id.tvNamaPekerjaan)
        val tvPerusahaan: TextView = view.findViewById(R.id.tvPerusahaan)
        val tvKuotaLowongan: TextView = view.findViewById(R.id.tvKuotaLowongan)
        val tvDetailLowongan: TextView = view.findViewById(R.id.tvDetailLowongan)

        init{
            view.setOnClickListener{
                val lowongan = listLowongan[adapterPosition]
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val item = LayoutInflater.from(context).inflate(R.layout.lowongan_list_item, parent, false)
        return ListViewHolder(item)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val lowongan = listLowongan[position]
        holder.tvNamaPekerjaan.setText(lowongan!!.nama)
        holder.tvPerusahaan.setText(lowongan!!.perusahaan)
        holder.tvKuotaLowongan.setText("Kuota: ${lowongan!!.kuota} peserta")

        holder.tvDetailLowongan.setOnClickListener {
            onClick(position)
        }
    }

    override fun getItemCount(): Int {
        return listLowongan.size
    }
}