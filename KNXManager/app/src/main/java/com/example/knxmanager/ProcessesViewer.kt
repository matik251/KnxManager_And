package com.example.knxmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.example.knxmanager.Adapters.KnxTelegramsAdapter
import com.example.knxmanager.Adapters.ProcessAdapter
import com.example.knxmanager.Adapters.ProcessService
import com.example.knxmanager.Services.KnxTelegramsService
import com.txusballesteros.widgets.FitChart

class ProcessesViewer : AppCompatActivity() {

    lateinit var recyclerView : RecyclerView
    lateinit var processAdapter: ProcessAdapter
    lateinit var processList : List<KnxProcesses>
    lateinit var porcessService: ProcessService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.processes_view)


        var fitChart:FitChart = findViewById(R.id.processRecyclerView)

    }

    data class KnxProcesses(
        var ProcessId : Int,
        var ProcessName : String?,
        var ProcessIp : String?,
        var ProcessType : String?,

        var ProcessedFile : String?,
        var AcutalTelegramNr : Int?,
        var TotalTelegramNr : Int?
    ){
        init{
            ProcessId = 0
            ProcessName = ""
            ProcessIp = ""
            ProcessType = ""
            ProcessedFile = ""
            AcutalTelegramNr = 0
            TotalTelegramNr = 0
        }
    }
}