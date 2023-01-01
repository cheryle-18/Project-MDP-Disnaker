package com.example.projectdisnaker.rv

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectdisnaker.R
import com.example.projectdisnaker.api.PesertaPendaftaranItem

class RVPesertaPendaftaranAdapter(
    private val listPeserta: MutableList<PesertaPendaftaranItem?>,
    private val type: String,
    val context: Context,
    private val onClick: (idx:Int)->Unit
) : RecyclerView.Adapter<RVPesertaPendaftaranAdapter.ListViewHolder>() {
    inner class ListViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val tvNamaPeserta: TextView = view.findViewById(R.id.tvNamaPeserta)
        val tvStatusPeserta: TextView = view.findViewById(R.id.tvStatusPeserta)

        init{
            view.setOnClickListener{
                val peserta = listPeserta[adapterPosition]
                onClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val item = LayoutInflater.from(context).inflate(R.layout.peserta_list_item, parent, false)
        return ListViewHolder(item)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val peserta = listPeserta[position]
        holder.tvNamaPeserta.setText(peserta!!.nama)

        if(type=="lowongan"){
            holder.tvStatusPeserta.visibility = View.GONE
        }
        else{ //pelatihan
            var status = ""
            var statusInt = peserta.status_pendaftaran
            if(statusInt==0)
                status = "Menunggu"
            else if(statusInt==1)
                status = "Wawancara"
            else if(statusInt==2)
                status = "Dalam Pelatihan"
            else if(statusInt==3)
                status = "Selesai"
            else if(statusInt==4)
                status = "Ditolak"

            holder.tvStatusPeserta.setText("Status: ${status}")
        }
    }

    override fun getItemCount(): Int {
        return listPeserta.size
    }
}