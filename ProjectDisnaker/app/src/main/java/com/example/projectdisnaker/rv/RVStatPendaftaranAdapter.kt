package com.example.projectdisnaker.rv

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectdisnaker.R
import com.example.projectdisnaker.api.StatusItem

class RVStatPendaftaranAdapter(
    private val listStatus: MutableList<StatusItem?>,
    val context: Context,
    private val onClick: (idx:Int, pelatihanId:Int, stat: StatusItem)->Unit
) : RecyclerView.Adapter<RVStatPendaftaranAdapter.ListViewHolder>() {
    private var status_pendaftaran = arrayOf("Pendaftaran", "Wawancara", "Pelatihan", "Selesai", "Ditolak")
    private var status_kelulusan = arrayOf("Menunggu","Diterima", "Ditolak")

    inner class ListViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var tvNama : TextView = view.findViewById(R.id.tvNama)
        var tvNamaPesertaPend : TextView = view.findViewById(R.id.tvNamaPesertaPend)
        var tvStatusPendaftaran : TextView = view.findViewById(R.id.tvStatusPendaftaran)
        var llStatusKelulusan : LinearLayout = view.findViewById(R.id.llStatusKelulusan)
        var tvStatusKelulusan : TextView = view.findViewById(R.id.tvStatusKelulusan)

        init{
            view.setOnClickListener{
                val status = listStatus[adapterPosition]
                onClick(adapterPosition, status!!.pelatihanId!!, status)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val item = LayoutInflater.from(context).inflate(R.layout.pendaftaran_list_item, parent, false)
        return ListViewHolder(item)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        if(listStatus.size > 0){
            val status = listStatus[position]
            holder.tvNama.setText(status!!.pelatihan)
            holder.tvNamaPesertaPend.visibility = View.GONE
            holder.tvStatusPendaftaran.setText(status_pendaftaran[status.statusPendaftaran!!])
            holder.tvStatusKelulusan.setText(status_kelulusan[status.statusKelulusan!!])
            if(status.statusKelulusan==2){
                holder.llStatusKelulusan.background.setTint(context.resources.getColor(R.color.red))
            }
            else if(status.statusKelulusan==0){
                holder.llStatusKelulusan.background.setTint(Color.GRAY)
            }
        }
    }

    override fun getItemCount(): Int {
        return listStatus.size
    }
}