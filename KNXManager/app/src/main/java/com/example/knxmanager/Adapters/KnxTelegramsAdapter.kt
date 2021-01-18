package com.example.knxmanager.Adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.knxmanager.Adapters.KnxTelegramsAdapter.*
import com.example.knxmanager.Model.KnxTelegram
import com.example.knxmanager.R
import java.lang.Exception

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

            holder.TID.setText(telegram.tid.toString())
            holder.Timestamp.setText(telegram.timestamp.toString())
            holder.RawData.setText(telegram.rawData)
            holder.Service.setText(telegram.service)
            holder.FrameFormat.setText(telegram.frameFormat)
            holder.FileName.setText(telegram.fileName)
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
            try{
                TID = itemView.findViewById<TextView>(R.id.TID)
                Timestamp = itemView.findViewById<TextView>(R.id.TimeStamp)
                RawData = itemView.findViewById<TextView>(R.id.RawData)
                Service = itemView.findViewById<TextView>(R.id.Service)
                FrameFormat = itemView.findViewById<TextView>(R.id.FrameFormat)
                FileName = itemView.findViewById<TextView>(R.id.FileName)
            }catch (e:Exception){
                Log.e("Exception", e.message.toString())
            }
        }
    }

}

private fun LayoutInflater.inflate(knxtelegramLayout: Unit) {

}
