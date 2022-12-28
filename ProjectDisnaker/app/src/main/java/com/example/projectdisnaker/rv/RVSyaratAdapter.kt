package com.example.projectdisnaker.rv

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectdisnaker.R

class RVSyaratAdapter(
    private val listSyarat: ArrayList<String>,
    val context: Context
) : RecyclerView.Adapter<RVSyaratAdapter.ListViewHolder>() {
    inner class ListViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val tvDescSyarat: TextView = view.findViewById(R.id.tvDescSyarat)

        init{
            view.setOnClickListener{
                val syarat = listSyarat[adapterPosition]
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val item = LayoutInflater.from(context).inflate(R.layout.syarat_list_item, parent, false)
        return ListViewHolder(item)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val syarat = listSyarat[position]
        holder.tvDescSyarat.setText("- ${syarat}")
    }

    override fun getItemCount(): Int {
        return listSyarat.size
    }
}