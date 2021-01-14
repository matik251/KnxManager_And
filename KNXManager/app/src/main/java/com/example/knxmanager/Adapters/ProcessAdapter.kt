package com.example.knxmanager.Adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.knxmanager.Model.KnxProcess
import com.example.knxmanager.R
import java.lang.Exception


class ProcessAdapter(_context: Context, _list: List<KnxProcess>) : RecyclerView.Adapter<ProcessAdapter.ViewHolder>() {
    lateinit var context : Context
    lateinit var telegramList  : List<KnxProcess>

    init{
        context  = _context
        telegramList = _list
    }

    @NonNull
    @Override
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view: View = LayoutInflater.from(context).inflate(R.layout.process_layout, parent, false)
        return ViewHolder(view)
    }

    @Override
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(telegramList != null && telegramList.isNotEmpty()){
            var telegram: KnxProcess = telegramList.get(position)

            holder.progressChart.setText(telegram.ProcessId.toString())
            holder.percentage.setText(telegram.ProcessId.toString())
            holder.numbers.setText(telegram.ProcessId.toString())
            holder.processType.setText(telegram.ProcessId.toString())
            holder.processIp.setText(telegram.ProcessId.toString())
            holder.fileName.setText(telegram.ProcessId.toString())

        }
    }

    @Override
    override fun getItemCount(): Int {
        return telegramList.count()
    }

    class ViewHolder : RecyclerView.ViewHolder {
        lateinit var progressChart: TextView
        lateinit var percentage: TextView
        lateinit var numbers: TextView
        lateinit var processType: TextView
        lateinit var processIp: TextView
        lateinit var fileName: TextView

        constructor(itemView: View) : super(itemView)
        {
            try{
                progressChart = itemView.findViewById(R.id.progressChart)
                percentage = itemView.findViewById(R.id.percentage)
                numbers  = itemView.findViewById(R.id.numbers)
                processType = itemView.findViewById(R.id.processType)
                processIp = itemView.findViewById(R.id.processIp)
                fileName = itemView.findViewById(R.id.fileName)

            }catch (e: Exception){
                Log.e("Exception", e.message.toString())
            }
        }
    }

}

private fun LayoutInflater.inflate(knxtelegramLayout: Unit) {

}
