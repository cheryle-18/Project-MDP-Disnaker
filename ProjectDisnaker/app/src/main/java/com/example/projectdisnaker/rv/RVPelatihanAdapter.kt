package com.example.projectdisnaker.rv

import android.app.Activity
import android.graphics.BitmapFactory
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.projectdisnaker.R
import com.example.projectdisnaker.api.PelatihanItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RVPelatihanAdapter(private val activity: Activity,
                        private val listPelatihan : List<PelatihanItem>,
                        private val layout : Int
) : RecyclerView.Adapter<RVPelatihanAdapter.CustomViewHolder>() {

    private var coroutine = CoroutineScope(Dispatchers.IO)

    class CustomViewHolder(var view: View) : RecyclerView.ViewHolder(view)
    {
        var img : ImageView = view.findViewById(R.id.ivPelatihan)
        var txtNama: TextView = view.findViewById(R.id.tvNamaPelatihan)
        var txtKuota: TextView = view.findViewById(R.id.tvKuotaPelatihan)
        var txtDurasi: TextView = view.findViewById(R.id.tvDurasiPelatihan)
        var txtDetail: TextView = view.findViewById(R.id.tvLihatDetailPelatihan)
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
        holder.img.setImageResource(R.drawable.ic_baseline_library_books_24)
        holder.txtNama.text = item.nama
        holder.txtKuota.text = "Kuota: "+item.kuota+" peserta"
        holder.txtDurasi.text = "Durasi: "+item.durasi+" hari"
    }

    override fun getItemCount(): Int {
        return listPelatihan.size
    }


}