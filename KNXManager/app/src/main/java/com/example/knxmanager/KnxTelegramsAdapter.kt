package com.example.knxmanager

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.knxmanager.KnxTelegramsAdapter.*

class KnxTelegramsAdapter(_context: Context, _list: List<KnxTelegram>) : RecyclerView.Adapter<ViewHolder>() {
    lateinit var context : Context
    lateinit var telegramList  : List<KnxTelegram>

    init{
        context  = _context
        telegramList = _list
    }

    @NonNull
    @Override
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view: View = LayoutInflater.from(context).inflate(R.layout.knxtelegram_layout, parent, false)
        return ViewHolder(view)
    }

    @Override
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(telegramList != null && telegramList.isNotEmpty()){
            var telegram: KnxTelegram = telegramList.get(position)

            holder.TID.setText(telegram.Tid.toString())
            holder.Timestamp.setText(telegram.Timestamp.toString())
            holder.RawData.setText(telegram.RawData)
            holder.Service.setText(telegram.Service)
            holder.FrameFormat.setText(telegram.FrameFormat)
            holder.FileName.setText(telegram.FileName)
        }
    }

    @Override
    override fun getItemCount(): Int {
        return telegramList.count()
    }

    class ViewHolder : RecyclerView.ViewHolder {
        lateinit var TID: TextView
        lateinit var Timestamp: TextView
        lateinit var RawData: TextView
        lateinit var Service: TextView
        lateinit var FrameFormat: TextView
        lateinit var FileName: TextView

        constructor(itemView: View) : super(itemView)
        {
            TID = itemView.findViewById<TextView>(R.id.TID)
            Timestamp = itemView.findViewById<TextView>(R.id.Timestamp)
            RawData = itemView.findViewById<TextView>(R.id.RawData)
            Service = itemView.findViewById<TextView>(R.id.Service)
            FrameFormat = itemView.findViewById<TextView>(R.id.FramFormat)
            FileName = itemView.findViewById<TextView>(R.id.FileName)
        }
    }

}

private fun LayoutInflater.inflate(knxtelegramLayout: Unit) {

}
