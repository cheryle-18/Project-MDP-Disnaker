package com.example.projectdisnaker.rv

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectdisnaker.R
import com.example.projectdisnaker.api.LowonganItem
import com.example.projectdisnaker.api.UserResponseItem

class RVPerusahaanAdapter(
    val context: Context,
    private val listPerusahaan: List<UserResponseItem>,
    private val onClick: (idx:Int)->Unit
) : RecyclerView.Adapter<RVPerusahaanAdapter.ListViewHolder>() {
    inner class ListViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val tvNamaPerusahaan: TextView = view.findViewById(R.id.tvNamaPerusahaanLow)
        val tvAlamatPerusahaan: TextView = view.findViewById(R.id.tvAlamatPerusahaan)

        init{
            view.setOnClickListener{
                onClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val item = LayoutInflater.from(context).inflate(R.layout.perusahaan_list_item, parent, false)
        return ListViewHolder(item)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val perusahaan = listPerusahaan[position]
        holder.tvNamaPerusahaan.setText(perusahaan.nama)
        holder.tvAlamatPerusahaan.setText(perusahaan.alamat)
    }

    override fun getItemCount(): Int {
        return listPerusahaan.size
    }
}