package com.example.knxmanager

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.knxmanager.Adapters.KnxTelegramsAdapter
import com.example.knxmanager.Adapters.ProcessAdapter
import com.example.knxmanager.Model.KnxProcess
import com.example.knxmanager.Model.KnxTelegram
import com.example.knxmanager.Model.PREFERENCE_IP_ADDRESS_FULL
import com.example.knxmanager.Services.ProcessService

class ProcessesViewer : AppCompatActivity() {

    lateinit var processRecyclerView: RecyclerView
    lateinit var processAdapter: ProcessAdapter
    lateinit var processList : List<KnxProcess>
    lateinit var porcessService: ProcessService

    var ipAddress : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.processes_view)

        processRecyclerView = findViewById<RecyclerView>(R.id.processRecyclerView)
        if (processRecyclerView != null) {
            processRecyclerView = findViewById<RecyclerView>(R.id.processRecyclerView)
        }
        ipAddress = getIntent().extras?.getString(PREFERENCE_IP_ADDRESS_FULL)  ?: ""
        porcessService = ProcessService(ipAddress)
        setRecyclerView()
    }

    private fun setRecyclerView() {
        processRecyclerView.setHasFixedSize(true)
        processRecyclerView.setLayoutManager(LinearLayoutManager(this))
        processAdapter = ProcessAdapter(this, getList())
        processRecyclerView.adapter = processAdapter
    }

    private fun getList(): List<KnxProcess> {
        processList = porcessService.getDummyServerProcessesList()
        return processList
    }

}