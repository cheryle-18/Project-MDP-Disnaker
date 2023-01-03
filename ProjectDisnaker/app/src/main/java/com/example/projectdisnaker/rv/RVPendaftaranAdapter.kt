package com.example.projectdisnaker.rv

import android.app.Activity
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectdisnaker.R
import com.example.projectdisnaker.api.PendaftaranPelatihanItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class RVPendaftaranAdapter(private val activity: Activity,
                           private val listPendaftaran : List<PendaftaranPelatihanItem>,
                           private val layout : Int
) : RecyclerView.Adapter<RVPendaftaranAdapter.CustomViewHolder>() {

    private var status_pendaftaran = arrayOf("Pendaftaran", "Wawancara", "Pelatihan", "Selesai", "Ditolak")
    private var status_kelulusan = arrayOf("Menunggu","Diterima", "Ditolak")

    class CustomViewHolder(var view: View) : RecyclerView.ViewHolder(view)
    {
        var tvNama : TextView = view.findViewById(R.id.tvNama)
        var tvNamaPesertaPend : TextView = view.findViewById(R.id.tvNamaPesertaPend)
        var tvStatusPendaftaran : TextView = view.findViewById(R.id.tvStatusPendaftaran)
        var llStatusKelulusan : LinearLayout = view.findViewById(R.id.llStatusKelulusan)
        var tvStatusKelulusan : TextView = view.findViewById(R.id.tvStatusKelulusan)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        var itemView = LayoutInflater.from(parent.context)
        return CustomViewHolder(
            itemView.inflate(
                layout, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val item = listPendaftaran[position]
        holder.tvNama.setText(item.pelatihan_nama)
        holder.tvNamaPesertaPend.setText(item.peserta!!.nama)
        holder.tvStatusPendaftaran.setText(status_pendaftaran[item.status_pendaftaran!!])
        holder.tvStatusKelulusan.setText(status_kelulusan[item.status_kelulusan!!])
        if(item.status_kelulusan!=1){
            holder.llStatusKelulusan.background.setTint(activity.resources.getColor(R.color.red))
        }
        holder.itemView.setOnClickListener {
            onClickListener?.onClick(position)
        }
    }

    override fun getItemCount(): Int {
        return listPendaftaran.size
    }

    var onClickListener:OnClickListener? = null

    interface OnClickListener{
        fun onClick(idx:Int)
    }
}