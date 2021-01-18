package com.example.knxmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.knxmanager.Adapters.ProcessAdapter
import com.example.knxmanager.Model.KnxProcess
import com.example.knxmanager.Model.KnxTelegram
import com.example.knxmanager.Model.PREFERENCE_IP_ADDRESS_FULL
import com.example.knxmanager.Services.ProcessService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class ProcessesViewer : AppCompatActivity() {

    lateinit var processRecyclerView: RecyclerView
    lateinit var processProgressBar: ProgressBar
    lateinit var processAdapter: ProcessAdapter
    lateinit var processList : MutableList<KnxProcess>
    lateinit var porcessService: ProcessService

    var ipAddress : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.processes_view)

        processRecyclerView = findViewById<RecyclerView>(R.id.processRecyclerView)
        processProgressBar = findViewById<ProgressBar>(R.id.processProgressBar)
        if (processRecyclerView != null) {
            processRecyclerView = findViewById<RecyclerView>(R.id.processRecyclerView)
        }
        ipAddress = getIntent().extras?.getString(PREFERENCE_IP_ADDRESS_FULL)  ?: ""
        porcessService = ProcessService(ipAddress)
        setRecyclerView()
        getList()
    }

    private fun setRecyclerView() {
        processRecyclerView.setHasFixedSize(true)
        processRecyclerView.setLayoutManager(LinearLayoutManager(this))
        processList = ArrayList<KnxProcess>(0)
        processAdapter = ProcessAdapter(this, processList)
        processRecyclerView.adapter = processAdapter
    }

    private fun getList() {
        processProgressBar.setVisibility(View.VISIBLE)
        processList.clear()
        var tempProcessList: List<KnxProcess> = ArrayList<KnxProcess>(0)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                tempProcessList = porcessService.getServerProcesses(ipAddress)
            } catch (e: Exception) {
            }
            withContext(Dispatchers.Main) {
                processList.addAll(tempProcessList)
                processProgressBar.setVisibility(View.GONE)
                processAdapter.notifyDataSetChanged()
            }
        }
    }

}