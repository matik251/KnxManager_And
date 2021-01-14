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
import com.txusballesteros.widgets.FitChart
import java.lang.Exception


class ProcessAdapter(_context: Context, _list: List<KnxProcess>) : RecyclerView.Adapter<ProcessAdapter.ViewHolder>() {
    lateinit var context : Context
    lateinit var processList  : List<KnxProcess>

    init{
        context  = _context
        processList = _list
    }

    @NonNull
    @Override
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view: View = LayoutInflater.from(context).inflate(R.layout.process_layout, parent, false)
        return ViewHolder(view)
    }

    @Override
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(processList != null && processList.isNotEmpty()){
            var telegram: KnxProcess = processList.get(position)


            holder.progressChart.setValue(telegram.getPercentageToChart())
            holder.percentage.setText(telegram.getPercentageText())
            holder.numbers.setText(telegram.getProcessStatsText())
            holder.processType.setText(telegram.ProcessType)
            holder.processIp.setText(telegram.ProcessIp)
            holder.fileName.setText(telegram.ProcessedFile)

        }
    }

    @Override
    override fun getItemCount(): Int {
        return processList.count()
    }

    class ViewHolder : RecyclerView.ViewHolder {
        lateinit var progressChart: FitChart
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
