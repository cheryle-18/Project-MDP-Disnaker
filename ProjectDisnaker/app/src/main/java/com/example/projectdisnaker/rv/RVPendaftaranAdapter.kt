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
                         private val listPelatihan : List<PendaftaranPelatihanItem>,
                         private val layout : Int
) : RecyclerView.Adapter<RVPendaftaranAdapter.CustomViewHolder>() {

    private var coroutine = CoroutineScope(Dispatchers.IO)
    private var status_nama = arrayOf("Pending", "Wawancara", "Pelatihan", "Selesai", "Ditolak")
    private var status_warna = arrayOf(Color.RED, Color.rgb(255,215,0), Color.rgb(255,215,0), Color.GREEN, Color.RED)

    class CustomViewHolder(var view: View) : RecyclerView.ViewHolder(view)
    {
        var tvNama : TextView = view.findViewById(R.id.tvNama)
        var tvKategori : TextView = view.findViewById(R.id.tvKategori)
        var llStatus : LinearLayout = view.findViewById(R.id.llStatus)
        var tvStatus : TextView = view.findViewById(R.id.tvStatus)
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
        val item = listPelatihan[position]
        holder.tvNama.setText(item.pelatihan_nama)
        holder.tvKategori.setText(item.kategori)
        holder.tvStatus.setText(status_nama[item.status_pendaftaran!!])
        holder.llStatus.background.setTint(status_warna[item.status_pendaftaran!!])
    }

    override fun getItemCount(): Int {
        return listPelatihan.size
    }
}