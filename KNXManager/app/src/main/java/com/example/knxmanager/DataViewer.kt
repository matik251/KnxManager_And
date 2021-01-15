package com.example.knxmanager

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.knxmanager.Adapters.KnxTelegramsAdapter
import com.example.knxmanager.Model.KnxTelegram
import com.example.knxmanager.Model.PREFERENCE_IP_ADDRESS
import com.example.knxmanager.Model.PREFERENCE_IP_ADDRESS_FULL
import com.example.knxmanager.Services.KnxTelegramsService

class DataViewer : AppCompatActivity() {

    lateinit var recyclerView : RecyclerView
    lateinit var telegramsAdapter: KnxTelegramsAdapter
    lateinit var telegramList : List<KnxTelegram>
    lateinit var telegramsService: KnxTelegramsService
    var ipAddress : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.data_view)

        ipAddress = getIntent().extras?.getString(PREFERENCE_IP_ADDRESS_FULL)  ?: ""
        recyclerView = findViewById(R.id.dgRecyclerview)
        telegramsService = KnxTelegramsService(ipAddress)
        setRecyclerView()

    }

    private fun setRecyclerView() {
        recyclerView.setHasFixedSize(true)
        recyclerView.setLayoutManager(LinearLayoutManager(this))
        telegramsAdapter = KnxTelegramsAdapter(this, getList())
        recyclerView.adapter = telegramsAdapter
    }

    private fun getList(): List<KnxTelegram> {
        telegramList = telegramsService.getDummyKnxTelegrams()
        return telegramList
    }


}
