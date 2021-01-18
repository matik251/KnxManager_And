package com.example.knxmanager

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.knxmanager.Adapters.KnxTelegramsAdapter
import com.example.knxmanager.Model.KnxTelegram
import com.example.knxmanager.Model.PREFERENCE_IP_ADDRESS
import com.example.knxmanager.Model.PREFERENCE_IP_ADDRESS_FULL
import com.example.knxmanager.Services.ConnectionTesterService
import com.example.knxmanager.Services.KnxTelegramsService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class DataViewer : AppCompatActivity() {

    lateinit var recyclerView : RecyclerView
    lateinit var conProgBar : ProgressBar
    lateinit var telegramsAdapter: KnxTelegramsAdapter
    lateinit var telegramList : MutableList<KnxTelegram>
    lateinit var telegramsService: KnxTelegramsService
    var dataIpAddress : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.data_view)

        dataIpAddress = getIntent().extras?.getString(PREFERENCE_IP_ADDRESS_FULL)  ?: ""
        recyclerView = findViewById(R.id.dgRecyclerview)
        conProgBar = findViewById(R.id.dataProgressBar)
        telegramsService = KnxTelegramsService(dataIpAddress)
        setRecyclerView()
        getList()
    }

    private fun setRecyclerView() {
        recyclerView.setHasFixedSize(true)
        recyclerView.setLayoutManager(LinearLayoutManager(this))
        telegramList = ArrayList<KnxTelegram>(0)
        telegramsAdapter = KnxTelegramsAdapter(this, telegramList)
        recyclerView.adapter = telegramsAdapter
    }


    private fun getList()  {
        conProgBar.setVisibility(View.VISIBLE)
        telegramList.clear()
        var tempTelegramList :List<KnxTelegram> = ArrayList<KnxTelegram>(0)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                tempTelegramList = telegramsService.getKnxTelegrams(dataIpAddress)
            } catch (e: Exception) {
            }
            withContext(Dispatchers.Main) {
                telegramList.addAll(tempTelegramList)
                conProgBar.setVisibility(View.GONE)
                telegramsAdapter.notifyDataSetChanged()
            }
        }
    }
}
